import entidades.Organizaciones.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrganizacionesTest {
    public Empresa empresaMicro;
    public Empresa empresaPequenia;
    public Empresa empresaTramo1;
    public Empresa empresaTramo2;
    public Empresa empresaCareta;
    @BeforeAll
    public void Setup () {
        empresaMicro = new Empresa("EmpresaMicro", "Empresita", 2023123123, null, 1, 5, new Comercio(), (float)150000.0){};
        empresaPequenia = new Empresa("EmpresaConstructora", "MiEmpresita", 203123123, null, 2, 44, new Construccion(), (float)80000000.0){};
        empresaTramo1 = new Empresa("Los servicios de manaOS", "La de tramo 1, para testear, capo", 12354479, null, 420, 164, new Servicios(), (float)325000000.0){};
        empresaTramo2 = new Empresa("FriendlyCows", "FriendlyCows&co.", 85416645, null, 3, 214, new Agropecuario(), (float)546000000.0){};
    }

    @Test
    public void empresaComercialMicroEsCategorizadaComoMicro() {
        this.Setup();
        TipoEmpresa te = empresaMicro.getTipo();
        Assertions.assertEquals("MICRO", te.toString());
    }

    @Test
    public void empresaConstructoraPequeniaEsCategorizadaComoPequenia() {
        this.Setup();
        TipoEmpresa te = empresaPequenia.getTipo();
        Assertions.assertEquals("PEQUENIA", te.toString());
    }

    @Test
    public void empresaDeServiciosTramo1EsCategorizadaComoTramo1() {
        this.Setup();
        TipoEmpresa te = empresaTramo1.getTipo();
        Assertions.assertEquals("MEDIANATRAMO1", te.toString());
    }

    @Test
    public void empresaAgropecuariaTramo2EsCategorizadaComoTramo2() {
        this.Setup();
        TipoEmpresa te = empresaTramo2.getTipo();
        Assertions.assertEquals("MEDIANATRAMO2", te.toString());
    }

    @Test
    public void empresaCaretaRompe() {
        this.Setup();
        Assertions.assertThrows(RuntimeException.class, () -> {
            new Empresa("NotSoFriendlyCows", "NotSoFriendlyCows&co.", 85416645, null, 5, 700, new IndustriaYMineria(), (float)546000000.0);
        });
    }
}
