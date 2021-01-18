package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociadorCategoriaPresupuestoController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoOperacionesIngresos repoIngreso = new RepoOperacionesIngresos();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();

    private Usuario user;

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        String userName = request.cookie("user");
        user = repoUsuarios.buscarPorNombre(userName);

        Map<String, Object> parametros = new HashMap<>();

        List<OperacionIngreso> operacionesIngreso = new ArrayList<OperacionIngreso>(repoIngreso.getAllByOrg(user.getOrganizacionALaQuePertenece()));
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>(repoEgresos.getAllByOrg(user.getOrganizacionALaQuePertenece()));

        parametros.put("ingresos", operacionesIngreso);
        parametros.put("egresos", operacionesEgreso);

        return new ModelAndView(parametros,"asociar-presupuesto-categoria.hbs");
    }
    public Response asociarCategoriaPresupuesto(Request request, Response response){
        return response;
    }
}
