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

import javax.persistence.NoResultException;
import java.util.*;

public class AsociadorEgresoCategoriaController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoCategorias repoCategorias = new RepoCategorias();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();
    private Usuario user;
    //cache de categorias creadas durante un session ID
    private Map<String, List<Categoria>> categoriasCache = new HashMap<>();

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        user = repoUsuarios.buscarPorNombre(request.cookie("user"));

        Map<String, Object> parametros = new HashMap<>();

        List<Categoria> categorias = repoCategorias.getAll();
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>(repoEgresos.getAllByOrg(user.getOrganizacionALaQuePertenece()));
        List<Categoria> cats = categoriasCache.get(request.session().id());
        if (cats != null) {
            categorias.addAll(cats);
        }
        parametros.put("categorias", categorias);
        parametros.put("egresos", operacionesEgreso);
        return new ModelAndView(parametros, "asociar-egreso-categoria.hbs");
    }

    public ModelAndView asociarCategoriaEgreso(Request request, Response response) throws Exception {
        if (!request.cookie("id").equals(request.session().id())) {
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
                Integer categoriaID = Integer.parseInt(paramFieldValue[1]);
                try {
                    categorias.add(this.repoCategorias.find(Integer.parseInt(paramFieldValue[1])));
                } catch (NoResultException e) {
                    Categoria categoriaFound = findCategoriaInCacheForUser(request.session().id(), categoriaID);
                    //revert id change to get an auto incremented one
                    categoriaFound.setId(0);
                    repoCategorias.persistCategoria(categoriaFound);
                    categorias.add(categoriaFound);
                }
            }
            if (paramFieldValue[0].equals("egreso")) {
                operacionesEgreso.add(this.repoEgresos.get(Integer.parseInt(paramFieldValue[1])));
            }
        }
        List<Categoria> cats = categoriasCache.get(request.session().id());
        if (cats != null) {
            categorias.addAll(cats);
        }
        System.out.println(categoriasCache);
        for (OperacionEgreso egreso : operacionesEgreso) {
            try {
                repoEgresos.asociarCategorias(egreso, categorias);
            } catch (Exception e) {
                parametros.put("asociarECfail", true);
            }
        }
        parametros.put("refererAsociarEC", true);
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
    }

    public ModelAndView agregarCategoria(Request request, Response response) throws UserNotFoundException {
        String nombreCat = request.queryParams("newCategoria");
        Categoria categoria = new Categoria(nombreCat);

        List<Categoria> categoriasFound = categoriasCache.get(request.session().id());
        if (categoriasFound == null) {
            categoriasFound = new ArrayList<>();
        }
        //seteo un id unico para encontrarlo dsp
        categoria.setId(UUID.randomUUID().hashCode());
        categoriasFound.add(categoria);

        categoriasCache.put(request.session().id(), categoriasFound);

        return this.inicio(request, response);
    }

    public Categoria findCategoriaInCacheForUser(String sessionID, Integer categoriaID) {
        List<Categoria> categoriasCacheFromUser = categoriasCache.get(sessionID);
        for (Categoria cat : categoriasCacheFromUser) {
            if (cat.getId() == categoriaID) {
                return cat;
            }
        }
        return null;
    }
}
