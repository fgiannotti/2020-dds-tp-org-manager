
package controllers;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Estrategias.Validador;
import entidades.Estrategias.ValidadorUno;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Proveedor;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.Usuario;
import javafx.util.Pair;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoOperacionesIngresos;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import scala.Int;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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
        Integer cargasInvalidas = 0;
        for (OperacionEgreso egreso: egresos){
            Pair<Boolean,String> resultado = validador.validar(egreso);
            if (!resultado.getKey()){
                cargasInvalidas++;
            }
        }
        

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("validarDone",true);
        parametros.put("cargasInvalidas",cargasInvalidas);
        parametros.put("cargasTotales",egresos.size());
        response.cookie("validarDone","true");
        response.cookie("cargasInvalidas", String.valueOf(cargasInvalidas));
        response.cookie("argasTotales", String.valueOf(egresos.size()));

        return homeController.inicio(request,response);
    }
}
