import Estrategias.Criterio;
import Items.Articulo;
import Items.Item;
import MedioDePago.MedioDePago;
import MedioDePago.Debito;
import Operaciones.*;
import Organizaciones.Comercio;
import Organizaciones.Empresa;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBTest {
    public Empresa organizacion;
    public OperacionEgreso operacion;
    public Proveedor proveedor;
    public MedioDePago medioDePago;
    public List<Item> items;
    public List<Articulo> articulos;
    public List<Proveedor> proveedores = new ArrayList<Proveedor>();

    @Before
    public void setup() {
        proveedor = new Proveedor("Nachito deliveries", "123123", "Calle falsa 123");
        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (float) price, "Agua de coco 500 ml", proveedor);
        articulos = new ArrayList<Articulo>();
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);
        Item aguitasDeCoco = new Item("refrescante", "Pack de aguitas de coco, vienen 5. Si, cinco.", articulos);
        items = new ArrayList<Item>() {
        };
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        proveedores.add(proveedor);
        medioDePago = new Debito("Visa debito", 1000);
        organizacion = new Empresa("La del claudio", "Claudio Perez", 1325011222, null, 300, 5, new Comercio(), (float) 20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, new Date(), "DNI", new Comprobante(items), items, 1, Criterio.MENOR_VALOR);
        organizacion.agregarOperacion(operacion);
    }

    @Test
    public void persistiendoIngreso() {
        OperacionIngreso ingreso = new OperacionIngreso(100,"desc");
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ingreso);
        EntityManagerHelper.commit();
    }

    @Test
    public void recuperandoIngreso(){
        OperacionIngreso ingreso = (OperacionIngreso) EntityManagerHelper.createQuery("from OperacionIngreso where id = 1").getSingleResult();
        Assert.assertEquals(100, ingreso.getMontoTotal());
    }

    @Test
    public void persistiendoEgreso() {
        this.setup();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(operacion);
        EntityManagerHelper.commit();
    }
    @Test
    public void recuperandoEgreso(){
        OperacionEgreso egreso = (OperacionEgreso) EntityManagerHelper.createQuery("from OperacionEgreso where id = 1").getSingleResult();
        Assert.assertEquals(1, egreso.getId());
    }
}
