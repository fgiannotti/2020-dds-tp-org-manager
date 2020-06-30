import BandejaDeEntrada.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;

public class ValidacionesTest {
    public BandejaDeEntrada bandeja;
    public Resultado resultado;

    @Before
    public void Setup () {
        Resultado resultado = new Resultado();
   }

    public void algunTest() {
        this.Setup();

        Assertions.assertEquals("asd", "asd");
    }

}
