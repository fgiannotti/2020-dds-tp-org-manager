package server;


import controllers.*;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        LoginController loginController = new LoginController();
        HomeController homeController = new HomeController();
        OperacionController operacionController = new OperacionController();
        AsociadorEgresoIngresoController asociadorEgresoIngresoController = new AsociadorEgresoIngresoController();
        AsociadorEgresoCategoriaController asociadorEgresoCategoriaController = new AsociadorEgresoCategoriaController();
        VerIngresoEgresoController verIngresoEgresoController = new VerIngresoEgresoController();
        BandejaDeEntradaController bandejaDeEntradaController = new BandejaDeEntradaController();

        Spark.get("/", loginController::inicio, Router.engine);

        Spark.post("/login", loginController::login);

        Spark.get("/logout", loginController::logout);

        Spark.get("/home", homeController::inicio, Router.engine);

        Spark.get("/CrearOperacionEgreso", operacionController::inicio, Router.engine);

        Spark.post("/CrearOperacionEgreso", operacionController::crearOperacionEgreso);

        Spark.get("/asocIngresoEgreso", asociadorEgresoIngresoController::inicio, Router.engine);

        Spark.post("/asocIngresoEgreso", asociadorEgresoIngresoController::asociar);

        Spark.get("/asocEgresoPresupuestoACategoria", asociadorEgresoCategoriaController::inicio, Router.engine);

        Spark.get("/visualizacionIngresoEgresoPorCat", verIngresoEgresoController::inicio, Router.engine);

        Spark.get("/BandejaDeMensajes", bandejaDeEntradaController::inicio, Router.engine);
    }
}