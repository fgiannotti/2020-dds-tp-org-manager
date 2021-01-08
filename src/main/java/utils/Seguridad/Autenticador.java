package utils.Seguridad;

import com.sun.tools.internal.xjc.model.CDefaultValue;
import entidades.Configuracion.Configuracion;
import entidades.Organizaciones.Organizacion;
import entidades.Usuarios.TipoUsuario;
import javafx.util.Pair;
import repositorios.RepoUsuarios;
import entidades.Usuarios.Usuario;
import repositorios.Builders.UsuarioBuilder;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import scala.Int;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Autenticador {
    private final UsuarioBuilder usuarioBuilder;
    private final RepoUsuarios repoUsuarios;
    private Map<String, Pair<Integer, LocalDateTime>> usuariosInfo = new HashMap<String, Pair<Integer,LocalDateTime>>();
    private final Configuracion configuracion;

    private static final Pair<Integer,LocalDateTime> defaultPair = new Pair<>(0, LocalDateTime.now());

    public Autenticador(RepoUsuarios repo, UsuarioBuilder builder) {
        this.repoUsuarios = repo;
        this.usuarioBuilder = builder;
        this.configuracion = new Configuracion();
    }

    public Boolean controlDePassword(String password) {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return (strength.getScore() > configuracion.getPasswordScoreMinimo() && password.length() > configuracion.getPasswordLengthMinimo());
    }

    public Boolean checkUser(String nombre, String password) throws RuntimeException {
        Usuario usuario = repoUsuarios.buscarPorNombre(nombre);
        Pair<Integer,LocalDateTime> userInfo = usuariosInfo.getOrDefault(nombre, defaultPair);
        //si supera cant intentos max OR fecha de bloqueo es posterior a la fehca actual
        if (userInfo.getKey() > configuracion.getIntentosMaximos() || userInfo.getValue().isAfter(LocalDateTime.now())) {
            bloquearUsuario(nombre);
            throw new RuntimeException("Usuario bloqueado");
        }

        if (!usuario.getPassword().equals(password)) {
            sumarUnIntento(usuario.getNombre());
            throw new RuntimeException("Contraseña incorrecta");
        }
        System.err.println ( "autenticado" );
        resetearIntentosDe(usuario.getNombre());
        return true;
    }
    private void bloquearUsuario(String nombre){
        usuariosInfo.remove(nombre);

        usuariosInfo.put(nombre, new Pair<>(0, LocalDateTime.now().plusMinutes(2)));
    }
    private void resetearIntentosDe(String nombre) {
        usuariosInfo.remove(nombre);
    }

    private void sumarUnIntento(String nombre) {

        Pair<Integer,LocalDateTime> usuarioInfo = usuariosInfo.getOrDefault(nombre, defaultPair);
        Pair<Integer,LocalDateTime> usuarioInfoActualizada =  new Pair<Integer,LocalDateTime>(usuarioInfo.getKey()+1,LocalDateTime.now());

        usuariosInfo.put(nombre, usuarioInfoActualizada);
    }

    public void crearUsuario(String nombre, Organizacion organizacion ,String password) throws RuntimeException {
        if (this.controlDePassword(password)) {
            Usuario nuevoUsuario = usuarioBuilder.crearUsuario(TipoUsuario.BASICO, nombre, password, organizacion,null);
            repoUsuarios.agregar(nuevoUsuario);
        } else {
            throw new RuntimeException("Tu contraseña es malarda");
        }
    }

    public RepoUsuarios getRepoUsuarios() {
        return repoUsuarios;
    }


}
