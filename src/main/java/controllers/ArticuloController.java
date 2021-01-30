package controllers;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class ArticuloController {
    EntityManager em = EntityManagerHelper.getEntityManager();

    public ModelAndView articulos(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"crear-articulo.hbs");
    }

    public Response postArticulos(Request request, Response response) {
        String nombre = request.queryParams("nombre");
        Float valor = Float.parseFloat(request.queryParams("valor"));
        Articulo unArticulo = new Articulo(nombre, valor);
        System.out.println(unArticulo);
        em.getTransaction().begin();
        em.persist(unArticulo);
        em.getTransaction().commit();
        response.redirect("/crearEgreso4");
        return response;
    }
}
