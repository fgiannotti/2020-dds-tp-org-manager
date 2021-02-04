package controllers;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Configuracion.Configuracion;
import entidades.Configuracion.ConfiguracionApi;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Vinculador.VinculadorApi;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class AsociadorEgresoIngresoController {
    private final RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private final RepoOperacionesIngresos repoIngreso = new RepoOperacionesIngresos();
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();
    private EntityManager em = EntityManagerHelper.getEntityManager();
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

        return new ModelAndView(parametros, "asociar-egreso-ingreso.hbs");
    }

    public ModelAndView asociarIngresoEgreso(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();

        String[] ingresosEgresos = request.body().split("&");

        ArrayList<OperacionIngreso> operacionesIngreso = new ArrayList<>();
        ArrayList<OperacionEgreso> operacionesEgreso = new ArrayList<>();
        //obtengo los ingresos y egresos del body
        for (String ingresoEgreso : ingresosEgresos) {
            String[] splitIngresoEgreso = ingresoEgreso.split("=");
            if (splitIngresoEgreso[0].equals("ingreso")) {
                operacionesIngreso.add(this.repoIngreso.get(Integer.parseInt(splitIngresoEgreso[1])));
            }
            if (splitIngresoEgreso[0].equals("egreso")) {
                operacionesEgreso.add(this.repoEgresos.get(Integer.parseInt(splitIngresoEgreso[1])));
            }
        }


        if (operacionesEgreso.size() != 1) {
            parametros.put("asociar-invalido", true);
        }
        OperacionEgreso egreso = null;
        int asocFailCount = 0;
        if (!operacionesIngreso.isEmpty()) {
            egreso = operacionesEgreso.get(0);

            for (OperacionIngreso ingreso : operacionesIngreso) {

                try {
                    repoEgresos.asociarIngreso(egreso, ingreso);
                } catch (Exception e) {
                    asocFailCount++;
                }
            }
        }
        parametros.put("asociarEIok", asocFailCount == 0);
        parametros.put("asociarEIfail", asocFailCount > 0);
        parametros.put("failCount", asocFailCount);
        parametros.put("totalECount", operacionesIngreso.size());
        //response.redirect("/home");
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
    }

    public ModelAndView inicioVincular(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        String userName = request.cookie("user");
        user = repoUsuarios.buscarPorNombre(userName);

        Map<String, Object> parametros = new HashMap<>();

        List<OperacionIngreso> operacionesIngreso = new ArrayList<OperacionIngreso>(repoIngreso.getAllByOrg(user.getOrganizacionALaQuePertenece()));
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>(repoEgresos.getAllByOrg(user.getOrganizacionALaQuePertenece()));

        parametros.put("ingresos", operacionesIngreso);
        parametros.put("egresos", operacionesEgreso);

        return new ModelAndView(parametros, "vincular-egreso-ingreso.hbs");
    }

    public ModelAndView vincular(Request request, Response response) {
        Router.CheckIfAuthenticated(request,response);
        String[] ingresosEgresos = request.body().split("&");

        ArrayList<Operacion> operacionIngresos = new ArrayList<>();
        ArrayList<Operacion> operacionesEgresos = new ArrayList<>();

        for (String ingresoEgreso : ingresosEgresos) {
            String[] splitIngresoEgreso = ingresoEgreso.split("=");
            if (splitIngresoEgreso[0].equals("ingreso")) {
                operacionIngresos.add(this.repoIngreso.get(Integer.parseInt(splitIngresoEgreso[1])));
            }
            if (splitIngresoEgreso[0].equals("egreso")) {
                operacionesEgresos.add(this.repoEgresos.get(Integer.parseInt(splitIngresoEgreso[1])));
            }
        }
        JSONArray jsonIngreso = this.jsonOperacional(operacionIngresos.stream());
        JSONArray jsonEgreso = this.jsonOperacional(operacionesEgresos.stream());
        JSONObject json = new JSONObject();
        json.put("Ingresos", jsonIngreso);
        json.put("Egresos", jsonEgreso);
        ConfiguracionApi configApi = new ConfiguracionApi();
        json.put("Configuracion", configApi.getJsonConfig());

        VinculadorApi vinculador = new VinculadorApi();
        Configuracion config = new Configuracion();
        System.out.println(json.toString()+"\n" + config.getApiVinculador());

        JSONObject responseVinculador = vinculador.Post_JSON(json.toString(), config.getApiVinculador());
        JSONArray jsonVinculos = (JSONArray) responseVinculador.get("Relaciones");

        List<OperacionIngreso> ingresos = new ArrayList<OperacionIngreso>();

        List<Resultado> resultados = new ArrayList<Resultado>();

        for (Object jsonVinculo : jsonVinculos) {
            JSONObject jsonViculaciones = (JSONObject) jsonVinculo;
            Integer idIngreso = (Integer) jsonViculaciones.get("IDIngreso");
            JSONArray jsonEgresos = (JSONArray) jsonViculaciones.get("IDSEgresos");

            OperacionIngreso ingreso = this.repoIngreso.get(idIngreso);

            //ahora los egresos para este ingreso
            jsonEgresos.forEach((idEgresos) -> {
                OperacionEgreso operacionEgreso = this.repoEgresos.get(Integer.parseInt(String.valueOf(idEgresos)));

                ingreso.agregarOperacionEgreso(operacionEgreso);
                repoEgresos.asociarIngreso(operacionEgreso,ingreso);
            });

            ingresos.add(ingreso);
        }

        //pongo todos estos ingresos vinculados en la bandeja de cada usuario revisor de la empresa
        List<Revisor> revisoresDeLaOrg = repoUsuarios.buscarRevisoresPorOrganizacion(user.getOrganizacionALaQuePertenece().getId());
        for (Revisor revisor : revisoresDeLaOrg) {
            for (OperacionIngreso ingreso : ingresos) {
                for (OperacionEgreso egreso : ingreso.getOperacionesEgreso()) {
                    Resultado egresoResultado = new Resultado(ingreso.getId(), egreso.getProveedorElegido(), true, true, true, false, LocalDate.now(),"Vinculación de ingreso '"+ingreso.getId()+"' con el egreso '"+egreso.getNumeroOperacion()+"'", revisor.getBandejaDeEntrada());
                    resultados.add(egresoResultado);
                }
            }

        }

        if (ingresos.size() != 0) {
            System.out.println("operacion ingreso vinculada con " + ingresos.get(0).toString() + " con " + ingresos.get(0).getOperacionesEgreso().size() + " egresos");
        }

        for (Resultado resultado : resultados) {
            em.persist(resultado);
        }
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("vincularOK",true);
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
    }

    protected String fechaToString(LocalDate fecha) {
        return fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public JSONArray jsonOperacional(Stream<Operacion> operacionStream) {

        JSONArray jsonDeOperaciones = new JSONArray();
        operacionStream.forEach((operacion) -> {
            JSONObject jsonDeOperacion = new JSONObject();
            jsonDeOperacion.put("id", String.valueOf(operacion.getId()));
            jsonDeOperacion.put("fecha", this.fechaToString(operacion.getFecha()));
            jsonDeOperacion.put("monto", String.valueOf(operacion.getMontoTotal()));
            jsonDeOperaciones.put(jsonDeOperacion);
        });
        return jsonDeOperaciones;
    }
}
