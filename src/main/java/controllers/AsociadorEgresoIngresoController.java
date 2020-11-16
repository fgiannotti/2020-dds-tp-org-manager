package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociadorEgresoIngresoController {
    private RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private RepoOperacionesIngresos repoIngreso = new RepoOperacionesIngresos();


    public ModelAndView inicio(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        Map<String, Object> parametros = new HashMap<>();
        List<OperacionIngreso> operacionesIngreso = new ArrayList<OperacionIngreso>();
        operacionesIngreso.addAll(this.repoIngreso.getAll());
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>();
        operacionesEgreso.addAll(this.repoEgresos.getAll());
        parametros.put("ingresos", operacionesIngreso);
        parametros.put("egresos", operacionesEgreso);
        return new ModelAndView(parametros,"asociar.hbs");
    }

    public Response asociar(Request request, Response response) {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        int unIngreso = Integer. parseInt(request.queryParams("ingreso"));
        int unEgreso = Integer. parseInt(request.queryParams("egreso"));
        System.out.println(unIngreso);
        System.out.println(unEgreso);
        response.redirect("/home"); //success
        return response;
    }
}
