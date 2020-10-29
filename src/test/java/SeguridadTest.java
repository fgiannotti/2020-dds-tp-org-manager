import utils.Seguridad.Login;
import entidades.Organizaciones.Comercio;
import entidades.Organizaciones.Empresa;
import entidades.Organizaciones.Juridica;
import repositorios.RepoUsuarios;
import utils.Seguridad.Autenticador;
import repositorios.Builders.UsuarioBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void controlDePasswordTest() {
        Boolean bool = this.autenticador.controlDePassword(":JM!VbT+y'-#?9c98`d,");
        Assertions.assertTrue(bool);
    }

    @Test
    public void puedoRegistrarUsuario() {
        Empresa unaEmpresa = new Empresa("EmpresaMicro", "Empresita", 2023123123, null, 1, 5, new Comercio(), (float)150000.0){};
        Juridica organizacion = new Juridica("organizacionJuridica.SRL","Descripcion", 2023123123, null, 1, null);
        login.register("Nacho", organizacion,":JM!VbT+y'-#?9c98`d,");
        Assertions.assertEquals(autenticador.getRepoUsuarios().buscarPorNombre("Nacho").getNombre(), "Nacho");
    }

    @Test
    public void puedoLoggearmeUnaVezRegistradoUsuario() {
        this.Setup();
        Empresa unaEmpresa = new Empresa("EmpresaMicro", "Empresita", 2023123123, null, 1, 5, new Comercio(), (float)150000.0){};
        Juridica organizacion = new Juridica("organizacionJuridica.SRL","Descripcion", 2023123123, null, 1, null);
        login.register("Nacho", organizacion,":JM!VbT+y'-#?9c98`d,");
        login.login("Nacho", ":JM!VbT+y'-#?9c98`d,");
    }

    @Test
    public void tiraErrorLuegoDe3IntentosFallidos() {
        Empresa unaEmpresa = new Empresa("EmpresaMicro", "Empresita", 2023123123, null, 1, 5, new Comercio(), (float)150000.0){};
        Juridica organizacion = new Juridica("organizacionJuridica.SRL","Descripcion", 2023123123, null, 1, null);
        login.register("Nacho", organizacion, ":JM!VbT+y'-#?9c98`d,");
        login.login("Nacho", "asd");
        login.login("Nacho", "asd");
        login.login("Nacho", "asd");
        Assertions.assertThrows(RuntimeException.class, () -> {
            login.login("Nachooo", "asd");
        });
    }

}
