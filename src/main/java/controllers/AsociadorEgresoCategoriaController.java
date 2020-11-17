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
        return new ModelAndView(parametros,"cargar-ingreso.hbs");
    }
    //me parece que no tiene sentido este metodo
    public Response altaIngreso(Request request, Response response) throws Exception {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        response.redirect("/home"); //Success
        return response;
    }
}
