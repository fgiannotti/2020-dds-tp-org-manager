package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        String host = "0.0.0.0";
        String port = System.getenv("PORT");
        if (port.equals("")){
            port = "8000";
        }
        Spark.port(Integer.parseInt(port));
        Router.init();
        DebugScreen.enableDebugScreen();
    }
}