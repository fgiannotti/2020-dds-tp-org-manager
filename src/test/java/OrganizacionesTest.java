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

public class OrganizacionesTest {
    public Empresa empresaMicro;
    public Empresa empresaPequenia;
    public Empresa empresaTramo1;
    public Empresa empresaTramo2;
    public Empresa empresaCareta;
    @Before
    public void Setup () {
        empresaMicro = new Empresa("EmpresaMicro", "Empresita", 2023123123, 1680, 1, 5, Actividad.COMERCIO, (float)150000.0){};
        empresaPequenia = new Empresa("EmpresaConstructora", "MiEmpresita", 203123123, 1685, 2, 44, Actividad.CONSTRUCCION, (float)80000000.0){};
        empresaTramo1 = new Empresa("Los servicios de manaOS", "La de tramo 1, para testear, capo", 12354479, 6969, 420, 164, Actividad.SERVICIOS, (float)325000000.0){};
        empresaTramo2 = new Empresa("FriendlyCows", "FriendlyCows&co.", 85416645, 4200, 3, 214, Actividad.AGROPECUARIO, (float)546000000.0){};
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
            new Empresa("NotSoFriendlyCows", "NotSoFriendlyCows&co.", 85416645, 4200, 5, 700, Actividad.INDUSTRIAYMINERIA, (float)546000000.0);
        });
    }
}
