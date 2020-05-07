import Login.Login;
import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.UsuarioBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeguridadTests {
    private RepoUsuarios repoUsuarios = new RepoUsuarios();
    private UsuarioBuilder builder = new UsuarioBuilder();
    private Autenticador autenticador = new Autenticador(repoUsuarios, builder);
    private Login login = new Login(autenticador);

    @Before
    public void Setup () {
        this.login = new Login(autenticador);
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

    @Test
    public void puedoRegistrarUsuario() {
        login.register("Nacho", ":JM!VbT+y'-#?9c98`d,");
        Assertions.assertEquals(autenticador.getRepoUsuarios().buscarPorNombre("Nacho").getNombre(), "Nacho");
    }

    @Test
    public void tiraErrorLuegoDe3IntentosFallidos() {
        login.register("Nachooo", ":JM!VbT+y'-#?9c98`d,");
        login.login("Nachooo", "asd");
        login.login("Nachooo", "asd");
        login.login("Nachooo", "asd");
        Assertions.assertThrows(RuntimeException.class, () -> {
            login.login("Nachooo", "asd");
        });
    }


}
