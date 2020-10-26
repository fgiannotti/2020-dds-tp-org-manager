import DatosGeograficos.*;
import Estrategias.Criterio;
import Items.Articulo;
import Items.Item;
import MedioDePago.MedioDePago;
import MedioDePago.Debito;
import Operaciones.*;
import Organizaciones.*;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
    public DireccionPostal direccionPostal = new DireccionPostal(new Direccion("calle123",1500,2),new Ciudad("ciudad1"),new Provincia("bs as"),new Pais("arg","AR"));

    public Organizacion juridica = new Juridica("org-jur","razon",2222,direccionPostal,12,null);
    public Organizacion juridica2 = new OSC("org-jur","razon",2222,direccionPostal,12);
    public Organizacion orgBase = new Base("org-base","AA SA", (Juridica) juridica);
    public Organizacion orgBase2 = new Base("org-base","AA SA", (Juridica) juridica2);

    @Before
    public void setup() {
        proveedor = new Proveedor("Nachito deliveries", "123123", "Calle falsa 123");
        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (float) price, "Agua de coco 500 ml", proveedor);
        Item aguitasDeCoco = new Item("refrescante", "Pack de aguitas de coco, vienen 5. Si, cinco.", articulos);
        articulo.setItem(aguitasDeCoco);
        articulos = new ArrayList<Articulo>();
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);
        articulos.add(articulo);

        items = new ArrayList<Item>() {
        };
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        items.add(aguitasDeCoco);
        aguitasDeCoco.setComprobante(new Comprobante(items));
        proveedores.add(proveedor);
        medioDePago = new Debito("Visa debito", 1000);
        OperacionIngreso ingreso = new OperacionIngreso(100,"desc",LocalDate.now(),orgBase);
        organizacion = new Empresa("La del claudio", "Claudio Perez", 1325011222, direccionPostal, 300, 5, new Comercio(), (float) 20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, LocalDate.now(), "DNI", new Comprobante(items), items, 1, Criterio.MENOR_VALOR);
        operacion.setIngreso(ingreso);
        operacion.setOrganizacion(organizacion);
        organizacion.agregarOperacion(operacion);
    }

    @Test
    public void persistiendoIngreso() {
        OperacionIngreso ingreso = new OperacionIngreso(100,"desc",LocalDate.now(),orgBase2);
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
