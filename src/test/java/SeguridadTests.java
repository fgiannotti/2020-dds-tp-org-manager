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
    private Autenticador autenticador = new Autenticador();
    private UsuarioBuilder builder = new UsuarioBuilder(autenticador);
    private Login login = new Login(repoUsuarios, builder, autenticador);

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

    @Test
    public void puedoRegistrarUsuario() {
        login.register("Nacho", ":JM!VbT+y'-#?9c98`d,");
        Assertions.assertEquals(login.getRepoUsuarios().buscarPorNombre("Nacho").getNombre(), "Nacho");
    }

    @Test
    public void tiraErrorLuegoDe3IntentosFallidos() {
        login.register("Nacho", ":JM!VbT+y'-#?9c98`d,");
        login.login("Nacho", "asd");
        login.login("Nacho", "asd");
        Assertions.assertThrows(RuntimeException.class, () -> {
            login.login("Nacho", "asd");
        });
    }


}
