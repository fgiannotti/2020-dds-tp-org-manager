import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.Estrategias.*;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Credito;
import entidades.Operaciones.Comprobante;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Proveedor;
import utils.Scheduler.Orquestador;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*

public class SchedulerTest {
    Proveedor proveedorTest0 = new Proveedor("Jorge Guaymallen", "6321456", "1714");
    Articulo articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
    Articulo articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest0);
    Articulo articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest0);
    List<Articulo> articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);
    Item itemTest1 = new Item("Auriculares, pendrive y salsa" ,"Promo1", articulos);
    List<Item> itemsTest1 = Arrays.asList(itemTest1);
    Comprobante comprobanteTest2 = new Comprobante(itemsTest1);
    Credito creditoTest1 = new Credito("Tarjeta de credito Santander", 20202020);
    List<Proveedor> proveedoresTest0 = new ArrayList<Proveedor>();
    OperacionEgreso operacionPrueba1 = new OperacionEgreso(310, "Compra navidenia", proveedoresTest0, creditoTest1, LocalDate.now(), "Factura", comprobanteTest2, itemsTest1, 1,Criterio.MENOR_VALOR);

    @Test
    void testLevanta() throws InterruptedException {
        Orquestador orquestador = new Orquestador(10);
        BandejaDeEntrada bandejaDeEntrada = new BandejaDeEntrada();

        List<Filtro> filtros = new ArrayList<Filtro>();
        Filtro unFiltro = new FiltroPorEstado(true,bandejaDeEntrada);
        filtros.add(unFiltro);


        bandejaDeEntrada.setFiltros(filtros);
        Validador validador = new ValidadorUno(bandejaDeEntrada);
        orquestador.orquestraJob(validador, operacionPrueba1);
        System.out.println("Job esperando");
        Thread.sleep(5000);
    }
}
*/
