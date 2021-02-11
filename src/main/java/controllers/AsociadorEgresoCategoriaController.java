package controllers;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.CriterioDeEmpresa;
import entidades.Usuarios.Usuario;
import repositorios.*;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

public class AsociadorEgresoCategoriaController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoCategorias repoCategorias = new RepoCategorias();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();
    private Usuario user;
    //cache de categorias creadas durante un session ID
    private Map<String, List<Categoria>> categoriasCache = new HashMap<>();
    EntityManager em = EntityManagerHelper.getEntityManager();

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        user = repoUsuarios.buscarPorNombre(request.cookie("user"));

        List<CriterioDeEmpresa> criterioDeEmpresas = new ArrayList<>();
        List<Integer> critIDs =new ArrayList<>();
        em.createQuery("FROM CriterioDeEmpresa WHERE org_id= '" + user.getOrganizacion().getId() + "'OR org_id IS NULL").getResultList().forEach((a) -> {
            criterioDeEmpresas.add((CriterioDeEmpresa) a);
            critIDs.add(((CriterioDeEmpresa) a).getId());
        });

        Map<String, Object> parametros = new HashMap<>();

        List<Categoria> categorias = repoCategorias.getAllFromCritIDs(critIDs);
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>(repoEgresos.getAllByOrg(user.getOrganizacionALaQuePertenece()));

        List<Categoria> cats = new ArrayList<>();
        if (categoriasCache.get(request.session().id())!=null){
            cats.addAll(categoriasCache.get(request.session().id()));
        }

        categorias.addAll(cats);
        parametros.put("categorias", categorias);
        parametros.put("egresos", operacionesEgreso);
        parametros.put("criterios", criterioDeEmpresas);

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
        //obtengo las categorias
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
                    //persisto en db la categoria que encontre de la cache
                    repoCategorias.persistCategoria(categoriaFound);
                    categorias.add(categoriaFound);
                }
            }
            if (paramFieldValue[0].equals("egreso")) {
                operacionesEgreso.add(this.repoEgresos.get(Integer.parseInt(paramFieldValue[1])));
            }
        }
        System.out.println(categoriasCache);
        for (OperacionEgreso egreso : operacionesEgreso) {
            try {
                List<Categoria> categoriasSantizadas = getCategoriasNoAsignadas(egreso,categorias);
                if (categoriasSantizadas.size() > 0){
                    repoEgresos.asociarCategorias(egreso, categoriasSantizadas);
                }
            } catch (Exception e) {
                System.err.println("FALLAMO ASOCIANDO Eg-Cat: "+e.getMessage()+"\n");
                e.printStackTrace();
                parametros.put("asociarECfail", true);
            }
        }
        parametros.put("refererAsociarEC", true);
        this.categoriasCache.remove(request.session().id());
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
    }

    private List<Categoria> getCategoriasNoAsignadas(OperacionEgreso egreso, ArrayList<Categoria> categoriasAsociar) {
        Set<Categoria> catsSet = new HashSet<>(categoriasAsociar);
        catsSet.removeAll(egreso.getCategorias());

        return new ArrayList<>(catsSet);
    }

    public ModelAndView agregarCategoria(Request request, Response response) throws UserNotFoundException {
        String nombreCat = request.queryParams("newCategoria");
        String nombreCrit = request.queryParams("criterioName");
        int criterioID = 0;
        try {
            criterioID = Integer.parseInt(request.queryParams("criterio"));
        }catch (NumberFormatException e){
            System.err.println(e);
        }

        boolean creaCriterio = Boolean.parseBoolean(request.queryParams("creaCriterio"));
        CriterioDeEmpresa crit = null;

        if (creaCriterio) {
            crit = new CriterioDeEmpresa(nombreCrit,null,user.getOrganizacion());
            em.persist(crit);
        }
        if (criterioID != 0 ){
            crit  = (CriterioDeEmpresa) em.createQuery("FROM CriterioDeEmpresa where id = '"+criterioID+"'").getSingleResult();
        }

        Categoria categoria = new Categoria(nombreCat,crit);

        List<Categoria> categoriasFound = categoriasCache.getOrDefault(request.session().id(), new ArrayList<>());

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
