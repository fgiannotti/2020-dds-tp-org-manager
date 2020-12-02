package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));
        System.out.printf("Puerto Encontrado %d",port);
        if (port == 0) {port=9000; }
        Spark.port(port);

        Router.init();
        DebugScreen.enableDebugScreen();
    }
}