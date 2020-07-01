import BandejaDeEntrada.BandejaDeEntrada;
import Estrategias.*;
import Items.Articulo;
import Items.Item;
import MedioDePago.Credito;
import Operaciones.Comprobante;
import Operaciones.OperacionEgreso;
import Operaciones.Proveedor;
import Scheduler.Orquestador;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
    OperacionEgreso operacionPrueba1 = new OperacionEgreso(310, "Compra navidenia", proveedorTest0, creditoTest1, new Date(), "Factura", comprobanteTest2, itemsTest1, 1,Criterio.MENOR_VALOR);

    @Test
    void testLevanta() throws InterruptedException {
        Orquestador orquestador = new Orquestador(10);
        List<Filtro> filtros = new ArrayList<Filtro>();
        Filtro unFiltro = new FiltroPorEstado(true);
        filtros.add(unFiltro);
        BandejaDeEntrada bandejaDeEntrada = new BandejaDeEntrada(filtros);
        Validador validador = new ValidadorUno(bandejaDeEntrada);
        orquestador.orquestraJob(validador, operacionPrueba1);
        while(true){
            System.out.println("Job esperando");
            Thread.sleep(5000);
        }
    }
}

