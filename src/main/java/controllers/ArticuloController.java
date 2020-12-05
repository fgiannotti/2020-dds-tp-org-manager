package controllers;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ArticuloController {

    public ModelAndView articulos(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"crear-articulo.hbs");
    }

    public Response postArticulos(Request request, Response response) {
        String nombre = request.queryParams("nombre");
        Float valor = Float.parseFloat(request.queryParams("valor"));
        Articulo unArticulo = new Articulo(nombre, valor);
        System.out.println(unArticulo);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(unArticulo);
        EntityManagerHelper.commit();
        response.redirect("/crearEgreso4");
        return response;
    }
}
