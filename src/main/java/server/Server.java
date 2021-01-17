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
        //DebugScreen.enableDebugScreen();
        Router.init();
        System.out.println("Fin configure");

    }

    public static void persistirInicial(){
    }
}