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
import entidades.MedioDePago.Debito;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.Comprobante;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.*;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;
import spark.Spark;
import spark.debug.DebugScreen;

import java.time.LocalDate;
import java.util.ArrayList;
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
        //persistirInicial();
        Router.init();
        System.out.println(port);
        DebugScreen.enableDebugScreen();
    }

    public static void persistirInicial(){


         BandejaDeEntrada bandeja = new BandejaDeEntrada();
         BandejaDeEntrada bandeja2 = new BandejaDeEntrada();

         Empresa organizacion;
         OperacionEgreso operacion;
         Proveedor proveedor;
         MedioDePago medioDePago;
         List<Item> items = new ArrayList<Item>();;
         List<Articulo> articulos = new ArrayList<Articulo>();;
         List<Proveedor> proveedores = new ArrayList<Proveedor>();
         DireccionPostal direccionPostal = new DireccionPostal(new Direccion("calle123",1500,2),new Ciudad("ciudad1"),new Provincia("bs as"),new Pais("arg","AR"));
         Organizacion juridica = new Juridica("org-jur","razon",2222,direccionPostal,12,null);
         Usuario usuario1 = new User("pepito", "pep's-pass", juridica);
         Revisor revisor1 = new Revisor("alfonso","alfi123",juridica,bandeja2);
         List<Usuario> usuarios = new ArrayList<Usuario>();
         Organizacion juridica2 = new OSC("org-jur","razon",2222,direccionPostal,12);
         Organizacion orgBase = new Base("org-base","AA SA", (Juridica) juridica);
         Organizacion orgBase2 = new Base("org-base","AA SA", (Juridica) juridica2);
         Resultado resultado1;
         Resultado resultado2;
         Resultado resultado3;
         Resultado resultado4;
         FiltroPorFecha filtroPorFecha;
         FiltroPorEstado filtroPorEstadoNoLeido;
         OperacionIngreso ingreso;

         List<Filtro> filtros = new ArrayList<Filtro>();

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
        ingreso = new OperacionIngreso(100,"desc",LocalDate.now(),orgBase);
        organizacion = new Empresa("La del claudio", "Claudio Perez", 1325011222, direccionPostal, 300, 5, new Comercio(), (float) 20000.0);
        operacion = new OperacionEgreso(1000, "Pago de AGUITA", proveedores, medioDePago, LocalDate.now(), "DNI", new Comprobante(items), items, 1, Criterio.MENOR_VALOR);
        operacion.setIngreso(ingreso);
        operacion.setOrganizacion(organizacion);
        organizacion.agregarOperacion(operacion);

        usuarios.add(usuario1);
        juridica.setUsuarios(usuarios);
        juridica2.setUsuarios(usuarios);
        orgBase.setUsuarios(usuarios);
        orgBase2.setUsuarios(usuarios);
        filtros.add(new FiltroPorFecha(LocalDate.now(),bandeja));
        filtroPorFecha = new FiltroPorFecha(LocalDate.now(),bandeja);
        filtroPorEstadoNoLeido = new FiltroPorEstado(false,bandeja);
        bandeja = new BandejaDeEntrada(filtros);
        bandeja2 = new BandejaDeEntrada(filtros);

        proveedores.add(proveedor);
        proveedor = new Proveedor("jorgito-provides","Docu","calle-falsa123");
        resultado1 = new Resultado(1,proveedores,true,true,true,false, LocalDate.now().minusDays(1),bandeja);
        resultado2 = new Resultado(2,proveedores,true,true,true,false, LocalDate.now(),bandeja);
        resultado3 = new Resultado(3,proveedores,true,true,true,true, LocalDate.now(),bandeja);
        resultado4 = new Resultado(4,proveedores,true,true,false,true, LocalDate.now().minusDays(1),bandeja2);

        bandeja.guardarResultado(resultado2);
        bandeja.guardarResultado(resultado1);
        bandeja.guardarResultado(resultado3);
        bandeja2.guardarResultado(resultado4);

        revisor1.setBandejaDeEntrada(bandeja2);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ingreso);
        EntityManagerHelper.commit();

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(operacion);
        EntityManagerHelper.commit();
    }
}