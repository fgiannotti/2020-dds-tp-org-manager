import entidades.Estrategias.Criterio;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Debito;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.Comprobante;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.Comercio;
import entidades.Organizaciones.Empresa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperacionesTest {
    public Empresa organizacion;
    public Operacion operacion;
    public Proveedor proveedor;
    public MedioDePago medioDePago;
    public List<Item> items;
    public List<Articulo> articulos;
    public List<Proveedor> proveedores = new ArrayList<Proveedor>();

    @BeforeAll
    public void setup(){
        proveedor = new Proveedor("Nachito deliveries", "123123", "Calle falsa 123");
        proveedores.add(proveedor);
        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (int)price, "Agua de coco 500 ml", proveedor);
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

        medioDePago = new Debito("Visa debito", "1000");
        organizacion = new Empresa("La del claudio", "Claudio Perez", "1325011222", null, 300, 5, new Comercio(), (float)20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedor, medioDePago, LocalDate.now(), "DNI", null, items,1, Criterio.MENOR_VALOR,null);
    }


    @Test
    public void laOperacionPuedeSerGuardadaSinComprobante(){
        this.setup();
        Assertions.assertDoesNotThrow( () -> {
            new OperacionEgreso(1000, "Pago de AGUITA", proveedor, medioDePago, LocalDate.now(), "DNI", null, items,1, Criterio.MENOR_VALOR, new ArrayList<>());
        });
    }

    @Test
    public void laOperacionPuedeSerGuardadaConComprobante(){
        this.setup();
        Comprobante comprobante = new Comprobante(this.items);
        Assertions.assertDoesNotThrow( () -> {
            new OperacionEgreso(1000, "Pago de AGUITA", proveedor, medioDePago, LocalDate.now(), "DNI", comprobante, items,1, Criterio.MENOR_VALOR, new ArrayList<>());
        });
    }

    @Test
    public void sePuedeObtenerProveedorDeUnaOperacion(){
        this.setup();
        String nombre = proveedor.getnombreApellidoRazon();
        OperacionEgreso OE = (OperacionEgreso) operacion;
        String nombreEnOperacion = proveedores.get(0).getnombreApellidoRazon();
        Assertions.assertEquals(nombre, nombreEnOperacion);
    }

    @Test
    public void sePuedeObtenerDetalleItemsDeUnaOperacion(){
        String item = new String();
        this.setup();
        OperacionEgreso OE = (OperacionEgreso)operacion;
        item = OE.getItems().get(0).toString();
        System.out.println(item);
        Assertions.assertEquals(item, this.items.get(0).toString());
    }

    @Test
    public void seRegistranLosDatosDelMedioDePago(){
        this.setup();
        OperacionEgreso OE = (OperacionEgreso) operacion;
        Assertions.assertEquals(this.medioDePago.getMedio(), OE.getMedioDePago().getMedio());
        Assertions.assertEquals(this.medioDePago.getNumero(), OE.getMedioDePago().getNumero());
    }


}