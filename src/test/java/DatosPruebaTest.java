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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatosPruebaTest {
    public BandejaDeEntrada bandeja;
    public BandejaDeEntrada bandeja2;

    //-- DIRECCIONES  --
    Ciudad caba = new Ciudad("CABA");
    Provincia provCABA = new Provincia("CABA");
    Pais arg = new Pais("Argentina","AR");

    DireccionPostal dirEeafBA = new DireccionPostal(
            new Direccion("Medrano",951,0),
            caba,provCABA,arg);
    //-- ORGANIZACIONES  --

    public Organizacion eeafBA = new Empresa("EAAF Oficina Central BA","EAAF BA","30152698572",
            dirEeafBA,1,null,150,new Construccion(), (float) 600000000.00);
    //--  MEDIOS DE PAGO  --
    public MedioDePago creditoSerrentino = new Credito("Credito","4509953555233704");
    public MedioDePago debitoMitoas = new Debito("Debito","5031755734530604");
    public MedioDePago efectivoEdesur  = new AccountMoney("efectivo");
    public MedioDePago efectivoMetro  = new AccountMoney("efectivo");
    public MedioDePago efectivoMitoas  = new AccountMoney("efectivo");
    public MedioDePago efectivoIngCom  = new AccountMoney("efectivo");
    public MedioDePago efectivoLaprida  = new AccountMoney("efectivo");
    public MedioDePago efectivoTelas  = new AccountMoney("efectivo");
    //--  CRITERIOS DE EMPRESAS  --
    public CriterioDeEmpresa lugarAplicacion = new CriterioDeEmpresa("Lugar de aplicacion",null,null);
    public CriterioDeEmpresa causante = new CriterioDeEmpresa("Causante",null,null);
    public CriterioDeEmpresa gastosMantenimiento = new CriterioDeEmpresa("Gastos de Mantenimiento",new ArrayList<>(Arrays.asList(lugarAplicacion)),null);
    //--  CATEGORIAS  --
    public Categoria fachada = new Categoria("Fachada",gastosMantenimiento,null,null);
    public Categoria interior = new Categoria("Interior",lugarAplicacion,null,null);
    public Categoria humedad = new Categoria("Humedad",causante,null,null);
    public List<Categoria> categoriasOpSerrentino = new ArrayList<>(Arrays.asList(fachada,interior,humedad));

    //--  PROVEEDORES  --
    public Proveedor pintureriaREX = new Proveedor("Pinturerias REX","","Av. Gral. Las Heras 2140");
    public Proveedor pintureriasSanJorge = new Proveedor("Pinturerias San Jorge","","  Av. Libertador 270");
    public Proveedor pintureriasSerrentino = new Proveedor("Pinturerias Serrentino","","  Ayacucho 3056");
    public Proveedor casaDelAudio = new Proveedor("La casa del Audio","","Av. Rivadavia 12090");
    public Proveedor garbarino = new Proveedor("Garbarino","","Av. Chiclana 553");
    public Proveedor ingenieriaComercialSRL = new Proveedor("Ingenieria Comercial SRL","","Av. Boedo 1091");
    public Proveedor corralonLaprida = new Proveedor("Corralon Laprida","","Correa 1052");
    public List<Proveedor> proveOpSerrentino = new ArrayList<>(Arrays.asList(pintureriaREX,pintureriasSanJorge,pintureriasSerrentino));

    //--  PRESUPUESTOS  --
    public Articulo artRex20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9900.80);
    public Articulo artRex10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 7200.00);
    public Articulo artRex4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4350.80);

    public Item pinturaREX20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L","Pintura Z10 LATEX 20L",new ArrayList<>(Arrays.asList(artRex20L)));
    public Item pinturaREX10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L","Pintura LOXON FTES 10L",new ArrayList<>(Arrays.asList(artRex10L)));
    public Item pinturaREX4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L","Pintura BRIKOL PISOS 4L",new ArrayList<>(Arrays.asList(artRex4L)));
    public List<Item> itemsREX = new ArrayList<Item>(Arrays.asList(pinturaREX20L,pinturaREX10L,pinturaREX4l));
    public Presupuesto prepREX = new Presupuesto(itemsREX,3, (float) 19949.69,new Comprobante(itemsREX),pintureriaREX,null);

    public Articulo artSJ20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9610.50);
    public Articulo artSJ10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6590.30);
    public Articulo artSJ4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4100.00);
    public Item pinturaSJ20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L","Pintura Z10 LATEX 20L",new ArrayList<>(Arrays.asList(artSJ20L)));
    public Item pinturaSJ10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L","Pintura LOXON FTES 10L",new ArrayList<>(Arrays.asList(artSJ10L)));
    public Item pinturaSJ4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L","Pintura BRIKOL PISOS 4L",new ArrayList<>(Arrays.asList(artSJ4L)));
    public List<Item> itemsSanJorge = new ArrayList<Item>(Arrays.asList(pinturaSJ20L,pinturaSJ10L,pinturaSJ4l));
    public Presupuesto prepSanJorge = new Presupuesto(itemsSanJorge,3, (float) 19949.69,new Comprobante(itemsREX),pintureriasSanJorge,null);

    public Articulo artSerr20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9625.00);
    public Articulo artSerr10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6589.40);
    public Articulo artSerr4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 3738.29);
    public Item pinturaSerr20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L","Pintura Z10 LATEX 20L",new ArrayList<>(Arrays.asList(artSerr20L)));
    public Item pinturaSerr10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L","Pintura LOXON FTES 10L",new ArrayList<>(Arrays.asList(artSerr10L)));
    public Item pinturaSerr4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L","Pintura BRIKOL PISOS 4L",new ArrayList<>(Arrays.asList(artSerr4L)));
    public List<Item> itemsSerrentino = new ArrayList<>(Arrays.asList(pinturaSerr20L,pinturaSerr10L,pinturaSerr4l));
    public Presupuesto prepSerrentino = new Presupuesto(itemsSerrentino,3, (float) 19949.69,new Comprobante(itemsREX),pintureriasSerrentino,null);
    public List<Presupuesto> preliminaresOpSerrentino = new ArrayList<>(Arrays.asList(prepREX,prepSanJorge,prepSerrentino));


    //--  EGRESOS  --
    public OperacionEgreso opSerrentino = new OperacionEgreso(19950,"Egreso serrentino",
            proveOpSerrentino, creditoSerrentino, LocalDate.of(2020,10,3),"",new Comprobante(itemsSerrentino),itemsSerrentino,
            3,Criterio.MENOR_VALOR,eeafBA,preliminaresOpSerrentino,categoriasOpSerrentino);

    public OperacionEgreso opEdesur;
    public OperacionEgreso opEdesur2;
    public OperacionEgreso opMetrogas;
    public OperacionEgreso opMetrogas2;
    public OperacionEgreso opMitoas;
    public OperacionEgreso opLap1;
    public OperacionEgreso opLap2;
    public OperacionEgreso opTelas;


    @BeforeAll
    public void setup() {
        for (Categoria cat: categoriasOpSerrentino){
            cat.agregarEgreso(opSerrentino);
            cat.agregarPresupuesto(prepSerrentino);
        }
        for (Proveedor prov: proveOpSerrentino){
            prov.setEgreso(opSerrentino);
        }
        pintureriaREX.setPresupuesto(prepREX);
        pintureriasSanJorge.setPresupuesto(prepSanJorge);
        pintureriasSerrentino.setPresupuesto(prepSerrentino);
    }


    @Test
    public void persistiendoOperacionSERRENTINO() {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(new CriterioDeEmpresa("test",null,null));

        for (Categoria cat : opSerrentino.getCategorias()){
            EntityManagerHelper.getEntityManager().persist(cat);
        }
        for (Proveedor p : opSerrentino.getProveedores()){
            EntityManagerHelper.getEntityManager().persist(p);
        }
        EntityManagerHelper.getEntityManager().persist(opSerrentino);
        EntityManagerHelper.commit();
    }
}