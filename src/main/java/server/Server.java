package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        int port;
        try{ port = Integer.parseInt(System.getenv("PORT"));
        } catch( NumberFormatException e){ port=9000; }

        System.out.printf("Puerto Encontrado %d",port);
        if (port == 0) { port=9000; }
        Spark.port(port);

        Router.init();
        DebugScreen.enableDebugScreen();
    }
}