/*import controllers.AsociadorEgresoIngresoController;
import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.DatosGeograficos.*;
import entidades.Estrategias.*;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.AccountMoney;
import entidades.MedioDePago.Credito;
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
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoPresupuestos;

import javax.persistence.EntityManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatosPruebaTest {
    public BandejaDeEntrada bandejaRevisores = new BandejaDeEntrada();
    public BandejaDeEntrada bandeja2;

    //-- DIRECCIONES  --
    Ciudad caba = new Ciudad("CABA");
    Provincia provCABA = new Provincia("CABA");
    Pais arg = new Pais("Argentina", "AR");

    DireccionPostal dirEeafBA = new DireccionPostal(
            new Direccion("Medrano", 951, 0),
            caba, provCABA, arg);
    //-- ORGANIZACIONES  --

    public Organizacion eeafBA = new Empresa("EAAF Oficina Central BA", "EAAF BA", "30152698572",
            dirEeafBA, 1, null, 150, new Construccion(), (float) 600000000.00);
    //--  MEDIOS DE PAGO  --
    public MedioDePago creditoSerrentino = new Credito("CreditoSerrentino", "4509953555233704");
    public MedioDePago debitoMitoas = new Debito("DebitoMitoas", "5031755734530604");
    public MedioDePago efectivoEdesur = new AccountMoney("efectivoEdesur");
    public MedioDePago efectivoMetro = new AccountMoney("efectivoMetro");
    public MedioDePago efectivoMitoas = new AccountMoney("efectivoMitoas");
    public MedioDePago efectivoIngCom = new AccountMoney("efectivoIngCom");
    public MedioDePago efectivoLaprida = new AccountMoney("efectivoLaprida");
    public MedioDePago efectivoTelas = new AccountMoney("efectivoTelas");

    //--  CRITERIOS DE EMPRESAS  --
    public CriterioDeEmpresa lugarAplicacion = new CriterioDeEmpresa("Lugar de aplicacion", null, null);
    public CriterioDeEmpresa causante = new CriterioDeEmpresa("Causante", null, null);
    public CriterioDeEmpresa gastosMantenimiento = new CriterioDeEmpresa("Gastos de Mantenimiento", new ArrayList<>(Arrays.asList(lugarAplicacion)), null);
    //--  CATEGORIAS  --
    public Categoria fachada = new Categoria("Fachada", gastosMantenimiento);
    public Categoria interior = new Categoria("Interior", lugarAplicacion);
    public Categoria humedad = new Categoria("Humedad", causante);
    public List<Categoria> categoriasOpSerrentino = new ArrayList<>(Arrays.asList(fachada, interior, humedad));

    //--  PROVEEDORES  --
    public Proveedor pintureriaREX = new Proveedor("Pinturerias REX", "", "Av. Gral. Las Heras 2140");
    public Proveedor pintureriasSanJorge = new Proveedor("Pinturerias San Jorge", "", "  Av. Libertador 270");
    public Proveedor pintureriasSerrentino = new Proveedor("Pinturerias Serrentino", "", "  Ayacucho 3056");
    public Proveedor casaDelAudio = new Proveedor("La casa del Audio", "", "Av. Rivadavia 12090");
    public Proveedor garbarino = new Proveedor("Garbarino", "", "Av. Chiclana 553");
    public Proveedor ingenieriaComercialSRL = new Proveedor("Ingenieria Comercial SRL", "", "Av. Boedo 1091");
    public Proveedor corralonLaprida = new Proveedor("Corralon Laprida", "", "Correa 1052");
    public List<Proveedor> proveOpSerrentino = new ArrayList<>(Arrays.asList(pintureriaREX, pintureriasSanJorge, pintureriasSerrentino));

    //--  PRESUPUESTOS  --
    public Articulo artRex20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9900.80, "pinturas 20L");
    public Articulo artRex10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 7200.00, "pinturas 10L");
    public Articulo artRex4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4350.80, "pinturas 4L");

    public Item pinturaREX20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artRex20L)));
    public Item pinturaREX10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artRex10L)));
    public Item pinturaREX4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artRex4L)));
    public List<Item> itemsREX = new ArrayList<Item>(Arrays.asList(pinturaREX20L, pinturaREX10L, pinturaREX4l));
    public Presupuesto prepREX = new Presupuesto(itemsREX, 3, (float) 19949.69, pintureriaREX, null);

    public Articulo artSJ20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9610.50, "pinturas 20L");
    public Articulo artSJ10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6590.30, "pinturas 10L");
    public Articulo artSJ4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4100.00, "pinturas 4L");
    public Item pinturaSJ20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artSJ20L)));
    public Item pinturaSJ10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artSJ10L)));
    public Item pinturaSJ4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artSJ4L)));
    public List<Item> itemsSanJorge = new ArrayList<Item>(Arrays.asList(pinturaSJ20L, pinturaSJ10L, pinturaSJ4l));
    public Presupuesto prepSanJorge = new Presupuesto(itemsSanJorge, 3, (float) 20300.8, pintureriasSanJorge, null);

    public Articulo artSerr20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9625.00, "pinturas 20L");
    public Articulo artSerr10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6589.40, "pinturas 10L");
    public Articulo artSerr4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 3738.29, "pinturas 4L");
    public Item pinturaSerr20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artSerr20L)));
    public Item pinturaSerr10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artSerr10L)));
    public Item pinturaSerr4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artSerr4L)));
    public List<Item> itemsSerrentino = new ArrayList<>(Arrays.asList(pinturaSerr20L, pinturaSerr10L, pinturaSerr4l));
    public Presupuesto prepSerrentino = new Presupuesto(itemsSerrentino, 3, (float) 19952.69, pintureriasSerrentino, null);
    public List<Presupuesto> preliminaresOpSerrentino = new ArrayList<>(Arrays.asList(prepREX, prepSanJorge, prepSerrentino));


    //--  EGRESOS  --
    public Comprobante compOpSerrentino = new Comprobante(itemsSerrentino);
    public OperacionEgreso opSerrentino = new OperacionEgreso((float) 19949.7, "Egreso serrentino",
            pintureriaREX, creditoSerrentino, LocalDate.of(2020, 10, 3), "", compOpSerrentino, itemsSerrentino,
            3, Criterio.MENOR_VALOR, eeafBA, preliminaresOpSerrentino, categoriasOpSerrentino);

    public OperacionEgreso opEdesur;
    public OperacionEgreso opEdesur2;
    public OperacionEgreso opMetrogas;
    public OperacionEgreso opMetrogas2;
    public OperacionEgreso opMitoas;
    public OperacionEgreso opLap1;
    public OperacionEgreso opLap2;
    public OperacionEgreso opTelas;

    //--  INGRESOS  --
    public OperacionIngreso ingresoDonacionTerceros = new OperacionIngreso((float) 20000.0,"Donacion de terceros.",LocalDate.of(2020,2,25),eeafBA);
    public OperacionIngreso ingresoRimoli = new OperacionIngreso((float) 10000.0,"Donacion de Rimoli SA.",LocalDate.of(2020,5,2),eeafBA);
    public OperacionIngreso ingresoGranImperio = new OperacionIngreso((float) 10000.0,"Donacion de Gran Imperio.",LocalDate.of(2020,8,3),eeafBA);

    //--  USUARIOS  --

    public Usuario aroco = new Revisor("aroco","aroco20",eeafBA,bandejaRevisores);
    //--  EXTRAS  --
    public List<Item> itemsOpSerr = new ArrayList<>();

    @BeforeAll
    public void setup() {
        itemsOpSerr.addAll(itemsREX);
        itemsOpSerr.addAll(itemsSanJorge);
        itemsOpSerr.addAll(itemsREX);

    }
    @Test
    public void persistiendoIngresos(){
        for (OperacionIngreso ing: new ArrayList<>(Arrays.asList(ingresoDonacionTerceros,ingresoGranImperio,ingresoRimoli))){
            ing.getOrganizacion().setId(1);
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(ing);
            EntityManagerHelper.commit();
        }

    }
    @Test
    public void persistiendoUsuarios(){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(aroco);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistiendoOperacionSERRENTINO() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        em.getTransaction().begin();
        em.persist(new CriterioDeEmpresa("test", null, null));
        try {
            em.persist(opSerrentino.getOrganizacion());
            em.getTransaction().commit();
        }catch (Exception e){
            opSerrentino.getOrganizacion().setId(1);
            em.getTransaction().rollback();
        }
        MedioDePago mp = opSerrentino.getMedioDePago();
        em.getTransaction().begin();
        OperacionEgreso op = new OperacionEgreso((int) 1111.0,"desc",pintureriaREX,mp,LocalDate.now(),"",null,itemsSerrentino,1,Criterio.MENOR_VALOR,preliminaresOpSerrentino);
        EntityManagerHelper.getEntityManager().persist(op);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistiendoComprobante() {
        Comprobante comprobante1 = new Comprobante(itemsSerrentino);

        EntityManagerHelper.beginTransaction();
        System.out.println(itemsSerrentino);
        EntityManagerHelper.getEntityManager().persist(comprobante1);
        EntityManagerHelper.commit();
    }

    @Test
    public void persistiendoPresupuesto() {

        EntityManagerHelper.beginTransaction();
        System.out.println(prepSerrentino);

        EntityManagerHelper.getEntityManager().persist(prepSerrentino);
        EntityManagerHelper.commit();
    }

    @Test
    public void asociarIngresoEgreso() throws Exception {
        RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
        opSerrentino.setId(11);
        ingresoDonacionTerceros.setId(1);
        repoEgresos.asociarIngreso(opSerrentino,ingresoDonacionTerceros);

    }
    @Test
    public void asociarCategoriasEgreso() throws Exception {
        RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
        opSerrentino.setId(11);
        repoEgresos.asociarCategorias(opSerrentino, (ArrayList<Categoria>) categoriasOpSerrentino);

    }
    @Test
    public void asociarCategoriasPresupuesto() throws Exception {
        RepoPresupuestos repoPresupuestos = new RepoPresupuestos();
        opSerrentino.setId(11);
        repoPresupuestos.asociarCategorias(prepSerrentino, (ArrayList<Categoria>) categoriasOpSerrentino);

    }
}*/