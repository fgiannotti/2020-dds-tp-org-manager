package controllers;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import org.apache.commons.compress.utils.IOUtils;
import repositorios.Builders.OperacionIngresoBuilder;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    public ModelAndView verIngreso(Request request, Response response) {
        String ingresoID = request.params("id");
        OperacionIngreso operacionIngreso = repoOperacionesIngresos.get(new Integer(ingresoID));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ingreso", operacionIngreso);
        parametros.put("ingresoID", operacionIngreso.getId());

        return new ModelAndView(parametros,"ingreso.hbs");
    }
}
