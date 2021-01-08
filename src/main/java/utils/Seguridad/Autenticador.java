package utils.Seguridad;

import entidades.Configuracion.Configuracion;
import entidades.Organizaciones.Organizacion;
import entidades.Usuarios.TipoUsuario;
import javafx.util.Pair;
import repositorios.RepoUsuarios;
import entidades.Usuarios.Usuario;
import repositorios.Builders.UsuarioBuilder;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Autenticador {
    private final UsuarioBuilder usuarioBuilder;
    private RepoUsuarios repoUsuarios;
    private Map<String, Pair<Integer, LocalDateTime>> usuariosInfo = new HashMap<String, Pair<Integer,LocalDateTime>>();
    private final Configuracion configuracion = new Configuracion();

    private static final Pair<Integer,LocalDateTime> defaultPair = new Pair<>(0, LocalDateTime.MIN);

    public Autenticador(RepoUsuarios repo, UsuarioBuilder builder) {
        this.repoUsuarios = repo;
        this.usuarioBuilder = builder;
    }

    public Boolean esSegura(String password) {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return (strength.getScore() > configuracion.getPasswordScoreMinimo() && password.length() > configuracion.getPasswordLengthMinimo());
    }

    public void checkUser(String nombre, String password) throws Exception {
        Usuario usuario = repoUsuarios.buscarPorNombre(nombre);
        Pair<Integer,LocalDateTime> userInfo = usuariosInfo.getOrDefault(nombre, defaultPair);

        //si supera cant intentos max OR fecha de bloqueo es posterior a la fecha actual
        System.out.printf("Intentos: %d de %d",userInfo.getKey(),configuracion.getIntentosMaximos());
        if (userInfo.getKey() > configuracion.getIntentosMaximos() || userInfo.getValue().isAfter(LocalDateTime.now())) {
            bloquearUsuario(nombre);
            throw new UserBlockedException("Usuario bloqueado");
        }

        if (!usuario.getPassword().equals(password)) {
            System.err.print("contraseña inc");
            sumarUnIntento(usuario.getNombre());
            throw new InvalidPasswordException("Contraseña incorrecta");
        }
        System.err.println ( "autenticado" );
        resetearIntentosDe(usuario.getNombre());
    }

    private void bloquearUsuario(String nombre){
        usuariosInfo.replace(nombre, new Pair<>(0, LocalDateTime.now().plusMinutes(2)));
    }

    private void resetearIntentosDe(String nombre) {
        usuariosInfo.remove(nombre);
    }

    private void sumarUnIntento(String nombre) {
        Pair<Integer,LocalDateTime> usuarioInfo = usuariosInfo.getOrDefault(nombre, defaultPair);
        Integer intentos = usuarioInfo.getKey();
        Pair<Integer,LocalDateTime> usuarioInfoActualizada =  new Pair<Integer,LocalDateTime>(intentos+1,LocalDateTime.now());
        System.err.print(usuarioInfoActualizada);
        usuariosInfo.put(nombre, usuarioInfoActualizada);
        System.out.print("usuarioInfo");
        System.out.print(usuarioInfo);
    }

    public void crearUsuario(String nombre, Organizacion organizacion ,String password) throws RuntimeException {
        if (this.esSegura(password)) {
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
