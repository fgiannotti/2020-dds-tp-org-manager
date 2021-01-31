package controllers;

import entidades.Usuarios.Usuario;
import repositorios.Builders.UsuarioBuilder;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Seguridad.Autenticador;
import utils.Seguridad.Login;
import utils.Seguridad.UserBlockedException;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();
    private final UsuarioBuilder builder = new UsuarioBuilder();
    private final Autenticador autenticador = new Autenticador(repoUsuarios, builder);
    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "login.hbs");
    }

    public ModelAndView login(Request request, Response response) {

        HashMap<String, Object> parametros =  new HashMap<String, Object>();

        Login login = new Login(autenticador);
        try {
            String nombreDeUsuario = request.queryParams("nombreDeUsuario");
            String contrasenia = request.queryParams("contrasenia");

            if (login.login(nombreDeUsuario, contrasenia)) {
                Usuario elUsuario = repoUsuarios.buscarPorNombre(nombreDeUsuario);

                request.session(true);                     // create and return session
                response.cookie("id", request.session().id(), 1000000000);
                response.cookie("user", elUsuario.getNombre());
                String rol = "basico";
                if (elUsuario.getClass().getName().contains("Revisor")) {
                    rol = "revisor";
                }

                response.cookie("rol", rol);

                System.out.printf("Usuario: %s con id %s y rol %s", elUsuario.getNombre(), request.session().id(), elUsuario.getClass().getName());

                response.redirect("/home");
            }
            //wrong password
            else {
                parametros.put("incorrecta",true);
            }
        }catch (UserNotFoundException e) {
            parametros.put("user-invalido",true);
        }
        //Usuario Bloqueado, tirar mensaje que es x 5 min
        catch (UserBlockedException e) {
            parametros.put("bloqueado",true);
        }
        return new ModelAndView(parametros,"login.hbs");
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/");
        return response;
    }
}