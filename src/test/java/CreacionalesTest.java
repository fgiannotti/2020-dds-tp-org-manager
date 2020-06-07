import Items.Articulo;
import Items.Item;
import Login.Login;
import MedioDePago.Credito;
import MedioDePago.Debito;
import Operaciones.Comprobante;
import Operaciones.OperacionEgreso;
import Operaciones.Proveedor;
import Organizaciones.Base;
import Organizaciones.Juridica;
import Organizaciones.*;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreacionalesTest {
    Proveedor proveedorTest0 = new Proveedor("Jorge Guaymallen", "6321456", "1714");
    Articulo articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
    Articulo articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest0);
    Articulo articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest0);
    List<Articulo> articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);
    Item itemTest1 = new Item("Auriculares, pendrive y salsa" ,"Promo1", articulos);
    List<Item> itemsTest1 = Arrays.asList(itemTest1);
    Comprobante comprobanteTest2 = new Comprobante(itemsTest1);
    Credito creditoTest1 = new Credito("Tarjeta de credito Santander", 20202020);
    OSC caritas = new OSC("caritas", "Caritas ONG", 2040511992, 3031, 201032);

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
    public void testCreacionComprobante(){
        Comprobante comprobantePrueba1 = new Comprobante(itemsTest1);
        Assert.assertEquals(itemsTest1, comprobantePrueba1.getItems());
    }

    @Test
    public void testCreacionOperacion(){
        OperacionEgreso operacionPrueba1 = new OperacionEgreso(310, "Compra navidenia", proveedorTest0, creditoTest1, new Date(), "Factura", comprobanteTest2, itemsTest1);
        Assertions.assertEquals(310, operacionPrueba1.getMontoTotal());
        Assertions.assertEquals(comprobanteTest2, operacionPrueba1.getComprobante());
        Assertions.assertEquals(itemsTest1, operacionPrueba1.getItems());
    }

    @Test
    public void testCreacionMedioDePago(){
        Debito tarjetaDeDebito = new Debito("Tarjeta Debito ICBC", 12204210);
        Assertions.assertEquals(12204210, tarjetaDeDebito.getNumero());
    }

    @Test
    public void testCreacionOSC(){
        OSC caritas = new OSC("caritas", "Caritas ONG", 2040511992, 3031, 201032);
        Assertions.assertEquals("caritas", caritas.getNombreFicticio());
    }

    @Test
    public void testCreacionBase(){
        Base cooperativaAlimentar = new Base("alimentar", "cooperativa de trabajadores del sector alimenticio", caritas);
        Assertions.assertEquals("alimentar", cooperativaAlimentar.getNombreFicticio());
        Assertions.assertEquals(caritas, cooperativaAlimentar.getEntidadPadre());
    }

    @Test
    public void testCreacionMicro(){
        Empresa acme = new Empresa("acme company" , "acme systems", 01040501, 2030, 30, 300, Actividad.SERVICIOS, (float)10000.00);
        Assertions.assertEquals("acme company", acme.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MICRO, acme.getTipo());
    }

    @Test
    public void testCreacionPequenia(){
        Empresa dia = new Empresa("DIA" , "Supermercados Dia SRL", 201629002, 5010, 600, 1000, Actividad.COMERCIO, (float)31000000.00);
        Assertions.assertEquals("DIA", dia.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.PEQUENIA, dia.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo1(){
        Empresa piramides = new Empresa("Piramides constructora" , "Martin Lopez", 20152909, 2020, 3000, 10000, Actividad.CONSTRUCCION, (float)400000000.00);
        Assertions.assertEquals("Piramides constructora", piramides.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MEDIANATRAMO1, piramides.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo2() {
        Empresa Mercadolibre = new Empresa("Mercadolibre", "Marcos Galperin",90807060, 8810, 12000, 30000, Actividad.INDUSTRIAYMINERIA, (float)600000000.00);
        Assertions.assertEquals("Mercadolibre", Mercadolibre.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MEDIANATRAMO2, Mercadolibre.getTipo());
    }

    @Test
    public void baseTieneUnaJuridicaParticular(){
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", 2023123123, 1680, 1, null);
        Base orgBase = new Base("Base", "Tengo solo un padre", organizacion);
        Assert.assertEquals("JuridicaEjemplar",orgBase.getEntidadPadre().getNombreFicticio());
    }
}