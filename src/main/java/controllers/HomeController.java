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
        if (rol.equals("revisor")){
            return new ModelAndView(parametros,"index-menu-revisor.hbs");
        }else{
            return new ModelAndView(parametros,"home.hbs");
        }
    }
}
