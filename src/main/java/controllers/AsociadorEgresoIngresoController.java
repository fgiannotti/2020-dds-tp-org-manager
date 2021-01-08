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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class AsociadorEgresoIngresoController {
    private RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    private RepoOperacionesIngresos repoIngreso = new RepoOperacionesIngresos();
    private RepoUsuarios repoUsuarios = new RepoUsuarios();

    private Usuario user;

    public ModelAndView inicio(Request request, Response response) throws UserNotFoundException {
        Router.CheckIfAuthenticated(request, response);
        String userName = request.cookie("user");
        user = repoUsuarios.buscarPorNombre(userName);

        Map<String, Object> parametros = new HashMap<>();
        List<OperacionIngreso> operacionesIngreso = new ArrayList<OperacionIngreso>();
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>();

        operacionesIngreso.addAll(user.getOrganizacionALaQuePertenece().getIngresos());

        operacionesEgreso.addAll(user.getOrganizacionALaQuePertenece().getEgresos());

        parametros.put("ingresos", operacionesIngreso);
        parametros.put("egresos", operacionesEgreso);

        return new ModelAndView(parametros,"asociar.hbs");
    }

    public Response asociar(Request request, Response response) {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        String[] ingresosEgresos = request.body().split("&");

        ArrayList<Operacion> operacionIngresos = new ArrayList<>();
        ArrayList<Operacion> operacionesEgresos = new ArrayList<>();

        for (String ingresoEgreso : ingresosEgresos) {
            String[] splitIngresoEgreso = ingresoEgreso.split("=");
            if(splitIngresoEgreso[0].equals("ingreso")){
                operacionIngresos.add(this.repoIngreso.find(Integer.parseInt(splitIngresoEgreso[1])));
            }
            if(splitIngresoEgreso[0].equals("egreso")){
                operacionesEgresos.add(this.repoEgresos.find(Integer.parseInt(splitIngresoEgreso[1])));
            }
        }
        JSONArray jsonIngreso = this.jsonOperacional(operacionIngresos.stream());
        JSONArray jsonEgreso= this.jsonOperacional(operacionesEgresos.stream());
        JSONObject json= new JSONObject();
        json.put("Ingresos",jsonIngreso);
        json.put("Egresos",jsonEgreso);
        ConfiguracionApi configApi = new ConfiguracionApi();
        json.put("Configuracion",configApi.getJsonConfig());

        VinculadorApi vinculador = new VinculadorApi();
        Configuracion config = new Configuracion();
        System.out.println(json.toString() + config.getApiVinculador());

        JSONObject responseVinculador = vinculador.Post_JSON(json.toString(), config.getApiVinculador());
        JSONArray jsonVinculos = (JSONArray) responseVinculador.get("Relaciones");

        List<OperacionIngreso> ingresos = new ArrayList<OperacionIngreso>();

        List<Resultado> resultados = new ArrayList<Resultado>();

        for (Object jsonVinculo : jsonVinculos) {
            JSONObject jsonViculaciones = (JSONObject) jsonVinculo;
            Integer idIngreso = (Integer) jsonViculaciones.get("IDIngreso");
            JSONArray jsonEgresos = (JSONArray) jsonViculaciones.get("IDSEgresos");

            Optional<Operacion> operacionIngresoResponse = Optional.ofNullable(this.repoIngreso.find(idIngreso));
            OperacionIngreso operacionIngreso = (OperacionIngreso) operacionIngresoResponse.get();

            //ahora los egresos para este ingreso
            jsonEgresos.forEach((jsonConId) -> {
                Optional<Operacion> operacionEgresoResponse;
                operacionEgresoResponse = Optional.ofNullable(this.repoEgresos.find(Integer.parseInt(String.valueOf(jsonConId))));
                OperacionEgreso operacionEgreso2 = (OperacionEgreso) operacionEgresoResponse.get();

                operacionIngreso.agregarOperacionEgresos(operacionEgreso2);
            });

            ingresos.add(operacionIngreso);
        }

        //pongo todos estos ingresos vinculados en la bandeja de cada usuario revisor de la empresa
        List<Revisor> revisoresDeLaOrg = repoUsuarios.buscarRevisoresPorOrganizacion(user.getOrganizacionALaQuePertenece().getId());
        for (Revisor revisor : revisoresDeLaOrg) {
            for (OperacionIngreso ingreso: ingresos){
                for(OperacionEgreso egreso:  ingreso.getOperacionEgresos()){
                    Resultado egresoResultado = new Resultado(ingreso.getId(), egreso.getProveedores(), true, true, true, true, LocalDate.now(), revisor.getBandejaDeEntrada());
                    resultados.add(egresoResultado);
                }
            }

        }

        if (ingresos.size() != 0){
            System.out.println("operacion ingreso vinculada con "+ ingresos.get(0).toString()+" con " + ingresos.get(0).getOperacionEgresos().size()+" egresos");
        }

        for(Resultado resultado: resultados){
            EntityManagerHelper.persist(resultado);
        }

        response.redirect("/home"); //success
        return response;
    }
    protected String fechaToString(LocalDate fecha){
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
