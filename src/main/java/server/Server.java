package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));
        Spark.port(port);

        Router.init();
        DebugScreen.enableDebugScreen();
    }
}