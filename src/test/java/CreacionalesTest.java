import Builders.EmpresaBuilder;
import Factorys.EmpresaBuilderFactory;
import Items.Articulo;
import Items.Item;
import MedioDePago.Credito;
import MedioDePago.Debito;
import Operaciones.Comprobante;
import Operaciones.OperacionEgreso;
import Operaciones.Proveedor;
import Organizaciones.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreacionalesTest<nuevaEmpresa> {
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
        Empresa acme = new Empresa("acme company" , "acme systems", 01040501, 2030, 30, 5, new Servicios(), (float)10000.00);
        Assertions.assertEquals("acme company", acme.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MICRO, acme.getTipo());
    }

    @Test
    public void testCreacionPequenia(){
        Empresa dia = new Empresa("DIA" , "Supermercados Dia SRL", 201629002, 5010, 600, 30, new Comercio(), (float)38000000.00);
        Assertions.assertEquals("DIA", dia.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.PEQUENIA, dia.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo1(){
        Empresa piramides = new Empresa("Piramides constructora" , "Martin Lopez", 20152909, 2020, 3000, 185, new Construccion(), (float)400000000.00);
        Assertions.assertEquals("Piramides constructora", piramides.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MEDIANATRAMO1, piramides.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo2() {
        Empresa Mercadolibre = new Empresa("Mercadolibre", "Marcos Galperin",90807060, 8810, 12000, 600, new IndustriaYMineria(), (float)1709590000.00);
        Assertions.assertEquals("Mercadolibre", Mercadolibre.getNombreFicticio());
        Assertions.assertEquals(TipoEmpresa.MEDIANATRAMO2, Mercadolibre.getTipo());
    }

    @Test
    public void baseTieneUnaJuridicaParticular(){
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", 2023123123, 1680, 1, null);
        Base orgBase = new Base("Base", "Tengo solo un padre", organizacion);
        Assert.assertEquals("JuridicaEjemplar", orgBase.getEntidadPadre().getNombreFicticio());
    }

    @Test
    public void testBuilderEmpresa() {

        EmpresaBuilderFactory builderFactory = new EmpresaBuilderFactory();

        EmpresaBuilder empresaBuilder = builderFactory.createBuilder();

        Agropecuario agro = new Agropecuario();

        Empresa nuevaEmpresa = null;
        try {
            nuevaEmpresa = empresaBuilder.agregarNombre("pepito")
                    .agregarRazonSocial("Jorge Lopez")
                    .agregarCuit(20302030L)
                    .agregarCodigoPostal(1410)
                    .agregarCantidadDePersonal(30)
                    .agregarActividad(new Agropecuario())
                    .agregarPromedioDeVentas((float)345429999)
                    .agregarCodigoDeInscripcion(13030)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(nuevaEmpresa);
        Assertions.assertEquals("pepito", nuevaEmpresa.getNombreFicticio());
        Assertions.assertEquals("Jorge Lopez", nuevaEmpresa.getRazonSocial());
        Assertions.assertEquals(20302030, nuevaEmpresa.getCuit());
        Assertions.assertEquals(1410, nuevaEmpresa.getDirPostal());
        Assertions.assertEquals(30, nuevaEmpresa.getCantidadPersonal());
        Assertions.assertEquals("Agropecuario" , nuevaEmpresa.getActividad().getNombre());
        Assertions.assertEquals(345429999f, nuevaEmpresa.getPromedioVentas());
        Assertions.assertEquals(TipoEmpresa.MEDIANATRAMO1, nuevaEmpresa.getTipo());
    }
    }
