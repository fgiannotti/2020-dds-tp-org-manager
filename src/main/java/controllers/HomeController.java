package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();

        if(!request.cookie("id").equals(request.session().id())){
            System.out.printf("USUARIO NO AUTENTICADO, REDIRECT A LOGIN. cookie-id: %s, session-id: %s",request.cookie("id"),request.session().id());
            response.redirect("/");
        }
        String rol = request.cookie("rol");
        if (rol.equals("revisor")){
            return new ModelAndView(parametros,"index-menu-revisor.hbs");
        }else{
            return new ModelAndView(parametros,"home.hbs");
        }
    }
}