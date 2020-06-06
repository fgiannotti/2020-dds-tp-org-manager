import Items.Articulo;
import Items.Item;
import Login.Login;
import Operaciones.Proveedor;
import Organizaciones.Base;
import Organizaciones.Juridica;
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
    Proveedor proveedorTest0 = new Proveedor("Jorge Guaymallen", "6321456", "1714");
    Articulo articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
    Articulo articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest0);
    Articulo articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest0);
    List<Articulo> articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);

    @Test
    public void testCreacionItem(){
        Item itemTest = new Item ("Combo Navideno", "Alegra tus fiestas", articulos);
        Float result = (float)310;
        Assert.assertEquals(result, itemTest.getPrecioTotal());
    }

    @Test
    public void testCreacionArticulo(){
        Articulo articuloTest = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
        Assert.assertEquals("Auriculares", articuloTest.getNombre());
    }

    @Test
    public void testCreacionProovedor(){
        Proveedor proveedorTest = new Proveedor("Ruben Phillips", "23623656", "4694");
        Assert.assertEquals("23623656", proveedorTest.getDocumento());
    }

    @Test
    public void sePuedeCrearEntidadJuridicaSinBases(){
        Juridica organizacion = new Juridica("organizacionJuridica.SRL","Sarasa", 2023123123, 1680, 1, null);
        Assert.assertEquals("Sarasa", organizacion.getRazonSocial());
    }

    @Test
    public void baseTieneUnaJuridicaParticular(){
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", 2023123123, 1680, 1, null);
        Base orgBase = new Base("Base", "Tengo solo un padre", organizacion);
        Assert.assertEquals("JuridicaEjemplar",orgBase.getEntidadPadre().getNombreFicticio());
    }
}
