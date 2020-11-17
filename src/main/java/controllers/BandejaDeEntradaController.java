package controllers;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Usuarios.Revisor;
import repositorios.RepoUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import server.Router;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BandejaDeEntradaController {
    private final RepoUsuarios repoUsuarios = new RepoUsuarios();

    public ModelAndView inicio(Request request, Response response){

        Router.CheckIfAuthenticated(request, response);

        String usuario = request.cookie("user");
        Revisor user = repoUsuarios.buscarRevisorPorNombre(usuario);
        BandejaDeEntrada bandeja = user.getBandejaDeEntrada();
        List<Resultado> resultados = bandeja.getResultadosFiltrados();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("resultados",resultados);

        System.out.println(Arrays.toString(resultados.toArray()));

        return new ModelAndView(parametros,"bandeja.hbs");
    }
}
