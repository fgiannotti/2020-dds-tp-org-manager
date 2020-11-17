package controllers;

import entidades.Operaciones.Operacion;
import repositorios.RepoOperaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.Router;

public class VerIngresoEgresoController {
    private RepoOperaciones repoOperacion = new RepoOperaciones();


    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);
        List<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        operaciones.addAll(this.repo.getAllByUser (username));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("operaciones", operaciones);
        return new ModelAndView(parametros,"visualizar-egresos.hbs");

        //List<Operacion> operaciones = new ArrayList<Operacion>();
        //operaciones.addAll(this.repoOperacion.mostrarTodos());
        //Map<String, Object> parametros = new HashMap<>();
        //parametros.put("operaciones", operaciones);
        //return new ModelAndView(parametros,"visualizar-egresos.hbs");
    }
    //public Response filtrarPorMayor(Request request, Response response){
    //    List<OperacionEgreso> egresos = new ArrayList<OperacionEgreso>();
    //    egresos.addAll(this.repoOperacion.esMayor());
    //    return response;
    //}

    //public ModelAndView filtrar (Request request, Response response){

    //}

}
