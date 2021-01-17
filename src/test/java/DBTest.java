import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.DatosGeograficos.*;
import entidades.Estrategias.*;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Debito;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.*;
import entidades.Organizaciones.*;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTest {
    public BandejaDeEntrada bandeja;
    public BandejaDeEntrada bandeja2;

    public Empresa organizacion;
    public OperacionEgreso operacion;
    public Proveedor proveedor;
    public MedioDePago medioDePago;
    public List<Item> items;
    public List<Articulo> articulos;
    public List<Proveedor> proveedores = new ArrayList<Proveedor>();
    public DireccionPostal direccionPostal = new DireccionPostal(new Direccion("calle123", 1500, 2), new Ciudad("ciudad1"), new Provincia("bs as"), new Pais("arg", "AR"));
    public Organizacion juridica = new Juridica("org-jur", "razon", "2222", direccionPostal, 12, null);
    public Usuario usuario1 = new User("pepito", "pep's-pass", juridica);
    public Revisor revisor1 = new Revisor("alfonso", "alfi123", juridica, bandeja2);
    public List<Usuario> usuarios = new ArrayList<Usuario>();
    public Organizacion juridica2 = new OSC("org-jur", "razon", "2222", direccionPostal, 12);
    public Organizacion orgBase = new Base("org-base", "AA SA", (Juridica) juridica);
    public Organizacion orgBase2 = new Base("org-base", "AA SA", (Juridica) juridica2);


    public Resultado resultado1;
    public Resultado resultado2;
    public Resultado resultado3;
    public Resultado resultado4;

    public FiltroPorFecha filtroPorFecha;
    public FiltroPorEstado filtroPorEstadoNoLeido;
    public List<Filtro> filtros = new ArrayList<Filtro>();

    @BeforeAll
    public void setup() {
        proveedor = new Proveedor("Nachito deliveries", "123123", "Calle falsa 123");
        double price = 50.0;
        Articulo articulo = new Articulo("CocoWater", (int) price, "Agua de coco 500 ml", proveedor);
        Item aguitasDeCoco = new Item("refrescante", "Pack de aguitas de coco, vienen 5. Si, cinco.", articulos);
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
        proveedores.add(proveedor);
        medioDePago = new Debito("Visa debito", "1000");
        OperacionIngreso ingreso = new OperacionIngreso(100, "desc", LocalDate.now(), orgBase);
        organizacion = new Empresa("La del claudio", "Claudio Perez", "1325011222", direccionPostal, 300, 5, new Comercio(), (float) 20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, LocalDate.now(), "DNI", new Comprobante(items), items, 1, Criterio.MENOR_VALOR,new ArrayList<>());
        operacion.setIngreso(ingreso);
        operacion.setOrganizacion(organizacion);
        organizacion.agregarOperacion(operacion);

        usuarios.add(usuario1);
        juridica.setUsuarios(usuarios);
        juridica2.setUsuarios(usuarios);
        orgBase.setUsuarios(usuarios);
        orgBase2.setUsuarios(usuarios);
        filtros.add(new FiltroPorFecha(LocalDate.now(), bandeja));
        filtroPorFecha = new FiltroPorFecha(LocalDate.now(), bandeja);
        filtroPorEstadoNoLeido = new FiltroPorEstado(false, bandeja);
        bandeja = new BandejaDeEntrada(filtros);
        bandeja2 = new BandejaDeEntrada(filtros);

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores.add(proveedor);
        proveedor = new Proveedor("jorgito-provides", "Docu", "calle-falsa123");
        resultado1 = new Resultado(1, proveedores, true, true, true, false, LocalDate.now().minusDays(1), bandeja);
        resultado2 = new Resultado(2, proveedores, true, true, true, false, LocalDate.now(), bandeja);
        resultado3 = new Resultado(3, proveedores, true, true, true, true, LocalDate.now(), bandeja);
        resultado4 = new Resultado(4, proveedores, true, true, false, true, LocalDate.now().minusDays(1), bandeja2);

        bandeja.guardarResultado(resultado2);
        bandeja.guardarResultado(resultado1);
        bandeja.guardarResultado(resultado3);
        bandeja2.guardarResultado(resultado4);

        revisor1.setBandejaDeEntrada(bandeja2);
    }

    @Test
    public void persistiendoIngreso() {
        OperacionIngreso ingreso = new OperacionIngreso(100, "desc", LocalDate.now(), orgBase2);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ingreso);
        EntityManagerHelper.commit();
    }

    @Test
    public void recuperandoIngreso() {
        OperacionIngreso ingreso = (OperacionIngreso) EntityManagerHelper.createQuery("from OperacionIngreso where id = 1").getSingleResult();
        assertEquals(100, ingreso.getMontoTotal());
    }

    @Test
    public void persistiendoEgreso() {
        this.setup();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(operacion);
        EntityManagerHelper.commit();
    }

    @Test
    public void recuperandoEgreso() {
        OperacionEgreso egreso = (OperacionEgreso) EntityManagerHelper.createQuery("from OperacionEgreso where id = 1").getSingleResult();
        assertEquals(1, egreso.getId());
    }

    @Test
    public void PersistiendoBandeja() {
        this.setup();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(bandeja);
        EntityManagerHelper.getEntityManager().persist(bandeja2);
        EntityManagerHelper.commit();
    }

    @Test
    public void recuperandoBandeja() {
        BandejaDeEntrada bandejaDeEntrada = (BandejaDeEntrada) EntityManagerHelper.createQuery("from BandejaDeEntrada where id = 2").getSingleResult();
        assertEquals(bandejaDeEntrada.getId(), 2);
        assertFalse(bandejaDeEntrada.getFiltros().isEmpty());
    }

    @Test
    public void PersistiendoRevisor() {
        this.setup();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(revisor1);
        EntityManagerHelper.commit();
    }

    @Test
    public void recuperandoRevisor() {
        Revisor alfonsito = (Revisor) EntityManagerHelper.createQuery("from Usuario where nombre = 'alfonso' and DTYPE = 'revisor'").setMaxResults(1).getSingleResult();
        alfonsito.verMensajes();
        BandejaDeEntrada bandejaDos = alfonsito.getBandejaDeEntrada();
        assertNotNull(bandejaDos);
    }
    @Test
    public void persistiendoCategoria(){
        Categoria cat = new Categoria("categoria-test");
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(cat);
        EntityManagerHelper.commit();
        assertTrue(cat.getId()>0);
    }
    @Test
    public void persistiendoPresupuesto() {
        List<Categoria> categorias = new ArrayList<Categoria>(Arrays.asList(
                new Categoria("golosinas"),
                new Categoria("bebidas")
        ));

        Presupuesto presupuesto = new Presupuesto(items, 4, (float) 1001.0,
                new Proveedor("cacho", "38420420", "calleCacho2B"), categorias
        );
        EntityManagerHelper.beginTransaction();
        for (Categoria cat : categorias){
            EntityManagerHelper.getEntityManager().persist(cat);
        }
        EntityManagerHelper.getEntityManager().persist(presupuesto);
        EntityManagerHelper.commit();
        Presupuesto presupuestoRecup = (Presupuesto) EntityManagerHelper.getEntityManager().createQuery("FROM Presupuesto WHERE id = '" + presupuesto.getId() + "'").getSingleResult();
        assertEquals(presupuesto, presupuestoRecup);
    }

}
