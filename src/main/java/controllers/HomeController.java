package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import server.Router;

public class HomeController {
    private Map<String,Object> parametrosAuxiliares;

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);
        String rol = request.cookie("rol");

        Map<String, Object> parametros = new HashMap<>();
        if (parametrosAuxiliares != null) {
            //probar leyendo BODY, esta cayendo en false

            parametros.put("validarDone",parametrosAuxiliares.get("validarDone"));
            parametros.put("cargasValidas",parametrosAuxiliares.get("cargasValidas"));
            parametros.put("cargasTotales",parametrosAuxiliares.get("cargasTotales"));
        }

        if (rol.equals("revisor")){
            return new ModelAndView(parametros,"index-menu-revisor.hbs");
        }else{
            return new ModelAndView(parametros,"home.hbs");
        }
    }

    public Map<String, Object> getParametrosAuxiliares() {
        return parametrosAuxiliares;
    }

    public void setParametrosAuxiliares(Map<String, Object> parametrosAuxiliares) {
        this.parametrosAuxiliares = parametrosAuxiliares;
    }
}
