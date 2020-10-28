package controllers;

import entidades.Usuarios.Usuario;
import repositorios.Builders.UsuarioBuilder;
import repositorios.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Seguridad.Autenticador;
import utils.Seguridad.Login;

import java.util.HashMap;
import java.util.Map;

public class LoginController {

    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"login.hbs");
    }

    public Response login(Request request, Response response){
        RepoUsuarios repoUsuarios = new RepoUsuarios();
        UsuarioBuilder builder = new UsuarioBuilder();
        Autenticador autenticador = new Autenticador(repoUsuarios, builder);
        Login login = new Login(autenticador);
        try{
            String nombreDeUsuario = request.queryParams("nombreDeUsuario");
            String contrasenia     = request.queryParams("contrasenia");

            if(login.login(nombreDeUsuario, contrasenia)){
                request.session(true);
                Usuario elUsuario = repoUsuarios.buscarPorNombre(nombreDeUsuario);
                //request.session().attribute("rol", elUsuario.getClaseUsuario().toString());
                response.redirect("/home");
            }
            else{
                response.redirect("/");
            }
        }
        catch (Exception e){
            //Funcionalidad disponible solo con persistencia en Base de Datos
            response.redirect("/");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response){
        request.session().invalidate();
        response.redirect("/");
        return response;
    }
}