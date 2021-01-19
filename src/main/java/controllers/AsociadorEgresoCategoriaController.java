package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Organizaciones.Categoria;
import entidades.Usuarios.Usuario;
import repositorios.*;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociadorEgresoCategoriaController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoCategorias repoCategorias = new RepoCategorias();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();
    private Usuario user;

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        user = repoUsuarios.buscarPorNombre(request.cookie("user"));

        Map<String, Object> parametros = new HashMap<>();

        List<Categoria> categorias = repoCategorias.getAll();
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>(repoEgresos.getAllByOrg(user.getOrganizacionALaQuePertenece()));

        parametros.put("categorias", categorias);
        parametros.put("egresos", operacionesEgreso);
        return new ModelAndView(parametros,"asociar-egreso-categoria.hbs");
    }
    public ModelAndView asociarCategoriaEgreso(Request request, Response response) throws Exception {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();

        String[] bodyParams = request.body().split("&");

        ArrayList<Categoria> categorias = new ArrayList<>();
        ArrayList<OperacionEgreso> operacionesEgreso = new ArrayList<>();
        //obtengo los ingresos y egresos del body
        for (String param : bodyParams) {
            String[] paramFieldValue = param.split("=");
            if (paramFieldValue[0].equals("categoria")) {
                categorias.add(this.repoCategorias.find(Integer.parseInt(paramFieldValue[1])));
            }
            if (paramFieldValue[0].equals("egreso")) {
                operacionesEgreso.add(this.repoEgresos.get(Integer.parseInt(paramFieldValue[1])));
            }
        }

        for (OperacionEgreso egreso : operacionesEgreso) {
            try {
                repoEgresos.asociarCategorias(egreso, categorias);
            } catch (Exception e) {
                parametros.put("asociarECfail", true);
            }
        }
        parametros.put("refererAsociarEC", true);
        return new ModelAndView(parametros,"index-menu-revisor.hbs");
    }
}
