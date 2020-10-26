import entidades.Configuracion.Configuracion;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class VinculadorTest {

    Configuracion configuracion;

    @Before
    public void Setup () {
        configuracion = new Configuracion();
    }

    @Test
    public void Test1() {
        this.Setup();

    }

}
