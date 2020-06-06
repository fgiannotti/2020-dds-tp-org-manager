import Items.Articulo;
import Items.Item;
import Login.Login;
import Operaciones.Proveedor;
import Organizaciones.Organizacion;
import Repos.RepoUsuarios;
import Seguridad.Autenticador;
import Usuarios.Admin;
import Usuarios.Usuario;
import Usuarios.UsuarioBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreacionalesTest {
    @Before
    public void Setup () {
        this.login = new Login(autenticador);
    }
    Proveedor proveedorTest0 = new Proveedor("Jorge Guaymallen", "6321456", "1714");
    Articulo articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest);
    Articulo articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest);
    Articulo articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest);
    List<Articulo> articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);

    @Test
    public void testCreacionItem(){
        Item itemTest = new Item(500, "Alegra tus fiestas", "Combo magico", articulos);
        Float result = (float)500;
        Assert.assertEquals(result, itemTest.getPrecioTotal());
    }

    @Test
    public void testCreacionArticulo(){
        Articulo articuloTest = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
        Assert.assertEquals("auriculares", articuloTest.getNombre());
    }

    @Test
    public void testCreacionProovedor(){
        Proveedor proveedorTest = new Proveedor("Ruben Phillips", "23623656", "4694");
        Assert.assertEquals("23623656", proveedorTest.getDocumento());
    }
}
