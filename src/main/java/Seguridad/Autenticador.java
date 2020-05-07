package Seguridad;

import Repos.RepoUsuarios;
import Usuarios.Usuario;
import Usuarios.UsuarioBuilder;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import java.util.HashMap;
import java.util.Map;

public class Autenticador {
    private UsuarioBuilder usuarioBuilder;
    private RepoUsuarios repoUsuarios;
    private Map<String, Integer> intentosPorUsuario = new HashMap<String, Integer>();

    public Boolean controlDePassword(String password) {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return (strength.getScore() > 2 && password.length() > 8);
    }

    public Boolean checkUser(String nombre, String password) throws RuntimeException {
        Usuario user = repoUsuarios.buscarPorNombre(nombre);
        int intentos = intentosPorUsuario.getOrDefault(nombre, 0);
        if (intentos > 2) {
            throw new RuntimeException("Usuario bloqueado");
        }
        if (user.getPassword() != password) {
            sumarUnIntento(user.getNombre());
            throw new RuntimeException("Contraseña incorrecta");
        }
        resetearIntentosDe(user.getNombre());
        return true;
    }

    private void resetearIntentosDe(String nombre) {
        intentosPorUsuario.remove(nombre);
    }

    private void sumarUnIntento(String nombre) {
        int x = intentosPorUsuario.getOrDefault(nombre, 0);
        x++;
        intentosPorUsuario.put(nombre, x);
    }

    public void crearUsuario(String nombre, String password) throws RuntimeException {
        if (this.controlDePassword(password)) {
            Usuario nuevoUsuario = usuarioBuilder.crearUsuario(null, nombre, password, null);
            repoUsuarios.agregar(nuevoUsuario);
        } else {
            throw new RuntimeException("Tu contraseña es malarda");
        }
    }

    public RepoUsuarios getRepoUsuarios() {
        return repoUsuarios;
    }

    public Autenticador (RepoUsuarios repo, UsuarioBuilder builder) {
        this.repoUsuarios = repo;
        this.usuarioBuilder = builder;
    }
}
