package controllers;

import db.EntityManagerHelper;
import entidades.Operaciones.Proveedor;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ProveedorController {

    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"crear-proveedor.hbs");
    }

    public Response altaProveedor(Request request, Response response) {
        String nombreORazon = request.queryParams("nombreORazon");
        String dni = request.queryParams("dni");
        String direccion = request.queryParams("direccion");
        Proveedor unProveedor = new Proveedor(nombreORazon, dni, direccion);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(unProveedor);
        EntityManagerHelper.commit();

        response.redirect("/crearEgreso1"); //success
        return response;
    }
}
