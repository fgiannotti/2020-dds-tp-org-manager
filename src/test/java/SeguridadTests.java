import Login.Login;
import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.UsuarioBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeguridadTests {
    private Login login;
    private RepoUsuarios repoUsuarios = new RepoUsuarios();
    private UsuarioBuilder builder = new UsuarioBuilder();
    private Autenticador autenticador = new Autenticador();

    @Before
    public void Setup () {
        this.login = new Login(repoUsuarios, builder, autenticador);
    }

    @Test
    public void weakPasswordIsWeak() {
        Boolean bool = this.autenticador.controlDePassword("weak");
        Assertions.assertFalse(bool);
    }

    @Test
    public void strongPasswordIsStrong() {
        Boolean bool = this.autenticador.controlDePassword(":JM!VbT+y'-#?9c98`d,");
        Assertions.assertTrue(bool);
    }
}
