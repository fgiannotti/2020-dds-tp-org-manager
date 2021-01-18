package controllers;

import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AsociadorEgresoCategoriaController {
    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"asociar-egreso-categoria.hbs");
    }
    //me parece que no tiene sentido este metodo
    public ModelAndView asociarCategoriaEgreso(Request request, Response response) throws Exception {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();

        String unaDescripcion = request.queryParams("descripcion");
        String unMonto = request.queryParams("monto");
        System.out.println(unaDescripcion);
        System.out.println(unMonto);
        response.redirect("/home"); //Success
        return new ModelAndView(parametros,"asociar-egreso-categoria.hbs");
    }
}
