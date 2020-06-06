import Login.Login;
import Organizaciones.*;
import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.Usuario;
import Usuarios.UsuarioBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class SeguridadTest {
    private RepoUsuarios repoUsuarios = new RepoUsuarios();
    private UsuarioBuilder builder = new UsuarioBuilder();
    private Autenticador autenticador = new Autenticador(repoUsuarios, builder);
    private Login login = new Login(autenticador);

    @Before
    public void Setup () {
        this.login = new Login(autenticador);
    }

    @Test
    public void weakPasswordIsWeakTest_testCase1() {
        Boolean bool = this.autenticador.controlDePassword("weak");
        Assertions.assertFalse(bool);
    }

    @Test
    public void Test2() {
        Boolean bool = this.autenticador.controlDePassword(":JM!VbT+y'-#?9c98`d,");
        Assertions.assertTrue(bool);
    }

    @Test
    public void puedoRegistrarUsuario() {
        Float promedio = Float.valueOf(13550);
        Empresa unaEmpresa = new Empresa("juridica.SA",
                null,
                "unaRazon",
                123456,
                1714,
                5949,
                Actividad.COMERCIO,
                promedio);
        Base organizacion = new Base("organizacionDeBase.SRL",null,"Descripcion",unaEmpresa);
        login.register("Nacho", organizacion,":JM!VbT+y'-#?9c98`d,");
        Assertions.assertEquals(autenticador.getRepoUsuarios().buscarPorNombre("Nacho").getNombre(), "Nacho");
    }

    @Test
    public void tiraErrorLuegoDe3IntentosFallidos() {
        Float promedio = Float.valueOf(13550);
        Empresa unaEmpresa = new Empresa("juridica.SA",
                null,
                "unaRazon",
                123456,
                1714,
                5949,
                Actividad.COMERCIO,
                promedio);
        Base organizacion = new Base("organizacionDeBase.SRL",null,"Descripcion",unaEmpresa);

        login.register("Nachooo",organizacion, ":JM!VbT+y'-#?9c98`d,");
        login.login("Nachooo", "asd");
        login.login("Nachooo", "asd");
        login.login("Nachooo", "asd");
        Assertions.assertThrows(RuntimeException.class, () -> {
            login.login("Nachooo", "asd");
        });
    }


}
