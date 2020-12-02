package controllers;

import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import repositorios.Builders.OperacionIngresoBuilder;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OperacionIngresoController {
    private RepoOperacionesIngresos repoOperacionesIngresos = new RepoOperacionesIngresos();
    private RepoUsuarios repoUsuarios = new RepoUsuarios();


    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"cargar-ingreso.hbs");
    }
    public Response altaIngreso(Request request, Response response) throws Exception {
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        String unaDescripcion = request.queryParams("descripcion");
        int unMonto = Integer.parseInt(request.queryParams("monto"));

        String nombreUsuario = request.cookie("user");
        Usuario usuario = repoUsuarios.buscarPorNombre(nombreUsuario);

        OperacionIngresoBuilder builder = new OperacionIngresoBuilder()
                .agregarMontoTotal(unMonto)
                .agregarDescripcion(unaDescripcion)
                .agregarOrganizacion(usuario.getOrganizacionALaQuePertenece())
                .agregarFechaOperacion(LocalDate.now());

        OperacionIngreso operacionIngreso = builder.build();

        repoOperacionesIngresos.guardar(operacionIngreso);
        System.out.println("Guardado OK de ingreso "+ unaDescripcion + " con id autogenerado: " + String.valueOf(operacionIngreso.getId()));
        response.redirect("/home"); //Success
        return response;
    }
}
