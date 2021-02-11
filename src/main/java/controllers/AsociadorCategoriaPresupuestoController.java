package controllers;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Presupuesto;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.CriterioDeEmpresa;
import entidades.Usuarios.Usuario;
import repositorios.*;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsociadorCategoriaPresupuestoController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoOperacionesIngresos repoIngreso = new RepoOperacionesIngresos();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();

    private final RepoCategorias repoCategorias = new RepoCategorias();
    private final RepoPresupuestos repoPresupuestos = new RepoPresupuestos();
    private final EntityManager em = EntityManagerHelper.getEntityManager();
    private Usuario user;

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        String userName = request.cookie("user");
        user = repoUsuarios.buscarPorNombre(userName);

        Map<String, Object> parametros = new HashMap<>();

        List<Integer> critIDs =new ArrayList<>();
        em.createQuery("FROM CriterioDeEmpresa WHERE org_id= '" + user.getOrganizacion().getId() + "'OR org_id IS NULL").getResultList().forEach((a) -> {
            critIDs.add(((CriterioDeEmpresa) a).getId());
        });

        List<Categoria> categorias = repoCategorias.getAllFromCritIDs(critIDs);
        List<Presupuesto> presupuestos = repoPresupuestos.getAllByOrg(user.getOrganizacion());

        parametros.put("categorias", categorias);
        parametros.put("presupuestos", presupuestos);

        return new ModelAndView(parametros,"asociar-presupuesto-categoria.hbs");
    }
    public ModelAndView asociarCategoriaPresupuesto(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();

        String[] bodyParams = request.body().split("&");

        ArrayList<Categoria> categorias = new ArrayList<>();
        ArrayList<Presupuesto> presupuestos = new ArrayList<>();

        for (String param : bodyParams) {
            String[] paramFieldValue = param.split("=");
            if (paramFieldValue[0].equals("categoria")) {
                categorias.add(this.repoCategorias.find(Integer.parseInt(paramFieldValue[1])));
            }
            if (paramFieldValue[0].equals("presupuesto")) {
                presupuestos.add(this.repoPresupuestos.find(Integer.parseInt(paramFieldValue[1])));
            }
        }

        for (Presupuesto presu : presupuestos) {
            try {
                repoPresupuestos.asociarCategorias(presu, categorias);
            } catch (Exception e) {
                parametros.put("asociarCPfail", true);
            }
        }

        parametros.put("refererAsociarCP", true);
        //response.redirect("/home");
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
    }
}
