package controllers;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Usuarios.Revisor;
import repositorios.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import server.Router;

import java.util.*;

public class BandejaDeEntradaController {
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

        String usuario = request.cookie("user");
        Revisor user = repoUsuarios.buscarRevisorPorNombre(usuario);
        List<Resultado> resultados = user.verMensajes();
        List<Resultado> resultadosDup = new ArrayList<>();

        resultados.forEach(r -> {resultadosDup.add(new Resultado(r.getNumeroOperacion(),r.getProveedorElegido(),r.getCorrespondeCargaCorrecta(),r.getCorrespondeDetalle(),r.getCorrespondeCriterio(),r.getFueLeido(),r.getFechaValidacion(),r.getDescripcion(),r.getBandeja()));});
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("resultados",resultadosDup);
        System.out.println(Arrays.toString(resultados.toArray()));

        return new ModelAndView(parametros,"bandeja.hbs");
    }
}
