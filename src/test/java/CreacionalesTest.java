import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import repositorios.Builders.EmpresaBuilder;
import entidades.DatosGeograficos.Ciudad;
import entidades.DatosGeograficos.Direccion;
import entidades.DatosGeograficos.DireccionPostal;
import entidades.DatosGeograficos.Provincia;
import entidades.DatosGeograficos.Pais;
import entidades.Estrategias.Criterio;
import repositorios.Factorys.EmpresaBuilderFactory;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Credito;
import entidades.MedioDePago.Debito;
import entidades.Operaciones.Comprobante;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreacionalesTest<nuevaEmpresa> {
    Proveedor proveedorTest0 = new Proveedor();
    Articulo articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
    Articulo articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest0);
    Articulo articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest0);
    List<Articulo> articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);
    Item itemTest1 = new Item("Auriculares, pendrive y salsa" ,"Promo1", articulos);
    List<Item> itemsTest1 = Arrays.asList(itemTest1);
    Comprobante comprobanteTest2 = new Comprobante(itemsTest1);
    Credito creditoTest1 = new Credito("Tarjeta de credito Santander", "20202020");
    OSC caritas = new OSC("caritas", "Caritas ONG", "2040511992", null, 201032);
    Direccion unaDireccion = new Direccion("Ratti", 1884,0);
    Ciudad unaCiudadFacherita = new Ciudad("Ituzaingo");
    Provincia unaProvincia = new Provincia("Buenas Aires");
    Pais unPais = new Pais("Argentina", "Locale");
    DireccionPostal unaDireccionPostalDePrueba = new DireccionPostal(unaDireccion, unaCiudadFacherita, unaProvincia, unPais);
    @BeforeAll
    public void setup(){
        proveedorTest0.setDireccionPostal("6321456");
        proveedorTest0.setnombreApellidoRazon("Jorge Guaymallen");
        proveedorTest0.setDireccionPostal("1714");

    }
    @Test
    public void testCreacionItem(){
        this.setup();
        Item itemTest = new Item ("Combo Navideno", "Alegra tus fiestas", articulos);
        Float result = (float)310;
        assertEquals(result, itemTest.getPrecioTotal());
    }

    @Test
    public void testCreacionArticulo(){
        this.setup();
        Articulo articuloTest = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
        assertEquals("Auriculares", articuloTest.getNombre());
    }

    @Test
    public void testCreacionProovedor(){
        this.setup();
        Proveedor proveedorTest = new Proveedor("Ruben Phillips", "23623656", "4694");
        assertEquals("23623656", proveedorTest.getDocumento());
    }

    @Test
    public void sePuedeCrearEntidadJuridicaSinBases(){
        this.setup();
        Juridica organizacion = new Juridica("organizacionJuridica.SRL","Sarasa", "2023123123", null, 1, null);
        assertEquals("Sarasa", organizacion.getRazonSocial());
    }

    @Test
    public void testCreacionComprobante(){
        this.setup();
        Comprobante comprobantePrueba1 = new Comprobante(itemsTest1);
        assertEquals(itemsTest1, comprobantePrueba1.getItems());
    }

    @Test
    public void testCreacionOperacion(){
        this.setup();
        List<Proveedor> proveedoresTest0 = new ArrayList<Proveedor>();
        proveedoresTest0.add(proveedorTest0);
        OperacionEgreso operacionPrueba1 = new OperacionEgreso(310, "Compra navidenia",proveedoresTest0, creditoTest1, LocalDate.now(), "Factura", comprobanteTest2, itemsTest1, 1,Criterio.MENOR_VALOR,new ArrayList<>());
        assertEquals(310, operacionPrueba1.getMontoTotal());
        assertEquals(comprobanteTest2, operacionPrueba1.getComprobante());
        assertEquals(itemsTest1, operacionPrueba1.getItems());
    }

    @Test
    public void testCreacionMedioDePago(){
        this.setup();
        Debito tarjetaDeDebito = new Debito("Tarjeta Debito ICBC", "12204210");
        assertEquals(12204210, tarjetaDeDebito.getNumero());
    }

    @Test
    public void testCreacionOSC(){
        this.setup();
        OSC caritas = new OSC("caritas", "Caritas ONG", "2040511992", null, 201032);
        assertEquals("caritas", caritas.getNombreFicticio());
    }

    @Test
    public void testCreacionBase(){
        this.setup();
        Base cooperativaAlimentar = new Base("alimentar", "cooperativa de trabajadores del sector alimenticio", caritas);
        assertEquals("alimentar", cooperativaAlimentar.getNombreFicticio());
        assertEquals(caritas, cooperativaAlimentar.getEntidadPadre());
    }

    @Test
    public void testCreacionMicro(){
        this.setup();
        Empresa acme = new Empresa("acme company" , "acme systems", "01040501", null, 30, 5, new Servicios(), (float)10000.00);
        assertEquals("acme company", acme.getNombreFicticio());
        assertEquals(TipoEmpresa.MICRO, acme.getTipo());
    }

    @Test
    public void testCreacionPequenia(){
        this.setup();
        Empresa dia = new Empresa("DIA" , "Supermercados Dia SRL", "201629002", null, 600, 30, new Comercio(), (float)38000000.00);
        assertEquals("DIA", dia.getNombreFicticio());
        assertEquals(TipoEmpresa.PEQUENIA, dia.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo1(){
        this.setup();
        Empresa piramides = new Empresa("Piramides constructora" , "Martin Lopez", "20152909", null, 3000, 185, new Construccion(), (float)400000000.00);
        assertEquals("Piramides constructora", piramides.getNombreFicticio());
        assertEquals(TipoEmpresa.MEDIANATRAMO1, piramides.getTipo());
    }

    @Test
    public void testCreacionMedianaTramo2() {
        this.setup();
        Empresa Mercadolibre = new Empresa("Mercadolibre", "Marcos Galperin","90807060", null, 12000, 600, new IndustriaYMineria(), (float)1709590000.00);
        assertEquals("Mercadolibre", Mercadolibre.getNombreFicticio());
        assertEquals(TipoEmpresa.MEDIANATRAMO2, Mercadolibre.getTipo());
    }

    @Test
    public void baseTieneUnaJuridicaParticular(){
        this.setup();
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", "2023123123", null, 1, null);
        Base orgBase = new Base("Base", "Tengo solo un padre", organizacion);
        assertEquals("JuridicaEjemplar", orgBase.getEntidadPadre().getNombreFicticio());
    }

    @Test
    public void testBuilderEmpresa() {
        this.setup();
        EmpresaBuilderFactory builderFactory = new EmpresaBuilderFactory();

        EmpresaBuilder empresaBuilder = builderFactory.createBuilder();

        Agropecuario agro = new Agropecuario();

        Empresa nuevaEmpresa = null;
        try {
            nuevaEmpresa = empresaBuilder.agregarNombre("pepito")
                    .agregarRazonSocial("Jorge Lopez")
                    .agregarCuit("20302030L")
                    .agregarCodigoPostal(unaDireccionPostalDePrueba)
                    .agregarCantidadDePersonal(30)
                    .agregarActividad(new Agropecuario())
                    .agregarPromedioDeVentas((float)345429999)
                    .agregarCodigoDeInscripcion(13030)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(nuevaEmpresa);
        assertEquals("pepito", nuevaEmpresa.getNombreFicticio());
        assertEquals("Jorge Lopez", nuevaEmpresa.getRazonSocial());
        assertEquals("20302030", nuevaEmpresa.getCuit());
        assertEquals(unaDireccionPostalDePrueba, nuevaEmpresa.getDirPostal());
        assertEquals(30, nuevaEmpresa.getCantidadPersonal());
        assertEquals("Agropecuario" , nuevaEmpresa.getActividad().getNombre());
        assertEquals(345429999f, nuevaEmpresa.getPromedioVentas());
        assertEquals(TipoEmpresa.MEDIANATRAMO1, nuevaEmpresa.getTipo());
    }

    @Test
    public void crearCriterioDeEmpresaVacio(){
        this.setup();
        CriterioDeEmpresa unCriterio = new CriterioDeEmpresa("criterio de prueba", null, null);
        assertEquals("criterio de prueba", unCriterio.getNombre());
    }

    @Test
    public void crearUnaCategoria(){
        this.setup();
        Categoria unaCategoria = new Categoria("Descripcion de categoria");
        assertEquals("Descripcion de categoria", unaCategoria.getDescripcion());
    }

    @Test
    public void crearCriterioDeEmpresaConCategria(){
        this.setup();
        Categoria unaCategoria = new Categoria("Descripcion de una categoria");
        Categoria otraCategoria = new Categoria("Descripcion de otra categoria");
        List<Categoria> listaDeCategrias = new ArrayList<>();
        listaDeCategrias.add(unaCategoria);
        listaDeCategrias.add(otraCategoria);
        CriterioDeEmpresa unCriterio = new CriterioDeEmpresa("criterio de prueba", null, listaDeCategrias);
        assertEquals("Descripcion de una categoria", unCriterio.getCategorias().get(0).getDescripcion());
        assertEquals("Descripcion de otra categoria", unCriterio.getCategorias().get(1).getDescripcion());
    }

    @Test
    public void crearCriterioDeEmpresaConCriterios(){
        this.setup();
        CriterioDeEmpresa criteroHijoUno = new CriterioDeEmpresa("criterio hijo uno", null, null);
        CriterioDeEmpresa criteroHijoDos = new CriterioDeEmpresa("criterio hijo dos", null, null);
        List<CriterioDeEmpresa> listaDeCriterios = new ArrayList<>();
        listaDeCriterios.add(criteroHijoUno);
        listaDeCriterios.add(criteroHijoDos);
        CriterioDeEmpresa unCriterio = new CriterioDeEmpresa("criterio padre", listaDeCriterios, null);
        assertEquals("criterio hijo uno", unCriterio.getCriteriosHijos().get(0).getNombre());
        assertEquals("criterio hijo dos", unCriterio.getCriteriosHijos().get(1).getNombre());
    }

    @Test
    public void crearCriterioDeEmpresaConCriteriosMasCategorias(){
        this.setup();
        CriterioDeEmpresa criteroHijoUno = new CriterioDeEmpresa("criterio hijo uno", null, null);
        CriterioDeEmpresa criteroHijoDos = new CriterioDeEmpresa("criterio hijo dos", null, null);
        List<CriterioDeEmpresa> listaDeCriterios = new ArrayList<>();
        listaDeCriterios.add(criteroHijoUno);
        listaDeCriterios.add(criteroHijoDos);
        Categoria unaCategoria = new Categoria("Descripcion de una categoria");
        Categoria otraCategoria = new Categoria("Descripcion de otra categoria");
        List<Categoria> listaDeCategrias = new ArrayList<>();
        listaDeCategrias.add(unaCategoria);
        listaDeCategrias.add(otraCategoria);
        CriterioDeEmpresa unCriterio = new CriterioDeEmpresa("criterio padre", listaDeCriterios, listaDeCategrias);
        assertEquals("criterio hijo uno", unCriterio.getCriteriosHijos().get(0).getNombre());
        assertEquals("criterio hijo dos", unCriterio.getCriteriosHijos().get(1).getNombre());
        assertEquals("Descripcion de una categoria", unCriterio.getCategorias().get(0).getDescripcion());
        assertEquals("Descripcion de otra categoria", unCriterio.getCategorias().get(1).getDescripcion());
    }

    @Test
    public void organizacionCreaCriterio(){
        this.setup();
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", "2023123123", null, 1, null);
        organizacion.crearCriterioDeEmpresa("criterioDeOrganizacion", null, null);
        assertEquals("criterioDeOrganizacion", organizacion.getCriterios().get(0).getNombre());
    }

    @Test
    public void organizacionCreaCategoria(){
        Juridica organizacion = new Juridica("JuridicaEjemplar","Sarasa", "2023123123", null, 1, null);
        organizacion.crearCriterioDeEmpresa("criterioDeOrganizacion", null, null);
        CriterioDeEmpresa criterio =organizacion.getCriterios().get(0);
        organizacion.crearCategoria(criterio,"categoriaDeOrganizacion");
        assertEquals("categoriaDeOrganizacion", organizacion.getCriterios().get(0).getCategorias().get(0).getDescripcion());
    }


}
