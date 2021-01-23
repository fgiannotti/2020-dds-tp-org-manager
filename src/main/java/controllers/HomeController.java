package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import server.Router;

public class HomeController {
    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        Router.CheckIfAuthenticated(request, response);
        String rol = request.cookie("rol");
        System.err.println("validador Done cookie:"+request.cookie("validarDone"));
        Boolean validarDone = Boolean.valueOf(request.cookie("validarDone"));
        System.err.println("validarDone: "+validarDone);
        if (!validarDone) {
            //probar leyendo BODY, esta cayendo en false
            Integer cargasInvalidas = Integer.parseInt(request.cookie("cargasInvalidas"));
            Integer cargasTotales = Integer.valueOf(request.cookie("cargasTotales"));

            parametros.put("validarDone",true);
            parametros.put("cargasInvalidas",cargasInvalidas);
            parametros.put("cargasTotales",cargasTotales);
        }

        if (rol.equals("revisor")){
            return new ModelAndView(parametros,"index-menu-revisor.hbs");
        }else{
            return new ModelAndView(parametros,"home.hbs");
        }
    }
}
