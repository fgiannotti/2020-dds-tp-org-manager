/*import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Estrategias.*;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Debito;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;
import entidades.Operaciones.Proveedor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidacionesTest {
    public BandejaDeEntrada bandeja;
    public BandejaDeEntrada bandeja2;

    public Resultado resultado1;
    public Resultado resultado2;
    public Resultado resultado3;
    public Resultado resultado4;
    public Proveedor proveedor;

    public FiltroPorFecha filtroPorFecha;
    public FiltroPorEstado filtroPorEstadoNoLeido;
    public List<Filtro> filtros = new ArrayList<Filtro>();
    public ValidadorUno validador;

    public Presupuesto presupuesto;
    public OperacionEgreso egreso;
    public MedioDePago medioDePago;
    public List<Item> items = new ArrayList<Item>();
    public List<Articulo> articulos = new ArrayList<Articulo>();

    @BeforeAll
    public void Setup () {
        filtroPorFecha = new FiltroPorFecha(LocalDate.now(),bandeja);
        filtroPorEstadoNoLeido = new FiltroPorEstado(false,bandeja);
        bandeja = new BandejaDeEntrada(filtros);
        bandeja2 = new BandejaDeEntrada(filtros);

        validador = new ValidadorUno(bandeja2);
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores.add(proveedor);
        proveedor = new Proveedor("jorgito-provides","Docu","calle-falsa123");
        resultado1 = new Resultado(1,proveedores,true,true,true,false, LocalDate.now().minusDays(1),bandeja);
        resultado2 = new Resultado(2,proveedores,true,true,true,false, LocalDate.now(),bandeja);
        resultado3 = new Resultado(3,proveedores,true,true,true,true, LocalDate.now(),bandeja);
        resultado4 = new Resultado(4,proveedores,true,true,false,true, LocalDate.now().minusDays(1),bandeja);

        bandeja.guardarResultado(resultado2);
        bandeja.guardarResultado(resultado1);
        bandeja.guardarResultado(resultado3);

        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (float)price, "Agua de coco 500 ml", proveedor);
        articulos.add(articulo);
        Item aguitasDeCoco = new Item("refrescante", "Pack de aguitas de coco, vienen 5. Si, cinco.", articulos);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        medioDePago = new Debito("Visa debito", 1000);
        egreso = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, LocalDate.now(), "DNI", null, items,1, Criterio.MENOR_VALOR);
        presupuesto = new Presupuesto(items,2,(float)price,null,proveedor);

        egreso.agregarPresupuesto(presupuesto);
    }
    @Test
    public void BandejaMuestra1ResultadoFiltradoPorFechaYFueLeido() {
        this.Setup();
        filtros.add(filtroPorFecha);
        bandeja.mostrarMensajes();
        Assertions.assertEquals(true, resultado1.getFueLeido());
        Assertions.assertEquals(false, resultado2.getFueLeido());
        Assertions.assertEquals(true, resultado3.getFueLeido());
    }
    @Test
    public void BandejaMuestra2ResultadoFiltradoPorEstadoNoLeido() {
        this.Setup();
        filtros.add(filtroPorEstadoNoLeido);
        bandeja.setFiltros(filtros);
        bandeja.mostrarMensajes();
        Assertions.assertEquals(true, resultado1.getFueLeido());
        Assertions.assertEquals(true, resultado2.getFueLeido());
        Assertions.assertEquals(true, resultado3.getFueLeido());
    }
    @Test
    public void BandejaMuestra1ResultadoFiltradoPorEstadoNoLeidoYLosDemasNoFueronLeidos() {
        this.Setup();
        filtros.add(filtroPorEstadoNoLeido);
        filtros.add(filtroPorFecha);
        bandeja.setFiltros(filtros);
        bandeja.mostrarMensajes();
        Assertions.assertEquals(true, resultado1.getFueLeido());
        Assertions.assertEquals(false, resultado2.getFueLeido());
        Assertions.assertEquals(true, resultado3.getFueLeido());
    }
    @Test
    public void ValidadorUnoValidaOKYAgregaALaBandeja() {
        this.Setup();
        if (validador != null) {
            Boolean esValido = validador.validar(egreso);

            filtros.add(filtroPorEstadoNoLeido);
            bandeja2.setFiltros(filtros);
            bandeja2.mostrarMensajes();

            Assertions.assertEquals(true, esValido);
        }

    }

    @Test
    public void ValidadorUnoValidaCargaInvalidaYAgregaALaBandeja() {
        this.Setup();
        validador.setPresupuestosNecesarios(10);
        if (validador != null){
            Boolean esValido = validador.validar(egreso);

            filtros.add(filtroPorEstadoNoLeido);
            bandeja2.setFiltros(filtros);
            bandeja2.mostrarMensajes();

            Assertions.assertEquals(false, esValido);
        }

    }

    @Test
    public void ValidadorUnoValidaDetallesInvalidosYAgregaALaBandeja() {
        this.Setup();

        List<Item> itemsFeos = new ArrayList<Item>();
        itemsFeos.add(new Item("alguno","nombre1",new ArrayList<Articulo>()));
        presupuesto.setItems(itemsFeos);
        if (validador != null) {
            Boolean esValido = validador.validar(egreso);

            filtros.add(filtroPorEstadoNoLeido);
            bandeja2.setFiltros(filtros);
            bandeja2.mostrarMensajes();

            Assertions.assertEquals(false, esValido);
        }

    }

    @Test
    public void ValidadorUnoValidaCriterioInvalidoYAgregaALaBandeja() {
        this.Setup();

        Presupuesto presupuesto2 = new Presupuesto(new ArrayList<Item>(),4,(float)1,null,proveedor);
        egreso.agregarPresupuesto(presupuesto2);
        if (validador != null) {

            Boolean esValido = validador.validar(egreso);

            filtros.add(filtroPorEstadoNoLeido);
            bandeja2.setFiltros(filtros);
            bandeja2.mostrarMensajes();

            Assertions.assertEquals(false, esValido);

        }
    }

}
*/