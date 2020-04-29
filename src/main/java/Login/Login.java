package Login;

import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.Usuario;
import Usuarios.UsuarioBuilder;

public class Login {
    private Autenticador autenticador;
    private UsuarioBuilder usuarioBuilder;
    private RepoUsuarios repoUsuarios;

    public Boolean login (String nombre, String password) {
        return this.repoUsuarios.checkUser(nombre, password);
    }

    public void register (String nombre, String password) {
        try {
            Usuario nuevoUsuario = usuarioBuilder.crearUsuario(null, nombre, password, null);
            repoUsuarios.agregar(nuevoUsuario);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    public Login (RepoUsuarios repo, UsuarioBuilder builder, Autenticador autenticador) {
        this.repoUsuarios = repo;
        this.usuarioBuilder = builder;
        this.autenticador = autenticador;
    }

    public RepoUsuarios getRepoUsuarios() {
        return repoUsuarios;
    }
}
