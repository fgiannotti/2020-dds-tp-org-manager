package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import server.Router;

public class AsociadorEgresoCategoriaController {

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"cargar-ingreso.hbs");
    }
    public Response altaIngreso(Request request, Response response) {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        String unaDescripcion = request.queryParams("descripcion");
        String unMonto = request.queryParams("monto");
        System.out.println(unaDescripcion);
        System.out.println(unMonto);
        //GuardarIngreso
        response.redirect("/home"); //Success
        return response;
    }
}
