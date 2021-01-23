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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static void main(String[] args) {

        int port;
        try{ port = Integer.parseInt(System.getenv("PORT"));
        } catch( NumberFormatException e){ port=9000; }

        System.out.println("Puerto Encontrado:");
        System.out.println(port);

        if (port == 0) { port=9000; }
        Spark.port(port);
        persistirInicial();
        //DebugScreen.enableDebugScreen();
        Router.init();
        System.out.println("Fin configure");

    }

    public static void persistirInicial(){
         BandejaDeEntrada bandejaRevisores = new BandejaDeEntrada();
         BandejaDeEntrada bandeja2;

        //-- DIRECCIONES  --
        Ciudad caba = new Ciudad("CABA");
        Provincia provCABA = new Provincia("CABA");
        Pais arg = new Pais("Argentina", "AR");

        DireccionPostal dirEeafBA = new DireccionPostal(
                new Direccion("Medrano", 951, 0),
                caba, provCABA, arg);
        //-- ORGANIZACIONES  --

         Organizacion eeafBA = new Empresa("EAAF Oficina Central BA", "EAAF BA", "30152698572",
                dirEeafBA, 1, null, 150, new Construccion(), (float) 600000000.00);
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
         CriterioDeEmpresa lugarAplicacion = new CriterioDeEmpresa("Lugar de aplicacion", null, null,eeafBA);
         CriterioDeEmpresa causante = new CriterioDeEmpresa("Causante", null, null,eeafBA);
         CriterioDeEmpresa gastosMantenimiento = new CriterioDeEmpresa("Gastos de Mantenimiento", new ArrayList<>(Arrays.asList(lugarAplicacion)), null,eeafBA);
        //--  CATEGORIAS  --
         Categoria fachada = new Categoria("Fachada", gastosMantenimiento, null, null);
         Categoria interior = new Categoria("Interior", lugarAplicacion, null, null);
         Categoria humedad = new Categoria("Humedad", causante, null, null);
         List<Categoria> categoriasOpSerrentino = new ArrayList<>(Arrays.asList(fachada, interior, humedad));

        //--  PROVEEDORES  --
         Proveedor pintureriaREX = new Proveedor("Pinturerias REX", "", "Av. Gral. Las Heras 2140");
         Proveedor pintureriasSanJorge = new Proveedor("Pinturerias San Jorge", "", "  Av. Libertador 270");
         Proveedor pintureriasSerrentino = new Proveedor("Pinturerias Serrentino", "", "  Ayacucho 3056");
         Proveedor casaDelAudio = new Proveedor("La casa del Audio", "", "Av. Rivadavia 12090");
         Proveedor garbarino = new Proveedor("Garbarino", "", "Av. Chiclana 553");
         Proveedor ingenieriaComercialSRL = new Proveedor("Ingenieria Comercial SRL", "", "Av. Boedo 1091");
         Proveedor corralonLaprida = new Proveedor("Corralon Laprida", "", "Correa 1052");
         List<Proveedor> proveOpSerrentino = new ArrayList<>(Arrays.asList(pintureriaREX, pintureriasSanJorge, pintureriasSerrentino));

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


        //--  EGRESOS  --
         Comprobante compOpSerrentino = new Comprobante(itemsSerrentino);
         OperacionEgreso opSerrentino = new OperacionEgreso((float) 19949.7, "Egreso serrentino",
                proveOpSerrentino, creditoSerrentino, LocalDate.of(2020, 10, 3), "", compOpSerrentino, itemsSerrentino,
                3, Criterio.MENOR_VALOR, eeafBA, preliminaresOpSerrentino, categoriasOpSerrentino);

         OperacionEgreso opEdesur;
         OperacionEgreso opEdesur2;
         OperacionEgreso opMetrogas;
         OperacionEgreso opMetrogas2;
         OperacionEgreso opMitoas;
         OperacionEgreso opLap1;
         OperacionEgreso opLap2;
         OperacionEgreso opTelas;

        //--  INGRESOS  --
         OperacionIngreso ingresoDonacionTerceros = new OperacionIngreso((float) 20000.0,"Donacion de terceros.",LocalDate.of(2020,2,25),eeafBA);
         OperacionIngreso ingresoRimoli = new OperacionIngreso((float) 10000.0,"Donacion de Rimoli SA.",LocalDate.of(2020,5,2),eeafBA);
         OperacionIngreso ingresoGranImperio = new OperacionIngreso((float) 10000.0,"Donacion de Gran Imperio.",LocalDate.of(2020,8,3),eeafBA);

        //--  USUARIOS  --
         Usuario aroco = new Revisor("aroco","aroco20",eeafBA,bandejaRevisores);
        //--  EXTRAS  --
         List<Item> itemsOpSerr = new ArrayList<>();

        itemsOpSerr.addAll(itemsREX);
        itemsOpSerr.addAll(itemsSanJorge);
        itemsOpSerr.addAll(itemsREX);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(new CriterioDeEmpresa("test", null, null));
        try {
            EntityManagerHelper.getEntityManager().persist(opSerrentino.getOrganizacion());
            EntityManagerHelper.commit();
        }catch (Exception e){
            opSerrentino.getOrganizacion().setId(1);
            EntityManagerHelper.rollback();
        }

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(opSerrentino);
        EntityManagerHelper.commit();

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(aroco);
        EntityManagerHelper.commit();

        for (OperacionIngreso ing: new ArrayList<>(Arrays.asList(ingresoDonacionTerceros,ingresoGranImperio,ingresoRimoli))){
            ing.getOrganizacion().setId(1);
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(ing);
            EntityManagerHelper.commit();
        }
    }
}