package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import repositorios.*;
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


    public ModelAndView verIngreso(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);

        Usuario usuario = repoUsuarios.buscarPorNombre(request.cookie("user"));

        List<OperacionEgreso> egresos = this.repoEgresos.getAllByOrg(usuario.getOrganizacionALaQuePertenece());

        List<OperacionIngreso> ingresos = this.repoIngresos.getAllByOrg(usuario.getOrganizacionALaQuePertenece());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ingresos", ingresos);

        return new ModelAndView(parametros,"visualizar-ingresos.hbs");
    }

    public ModelAndView verEgreso(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);

        Usuario usuario = repoUsuarios.buscarPorNombre(request.cookie("user"));

        List<OperacionEgreso> egresos = this.repoEgresos.getAllByOrg(usuario.getOrganizacionALaQuePertenece());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("egresos", egresos);

        return new ModelAndView(parametros,"visualizar-egresos.hbs");
    }

}
