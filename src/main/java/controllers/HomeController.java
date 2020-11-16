package controllers;

import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"index-menu-revisor.hbs");
    }
}