package server;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.DatosGeograficos.*;
import entidades.Estrategias.Criterio;
import entidades.Estrategias.Filtro;
import entidades.Estrategias.FiltroPorEstado;
import entidades.Estrategias.FiltroPorFecha;
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
import spark.Spark;
import spark.debug.DebugScreen;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static void main(String[] args) {

        int port;
        try {
            port = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException e) {
            port = 9000;
        }

        System.out.println("Puerto Encontrado:");
        System.out.println(port);

        if (port == 0) {
            port = 9000;
        }
        Spark.port(port);
        persistirInicial();
        //DebugScreen.enableDebugScreen();
        Router.init();
        System.out.println("Fin configure");

    }

    public static void persistirInicial() {
        BandejaDeEntrada bandejaRevisores = new BandejaDeEntrada();
        BandejaDeEntrada bandejaRevisoresCDIA = new BandejaDeEntrada();

        BandejaDeEntrada bandeja2;

        //-- DIRECCIONES  --
        Ciudad caba = new Ciudad("CABA");
        Provincia provCABA = new Provincia("CABA");
        Pais arg = new Pais("Argentina", "AR");

        DireccionPostal dirEeafBA = new DireccionPostal(new Direccion("Medrano", 951, 0), caba, provCABA, arg);

        DireccionPostal dirCDIA = new DireccionPostal(new Direccion("Jeronimo Salguero", 2800, 0), caba, provCABA, arg);
        //-- ORGANIZACIONES  --

        Organizacion eeafBA = new Empresa("EAAF Oficina Central BA", "EAAF BA", "30152698572",
                dirEeafBA, 1, null, 150, new Construccion(), (float) 600000000.00);
        Organizacion eeCDIA = new Empresa("Derechos de infancia y adolescencia", "Surcos CS", "30258888978",
                dirCDIA, 2, null, 8, new Construccion(), (float) 8000000.00);

        //--  MEDIOS DE PAGO  --
        MedioDePago creditoSerrentino = new Credito("CreditoSerrentino", "4509953555233704");
        MedioDePago debitoMitoas = new Debito("DebitoMitoas", "5031755734530604");
        MedioDePago efectivoEdesur = new AccountMoney("efectivoEdesur");
        MedioDePago efectivoMetro = new AccountMoney("efectivoMetro");
        MedioDePago efectivoMitoas = new AccountMoney("efectivoMitoas");
        MedioDePago efectivoIngCom = new AccountMoney("efectivoIngCom");
        MedioDePago efectivoLaprida = new AccountMoney("efectivoLaprida");
        MedioDePago efectivoTelas = new AccountMoney("efectivoTelas");

        //--  CRITERIOS DE EMPRESAS  --
        CriterioDeEmpresa lugarAplicacion = new CriterioDeEmpresa("Lugar de aplicacion", null, eeafBA);
        CriterioDeEmpresa causante = new CriterioDeEmpresa("Causante", null, eeafBA);
        CriterioDeEmpresa gastosMantenimiento = new CriterioDeEmpresa("Gastos de Mantenimiento", new ArrayList<>(Arrays.asList(lugarAplicacion)), eeafBA);
        CriterioDeEmpresa gastosGenerales = new CriterioDeEmpresa("Gastos generales", new ArrayList<>(Arrays.asList(lugarAplicacion)), eeafBA);
        CriterioDeEmpresa elementosOficina = new CriterioDeEmpresa("Elementos de oficina", null, eeafBA);
        CriterioDeEmpresa momentoUtilizacion = new CriterioDeEmpresa("Momento de utilización", null, eeafBA);
        CriterioDeEmpresa tipoProducto = new CriterioDeEmpresa("Tipo de producto", null, eeafBA);

        CriterioDeEmpresa critServicios = new CriterioDeEmpresa("Servicio", null, eeafBA);

        CriterioDeEmpresa critServiciosCDIA = new CriterioDeEmpresa("Servicio", null, eeCDIA);
        CriterioDeEmpresa critElementosInternos = new CriterioDeEmpresa("Elementos de uso Interno", null, eeCDIA);
        List<CriterioDeEmpresa> allcrits= new ArrayList<>(Arrays.asList(critServicios,critServiciosCDIA,tipoProducto,gastosGenerales,momentoUtilizacion));
        //--  CATEGORIAS  --
        Categoria fachada = new Categoria("Fachada", gastosMantenimiento);
        Categoria interior = new Categoria("Interior", lugarAplicacion);
        Categoria humedad = new Categoria("Humedad", causante);
        List<Categoria> categoriasOpSerrentino = new ArrayList<>(Arrays.asList(fachada, interior, humedad));

        Categoria servLuz = new Categoria("Servicios de Luz", critServiciosCDIA);
        Categoria servGas = new Categoria("Servicios de Gas", critServiciosCDIA);
        Categoria necesarios = new Categoria("Necesarios", critElementosInternos);
        List<Categoria> catsOpTelasZN = new ArrayList<>(Arrays.asList(necesarios));
        List<Categoria> allCats = new ArrayList<>(Arrays.asList(fachada,interior,humedad,servLuz,servGas,necesarios));

        //--  PROVEEDORES  --
        Proveedor pintureriaREX = new Proveedor("Pinturerias REX", "", "Av. Gral. Las Heras 2140");
        Proveedor pintureriasSanJorge = new Proveedor("Pinturerias San Jorge", "", "  Av. Libertador 270");
        Proveedor pintureriasSerrentino = new Proveedor("Pinturerias Serrentino", "", "  Ayacucho 3056");
        Proveedor casaDelAudio = new Proveedor("La casa del Audio", "", "Av. Rivadavia 12090");
        Proveedor garbarino = new Proveedor("Garbarino", "", "Av. Chiclana 553");
        Proveedor ingenieriaComercialSRL = new Proveedor("Ingenieria Comercial SRL", "", "Av. Boedo 1091");
        Proveedor corralonLaprida = new Proveedor("Corralon Laprida", "", "Correa 1052");
        Proveedor telasZN = new Proveedor("Telas ZN", "","Gorriti 3500");
        Proveedor edesur = new Proveedor("Edesur","","Av del Libertador 867, Merlo");
        Proveedor metrogas = new Proveedor("Edesur","","Av del Libertador 700, Merlo");
        List<Proveedor> proveOpSerrentino = new ArrayList<>(Arrays.asList(pintureriaREX, pintureriasSanJorge, pintureriasSerrentino));
        List<Proveedor> allProveedores = new ArrayList<>(Arrays.asList(pintureriaREX,pintureriasSanJorge,pintureriasSerrentino,casaDelAudio
        ,garbarino,ingenieriaComercialSRL,corralonLaprida,edesur,metrogas));

        //--  PRESUPUESTOS  --
        Articulo artRex20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9900.80, "pinturas 20L");
        Articulo artRex10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 7200.00, "pinturas 10L");
        Articulo artRex4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4350.80, "pinturas 4L");

        Item pinturaREX20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artRex20L)));
        Item pinturaREX10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artRex10L)));
        Item pinturaREX4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artRex4L)));
        List<Item> itemsREX = new ArrayList<Item>(Arrays.asList(pinturaREX20L, pinturaREX10L, pinturaREX4l));
        Presupuesto prepREX = new Presupuesto(itemsREX, 3, (float) 19949.69, pintureriaREX, null);

        Articulo artSJ20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9610.50, "pinturas 20L");
        Articulo artSJ10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6590.30, "pinturas 10L");
        Articulo artSJ4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 4100.00, "pinturas 4L");
        Item pinturaSJ20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artSJ20L)));
        Item pinturaSJ10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artSJ10L)));
        Item pinturaSJ4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artSJ4L)));
        List<Item> itemsSanJorge = new ArrayList<Item>(Arrays.asList(pinturaSJ20L, pinturaSJ10L, pinturaSJ4l));
        Presupuesto prepSanJorge = new Presupuesto(itemsSanJorge, 3, (float) 20300.8, pintureriasSanJorge, null);

        Articulo artSerr20L = new Articulo("Pintura Z10 LATEX 20L", (float) 9625.00, "pinturas 20L");
        Articulo artSerr10L = new Articulo("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", (float) 6589.40, "pinturas 10L");
        Articulo artSerr4L = new Articulo("PINTURA BRIKOL PISOS NEGRO 4L", (float) 3738.29, "pinturas 4L");
        Item pinturaSerr20L = new Item("PINTURA Z10 LATEX SUPERCUBRITIVO 20L", "Pintura Z10 LATEX 20L", new ArrayList<>(Arrays.asList(artSerr20L)));
        Item pinturaSerr10L = new Item("PINTURA LOXON FTES IMPERMEABILIZANTE 10L", "Pintura LOXON FTES 10L", new ArrayList<>(Arrays.asList(artSerr10L)));
        Item pinturaSerr4l = new Item("PINTURA BRIKOL PISOS NEGRO 4L", "Pintura BRIKOL PISOS 4L", new ArrayList<>(Arrays.asList(artSerr4L)));
        List<Item> itemsSerrentino = new ArrayList<>(Arrays.asList(pinturaSerr20L, pinturaSerr10L, pinturaSerr4l));
        Presupuesto prepSerrentino = new Presupuesto(itemsSerrentino, 3, (float) 19952.69, pintureriasSerrentino, null);
        List<Presupuesto> preliminaresOpSerrentino = new ArrayList<>(Arrays.asList(prepREX, prepSanJorge, prepSerrentino));

        Articulo artBlackout = new Articulo("Cortinas blackout vinilico 2 paños", (float) 1000.0, "cortinas 2 paños");
        Item itemBlackout = new Item("Cortinas blackout vinilico 2 años", "cortina item", new ArrayList<>(Arrays.asList(artBlackout)));
        List<Item> itemsTelasZN = new ArrayList<>(Arrays.asList(itemBlackout));


        //--  EGRESOS  --
        Comprobante compOpSerrentino = new Comprobante("120-120","facturaB",itemsSerrentino);

        OperacionEgreso opSerrentino = new OperacionEgreso((float) 19949.7, "Egreso serrentino",
                pintureriaREX, creditoSerrentino, LocalDate.of(2020, 10, 3), "", compOpSerrentino, itemsSerrentino,
                3, Criterio.MENOR_VALOR, eeafBA, preliminaresOpSerrentino, categoriasOpSerrentino);

        OperacionEgreso opEdesur;

        Comprobante compOPTelas = new Comprobante("12-12-40","factura",itemsTelasZN);
        OperacionEgreso opTelas = new OperacionEgreso((float) 4200.0, "Egreso con Telas ZN",
                telasZN,efectivoTelas,LocalDate.of(2020,9,25),"",compOPTelas,itemsTelasZN,
                0,Criterio.MENOR_VALOR,eeCDIA,null,catsOpTelasZN);

        //OperacionEgreso opEdesurCDIA = new OperacionEgreso(1100.0, "Egreso de CDIA con edesur",);
        OperacionEgreso opMetrogas;
        OperacionEgreso opMetrogas2;
        OperacionEgreso opMitoas;
        OperacionEgreso opLap1;
        OperacionEgreso opLap2;

        //--  INGRESOS  --
        OperacionIngreso ingresoDonacionTerceros = new OperacionIngreso((float) 20000.0, "Donacion de terceros.", LocalDate.of(2020, 2, 25), eeafBA);
        OperacionIngreso ingresoRimoli = new OperacionIngreso((float) 10000.0, "Donacion de Rimoli SA.", LocalDate.of(2020, 5, 2), eeafBA);
        OperacionIngreso ingresoGranImperio = new OperacionIngreso((float) 10000.0, "Donacion de Gran Imperio.", LocalDate.of(2020, 8, 3), eeafBA);
        OperacionIngreso ingresoGabino = new OperacionIngreso((float) 10000.0, "Donacion de Gabino SRL.", LocalDate.of(2020, 1, 10), eeCDIA);

        //--  USUARIOS  --
        Usuario aroco = new Revisor("aroco", "aroco20", eeafBA, bandejaRevisores);
        Usuario jazul = new Revisor("jazul", "jazul_", eeCDIA, bandejaRevisoresCDIA);

        //--  EXTRAS  --
        List<Item> itemsOpSerr = new ArrayList<>();

        itemsOpSerr.addAll(itemsREX);
        itemsOpSerr.addAll(itemsSanJorge);
        itemsOpSerr.addAll(itemsREX);
        EntityManager em = EntityManagerHelper.getEntityManager();
        em.getTransaction().begin();

        em.persist(opSerrentino.getOrganizacion());
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(aroco);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(jazul);
        em.getTransaction().commit();

        em.getTransaction().begin();
        for(Categoria cat:allCats){
            em.persist(cat);
            em.persist(cat);
        }
        for(CriterioDeEmpresa crit:allcrits){
            em.persist(crit);
        }
        for (Proveedor p : allProveedores) {
            em.persist(p);
        }
        em.persist(opSerrentino);

        em.persist(opTelas);
        em.getTransaction().commit();

        //persist ingresos
        for (OperacionIngreso ing : new ArrayList<>(Arrays.asList(ingresoDonacionTerceros, ingresoGranImperio, ingresoRimoli,ingresoGabino))) {
            em.getTransaction().begin();
            em.persist(ing);
            em.getTransaction().commit();
        }
    }
}