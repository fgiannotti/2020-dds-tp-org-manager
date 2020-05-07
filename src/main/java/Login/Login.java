package Login;

import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.Usuario;
import Usuarios.UsuarioBuilder;

public class Login {
    private Autenticador autenticador;

    public Boolean login (String nombre, String password) {
        try {
            return this.autenticador.checkUser(nombre, password);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().equals("Contraseña incorrecta")){
                return false;
            }
            throw(e);
        }
    }

    public void register (String nombre, String password) {
        try {
            autenticador.crearUsuario(nombre, password);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public Login (Autenticador autenticador) {
        this.autenticador = autenticador;
    }
}
