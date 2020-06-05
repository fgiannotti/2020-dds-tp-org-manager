import Login.Login;
import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.UsuarioBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreacionalesTest {
    @Before
    public void Setup () {
        this.login = new Login(autenticador);
    }

    @Test
    public void weakPasswordIsWeakTest_testCase1() {
        Boolean bool = this.autenticador.controlDePassword("weak");
        Assertions.assertFalse(bool);
    }
}
