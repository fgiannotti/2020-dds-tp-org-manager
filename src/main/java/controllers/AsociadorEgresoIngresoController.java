package controllers;

import entidades.Configuracion.Configuracion;
import entidades.Configuracion.ConfiguracionApi;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
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


    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);
        String userName = request.cookie("user");
        Usuario user = repoUsuarios.buscarPorNombre(userName);
        Map<String, Object> parametros = new HashMap<>();
        List<OperacionIngreso> operacionesIngreso = new ArrayList<OperacionIngreso>();
        List<OperacionIngreso> opIngreso = new ArrayList<>();
        for (Operacion operacion1 : user.getOrganizacionALaQuePertenece().getOperacionesRealizadas()) {
            if (operacion1.isIngreso()) {
                opIngreso.add((OperacionIngreso) operacion1);
            }
        }
        operacionesIngreso.addAll(opIngreso);
        List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>();
        List<OperacionIngreso> opEgreso = new ArrayList<>();
        for (Operacion operacion1 : user.getOrganizacionALaQuePertenece().getOperacionesRealizadas()) {
            if (operacion1.isEgreso()) {
                opEgreso.add((OperacionIngreso) operacion1);
            }
        }
        operacionesIngreso.addAll(opEgreso);
        parametros.put("ingresos", operacionesIngreso);
        parametros.put("egresos", operacionesEgreso);
        return new ModelAndView(parametros,"asociar.hbs");
    }

    public Response asociar(Request request, Response response) {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        String[] ingresosEgresos = request.body().split("&");
        ArrayList<Operacion> operacionIngreso = new ArrayList<>();
        ArrayList<Operacion> operacionEgreso = new ArrayList<>();
        for (String ingresoEgreso : ingresosEgresos) {
            String[] splitIngresoEgreso = ingresoEgreso.split("=");
            if(splitIngresoEgreso[0].equals("ingreso")){
                operacionIngreso.add(this.repoIngreso.find(Integer.parseInt(splitIngresoEgreso[1])));
            }
            if(splitIngresoEgreso[0].equals("egreso")){
                operacionEgreso.add(this.repoEgresos.find(Integer.parseInt(splitIngresoEgreso[1])));
            }
        }
        JSONArray jsonIngreso = this.jsonOperacional(operacionIngreso.stream());
        JSONArray jsonEgreso= this.jsonOperacional(operacionEgreso.stream());
        JSONObject json= new JSONObject();
        json.put("Ingresos",jsonIngreso);
        json.put("Egresos",jsonEgreso);
        ConfiguracionApi configApi = new ConfiguracionApi();
        json.put("Configuracion",configApi.getJsonConfig());

        VinculadorApi vinculador = new VinculadorApi();
        Configuracion config = new Configuracion();
        JSONObject responseVinculador = vinculador.Post_JSON(json.toString(), config.getApiVinculador());
        JSONArray jsonVinculos = (JSONArray) responseVinculador.get("Relaciones");
        jsonVinculos.forEach((jsonVinculo) -> {
            JSONObject jsonViculaciones=(JSONObject) jsonVinculo;
            Integer idIngreso = (Integer) jsonViculaciones.get("IDIngreso");
            JSONArray jsonEgresos = (JSONArray) jsonViculaciones.get("IDSEgresos");
            Optional<Operacion> operacionIngresoResponse;
            operacionIngresoResponse = Optional.ofNullable(this.repoIngreso.find(idIngreso));
            OperacionIngreso operacionIngreso2 = (OperacionIngreso) operacionIngresoResponse.get();
            jsonEgresos.forEach((jsonConId) -> {
                Optional<Operacion> operacionEgresoResponse;
                operacionEgresoResponse = Optional.ofNullable(this.repoEgresos.find(Integer.parseInt(String.valueOf(jsonConId))));
                OperacionEgreso operacionEgreso2 = (OperacionEgreso) operacionEgresoResponse.get();
                operacionIngreso2.agregarOperacionEgresos(operacionEgreso2);
            });
        });

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
