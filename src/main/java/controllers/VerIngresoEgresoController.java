package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import repositorios.RepoOperaciones;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerIngresoEgresoController {
    private RepoOperaciones repoOperacion = new RepoOperaciones();
    private RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private RepoOperacionesIngresos repoIngresos = new RepoOperacionesIngresos();
    private RepoUsuarios repoUsuarios = new RepoUsuarios();


    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        Usuario usuario = repoUsuarios.buscarPorNombre(request.cookie("user"));

        List<OperacionEgreso> egresos = this.repoEgresos.getAllByOrg(usuario.getOrganizacionALaQuePertenece());

        List<OperacionIngreso> ingresos = this.repoIngresos.getAllByOrg(usuario.getOrganizacionALaQuePertenece());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("egresos", egresos);
        parametros.put("ingresos", ingresos);

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
