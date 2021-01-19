package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Organizaciones.Categoria;
import repositorios.RepoCategorias;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AsociadorEgresoCategoriaController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoCategorias repoCategorias = new RepoCategorias();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        Map<String, Object> parametros = new HashMap<>();
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
                parametros.put("asociar-fallo", true);
            }
        }
        return new ModelAndView(parametros,"asociar-egreso-categoria.hbs");
    }
}
