import Estrategias.Criterio;
import Items.Articulo;
import Items.Item;
import MedioDePago.Debito;
import MedioDePago.MedioDePago;
import Operaciones.Comprobante;
import Operaciones.Operacion;
import Operaciones.OperacionEgreso;
import Operaciones.Proveedor;
import Organizaciones.Comercio;
import Organizaciones.Empresa;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperacionesTest {
    public Empresa organizacion;
    public Operacion operacion;
    public Proveedor proveedor;
    public MedioDePago medioDePago;
    public List<Item> items;
    public List<Articulo> articulos;
    public List<Proveedor> proveedores = new ArrayList<Proveedor>();

    @Before
    public void setup(){
        proveedor = new Proveedor("Nachito deliveries", "123123", "Calle falsa 123");
        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (float)price, "Agua de coco 500 ml", proveedor);
        articulos = new ArrayList<Articulo>();
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);
        Item aguitasDeCoco = new Item("refrescante", "Pack de aguitas de coco, vienen 5. Si, cinco.", articulos);
        items = new ArrayList<Item>() {};
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        List<Proveedor> proveedorestest = new ArrayList<Proveedor>();
        proveedores = proveedorestest;
        proveedores.add(proveedor);
        medioDePago = new Debito("Visa debito", 1000);
        organizacion = new Empresa("La del claudio", "Claudio Perez", 1325011222, null, 300, 5, new Comercio(), (float)20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, new Date(), "DNI", null, items,1, Criterio.MENOR_VALOR);
        organizacion.agregarOperacion(operacion);
    }

    @Test
    public void laOperacionEsGuardadaCorrectamenteEnLaOrg() {
        this.setup();
        int tamanio = organizacion.getOperacionesRealizadas().size();
        int hash = this.operacion.hashCode();
        Operacion actualOperacion = organizacion.getOperacionesRealizadas().get(0);
        Assertions.assertEquals(1, tamanio);
        Assertions.assertEquals(hash, actualOperacion.hashCode());
    }

    @Test
    public void laOperacionPuedeSerGuardadaSinComprobante(){
        this.setup();
        Assertions.assertDoesNotThrow( () -> {
            new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, new Date(), "DNI", null, items,1, Criterio.MENOR_VALOR);
        });
    }

    @Test
    public void laOperacionPuedeSerGuardadaConComprobante(){
        this.setup();
        Comprobante comprobante = new Comprobante(this.items);
        Assertions.assertDoesNotThrow( () -> {
            new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, new Date(), "DNI", comprobante, items,1, Criterio.MENOR_VALOR);
        });
    }

    @Test
    public void sePuedeObtenerProveedorDeUnaOperacion(){
        this.setup();
        String nombre = proveedor.getNombre_apellido_razon();
        OperacionEgreso OE = (OperacionEgreso) organizacion.getOperacionesRealizadas().get(0);
        String nombreEnOperacion = proveedores.get(0).getNombre_apellido_razon();
        Assertions.assertEquals(nombre, nombreEnOperacion);
    }

    @Test
    public void sePuedeObtenerDetalleItemsDeUnaOperacion(){
        String item = new String();
        this.setup();
        OperacionEgreso OE = (OperacionEgreso) organizacion.getOperacionesRealizadas().get(0);
        item = OE.getItems().get(0).toString();
        System.out.println(item);
        Assertions.assertEquals(item, this.items.get(0).toString());
    }

    @Test
    public void seRegistranLosDatosDelMedioDePago(){
        this.setup();
        OperacionEgreso OE = (OperacionEgreso) organizacion.getOperacionesRealizadas().get(0);
        Assertions.assertEquals(this.medioDePago.getMedio(), OE.getMedioDePago().getMedio());
        Assertions.assertEquals(this.medioDePago.getNumero(), OE.getMedioDePago().getNumero());
    }


}