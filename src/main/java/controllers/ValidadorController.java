
package controllers;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Estrategias.Validador;
import entidades.Estrategias.ValidadorUno;
import entidades.Operaciones.OperacionEgreso;
import entidades.Usuarios.Revisor;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidadorController {
    private RepoOperacionesEgresos repoOperacionesEgresos = new RepoOperacionesEgresos();
    private RepoOperacionesIngresos repoOperacionesIngresos = new RepoOperacionesIngresos();
    private RepoUsuarios repoUsuarios = new RepoUsuarios();
    private HomeController homeController = new HomeController();
    /*public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,???);
    }*/

    public ModelAndView validar(Request request, Response response) throws UserNotFoundException {
        String nombreUsuario = request.cookie("user");
        Revisor usuario = repoUsuarios.buscarRevisorPorNombre(nombreUsuario);
        List<OperacionEgreso> egresos = repoOperacionesEgresos.getAllByOrg(usuario.getOrganizacion());
        //List<OperacionIngreso> ingresos = repoOperacionesIngresos.getAllByOrg(usuario.getOrganizacion());

        Validador validador = new ValidadorUno(usuario.getBandejaDeEntrada());
        int cargasInvalidas = 0;
        for (OperacionEgreso egreso: egresos){
            List<Object> resultado = validador.validar(egreso);
            Boolean validarOK = (Boolean) resultado.get(0);
            if (!validarOK){
                cargasInvalidas++;
            }
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("validarDone",true);
        parametros.put("cargasValidas",egresos.size() - cargasInvalidas);
        parametros.put("cargasTotales",egresos.size());

        homeController.setParametrosAuxiliares(parametros);
        return homeController.inicio(request,response);
    }
}
