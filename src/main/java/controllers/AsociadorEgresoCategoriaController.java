package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AsociadorEgresoCategoriaController {
    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"cargar-ingreso.hbs");
    }
    public Response altaIngreso(Request request, Response response) {
        String unaDescripcion = request.queryParams("descripcion");
        String unMonto = request.queryParams("monto");
        System.out.println(unaDescripcion);
        System.out.println(unMonto);
        //GuardarIngreso
        response.redirect("/home"); //Success
        return response;
    }
}
