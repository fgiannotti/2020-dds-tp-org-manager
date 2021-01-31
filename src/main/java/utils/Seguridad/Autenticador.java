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
import java.util.*;

public class Autenticador {
    private final UsuarioBuilder usuarioBuilder;
    private RepoUsuarios repoUsuarios;
    private Map<String, List<Object>> usuariosInfo = new HashMap<>();
    private final Configuracion configuracion = new Configuracion();

    //private static final Pair<Integer,LocalDateTime> defaultPair = new Pair<>(0, LocalDateTime.MIN);
    private static final List<Object> defaultPair = new ArrayList<>(Arrays.asList(0,LocalDateTime.MIN));

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
        System.out.print("mapa encontrado: ");
        System.out.print(usuariosInfo);
        List<Object> userInfo = usuariosInfo.getOrDefault(nombre, defaultPair);
        System.out.print("\n par encontrado: ");
        System.out.print(userInfo);

        //si supera cant intentos max OR fecha de bloqueo es posterior a la fecha actual
        System.out.printf("\n \n Intentos: %d de %d \n",(Integer)userInfo.get(0),configuracion.getIntentosMaximos());

        if ((Integer)userInfo.get(0) > configuracion.getIntentosMaximos() || ((LocalDateTime)userInfo.get(1)).isAfter(LocalDateTime.now())) {
            bloquearUsuario(nombre);
            throw new UserBlockedException("Usuario bloqueado");
        }

        if (!usuario.getPassword().equals(password)) {
            System.out.print("contraseña inc \n");
            sumarUnIntento(usuario.getNombre());
            throw new InvalidPasswordException("Contraseña incorrecta \n");
        }
        System.err.println ( "autenticado" );
        resetearIntentosDe(usuario.getNombre());
    }

    private void bloquearUsuario(String nombre){
        usuariosInfo.replace(nombre, new ArrayList<Object>(Arrays.asList(0, LocalDateTime.now().plusMinutes(2))) {
        });
    }

    private void resetearIntentosDe(String nombre) {
        usuariosInfo.remove(nombre);
    }

    private void sumarUnIntento(String nombre) {
        List<Object> usuarioInfo = usuariosInfo.getOrDefault(nombre, defaultPair);
        Integer intentos = (Integer) usuarioInfo.get(0);

        List<Object> usuarioInfoActualizada =  new ArrayList<>(Arrays.asList(intentos+1,LocalDateTime.now()));

        System.out.print("actualice la info a: ");
        System.out.print(usuarioInfoActualizada);

        this.usuariosInfo.put(nombre, usuarioInfoActualizada);
        System.out.print("\n el mapa total usuarios dsp del put: ");
        System.out.print(this.usuariosInfo);
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
