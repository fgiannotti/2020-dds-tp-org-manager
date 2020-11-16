package server;


import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import controllers.*;
import spark.Request;
import spark.Response;
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
                .withHelper("inc", new Helper<Integer>() {
                    public Integer apply(Integer value, Options options){ return value+1;}
                })
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

        Spark.post("/asocEgresoPresupuestoACategoria", asociadorEgresoCategoriaController::altaIngreso);

        Spark.get("/visualizacionIngresoEgresoPorCat", verIngresoEgresoController::inicio, Router.engine);

        Spark.get("/ver-bandeja", bandejaDeEntradaController::inicio, Router.engine);

        Spark.post("/proveedor", asociadorEgresoCategoriaController::altaIngreso); //guardar Proveedor

        Spark.get("/CrearOperacionEgreso", operacionController::inicio, Router.engine);

    }

    public static void CheckIfAuthenticated(Request request, Response response){
        if(request.cookie("id") == null || !request.cookie("id").equals(request.session().id())){
            System.out.printf("USUARIO NO AUTENTICADO, REDIRECT A LOGIN. cookie-id: %s, session-id: %s",request.cookie("id"),request.session().id());
            response.redirect("/");
        }
    }
}