package server;


import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import controllers.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
        BasicConfigurator.configure(); //te lo deja con debug
        Logger.getRootLogger().setLevel(Level.INFO);
        Router.initEngine();
        Spark.staticFileLocation("/public");
        System.out.println("Configure");
        Router.configure();
    }

    private static void configure() {
        LoginController loginController = new LoginController();
        HomeController homeController = new HomeController();
        OperacionController operacionController = new OperacionController();
        OperacionIngresoController operacionIngresoController = new OperacionIngresoController();
        AsociadorEgresoIngresoController asociadorEgresoIngresoController = new AsociadorEgresoIngresoController();
        AsociadorEgresoCategoriaController asociadorEgresoCategoriaController = new AsociadorEgresoCategoriaController();
        AsociadorCategoriaPresupuestoController asociadorCategoriaPresupuestoController = new AsociadorCategoriaPresupuestoController();
        ValidadorController validadorController = new ValidadorController();
        VerIngresoEgresoController verIngresoEgresoController = new VerIngresoEgresoController();
        BandejaDeEntradaController bandejaDeEntradaController = new BandejaDeEntradaController();
        ProveedorController proveedorController = new ProveedorController();
        ArticuloController articuloController = new ArticuloController();

        Spark.get("/", loginController::inicio, Router.engine);

        Spark.post("/", loginController::login,Router.engine);

        Spark.get("/logout", loginController::logout);

        Spark.get("/home", homeController::inicio, Router.engine);

        Spark.get("/crearOperacionEgreso", operacionController::inicio, Router.engine);
        Spark.post("/upload/:id",operacionController::upload);

        Spark.post("/crearOperacionEgreso", proveedorController::inicio);

        Spark.get("/asocHome", asociadorEgresoIngresoController::inicio, Router.engine);

        Spark.get("/asocIngresoEgreso", asociadorEgresoIngresoController::inicio, Router.engine);
        Spark.post("/asocIngresoEgreso", asociadorEgresoIngresoController::asociarIngresoEgreso,Router.engine);

        Spark.get("/asocCategoriaEgreso",asociadorEgresoCategoriaController::inicio,Router.engine);
        Spark.post("/asocCategoriaEgreso",asociadorEgresoCategoriaController::asociarCategoriaEgreso,Router.engine);
        Spark.post("/cacheCategoria",asociadorEgresoCategoriaController::agregarCategoria,Router.engine);

        Spark.get("/asocCategoriaPresupuesto",asociadorCategoriaPresupuestoController::inicio,Router.engine);
        Spark.post("/asocCategoriaPresupuesto",asociadorCategoriaPresupuestoController::asociarCategoriaPresupuesto,Router.engine);


        Spark.get("/ingreso", operacionIngresoController::inicio, Router.engine);
        Spark.post("/ingreso", operacionIngresoController::altaIngreso);


        Spark.get("/ver-ingresos", verIngresoEgresoController::verIngreso, Router.engine);
        Spark.get("/ver-egresos", verIngresoEgresoController::verEgreso, Router.engine);
        Spark.get("/egreso/:id",operacionController::verEgreso,Router.engine);
        Spark.get("/ingreso/:id", operacionIngresoController::verIngreso, Router.engine);

        Spark.get("/ver-bandeja", bandejaDeEntradaController::inicio, Router.engine);

        Spark.post("/altaProveedor", proveedorController::altaProveedor);

        Spark.post("/validar",validadorController::validar,Router.engine);

        Spark.get("/CrearOperacionEgreso", operacionController::inicio, Router.engine);
        Spark.get("/crearEgreso1", operacionController::fechaYCantidad, Router.engine);
        Spark.post("/crearEgreso1", operacionController::postProveedorFechaYCantMin);

        Spark.get("/crearEgreso2", operacionController::seleccionarProveedor, Router.engine);
        Spark.post("/cachePresupuestos",operacionController::cachePresupuesto,Router.engine);
        Spark.post("/crearEgreso2", operacionController::postSeleccionarPresupuesto);

        Spark.get("/crearEgreso3", operacionController::medioDePago, Router.engine);
        Spark.post("/crearEgreso3", operacionController::postMedioDePago);

        Spark.get("/crearEgreso4", articuloController::articulos, Router.engine);
        Spark.post("/crearEgreso4", articuloController::postArticulos);

        Spark.get("/crearEgreso5", operacionController::seleccionArticulos, Router.engine);
        Spark.post("/crearEgreso5", operacionController::postSeleccionArticulos);

        Spark.get("/crearEgreso6", operacionController::cargarComprobante, Router.engine);
        Spark.post("/crearEgreso6", operacionController::postCargarComprobante);

        Spark.get("/crearEgreso7", operacionController::cargarCriterio, Router.engine);
        Spark.post("/crearEgreso7", operacionController::postCargarCriterio);

        Spark.get("/crearEgreso8", operacionController::cargarCriterioComplejo, Router.engine);
        Spark.post("/crearEgreso8", operacionController::postCargarCriterioComplejo);

        Spark.get("/crearEgreso9", operacionController::cargarOrganizacion, Router.engine);
        Spark.post("/crearEgreso9", operacionController::postCargarOrganizacion);

    }
    
        public static void CheckIfAuthenticated(Request request, Response response){
        if(request.cookie("id") == null || !request.cookie("id").equals(request.session().id())){
            System.out.printf("USUARIO NO AUTENTICADO, REDIRECT A LOGIN. cookie-id: %s, session-id: %s",request.cookie("id"),request.session().id());
            response.redirect("/");
        }
    }	    
}
