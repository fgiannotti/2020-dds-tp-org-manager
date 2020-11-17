package controllers;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.Operaciones.*;
import repositorios.RepoOperacionesEgresos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperacionController {
    private RepoOperacionesEgresos repoOperacionesEgresos = new RepoOperacionesEgresos();

    public ModelAndView inicio(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        List<Comprobante> comprobantes = new ArrayList<Comprobante>();
        List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
        List<Item> items = new ArrayList<Item>();
        List<Articulo> articulos = new ArrayList<Articulo>();
        List<OperacionIngreso> ingresos = new ArrayList<OperacionIngreso>();
        Map<String, Object> parametros = new HashMap<>();
        EntityManagerHelper.createQuery("from Proveedor").getResultList().forEach((a) -> { proveedores.add((Proveedor)a); });
        parametros.put("proveedores", proveedores);
        EntityManagerHelper.createQuery("from Comprobante").getResultList().forEach((a) -> { comprobantes.add((Comprobante)a); });
        parametros.put("comprobantes", comprobantes);
        EntityManagerHelper.createQuery("from Presupuesto").getResultList().forEach((a) -> { presupuestos.add((Presupuesto)a); });
        parametros.put("presupuestos", presupuestos);
        EntityManagerHelper.createQuery("from Articulo").getResultList().forEach((a) -> { articulos.add((Articulo)a); });
        parametros.put("articulos", articulos);
        EntityManagerHelper.createQuery("from Item").getResultList().forEach((a) -> { items.add((Item)a); });
        parametros.put("items", items);
        EntityManagerHelper.createQuery("from OperacionIngreso").getResultList().forEach((a) -> { ingresos.add((OperacionIngreso)a); });
        parametros.put("ingresos", ingresos);
        return new ModelAndView(parametros,"crear-proveedor.hbs");
    }

    public ModelAndView fechaYCantidad(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"index-crear-egreso.hbs");
    }

    public Response crearOperacionEgreso (Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        OperacionEgreso operacionEgreso = new OperacionEgreso();
        //asignarAtributosA(operacionEgreso, request);

        return response;
    }

    public Response postFechaYCantidad(Request request, Response response){
        String fecha = request.queryParams("fecha");
        int cantidadPresupuestos = Integer.parseInt(request.queryParams("cantidadMinima"));
        int valorTotal = Integer.parseInt(request.queryParams("valorTotal"));
        System.out.println(fecha);
        System.out.println(cantidadPresupuestos);
        System.out.println(valorTotal);
        response.redirect("/crearEgreso2");
        return response;
    }

    public ModelAndView seleccionarProveedor(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        EntityManagerHelper.createQuery("from Proveedor").getResultList().forEach((a) -> { proveedores.add((Proveedor)a); });
        parametros.put("proveedores", proveedores);
        return new ModelAndView(parametros,"index-seleccionar-proveedores.hbs");
    }

    public Response postSeleccionarProveedor(Request request, Response response){
        String proveedor = request.queryParams("proveedor");
        int presupuesto = Integer.parseInt(request.queryParams("presupuesto"));
        System.out.println(proveedor);
        System.out.println(presupuesto);
        response.redirect("/crearEgreso3");
        return response;
    }

    public ModelAndView medioDePago(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"crear-medio-pago.hbs");
    }

    public Response postMedioDePago(Request request, Response response){
        String medioDePago = request.queryParams("medioDePago");
        int total = Integer.parseInt(request.queryParams("total"));
        System.out.println(medioDePago);
        System.out.println(total);
        response.redirect("/crearEgreso4");
        return response;
    }

    public ModelAndView seleccionArticulos(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<Articulo> articulos = new ArrayList<Articulo>();
        EntityManagerHelper.createQuery("from Articulo").getResultList().forEach((a) -> { articulos.add((Articulo)a); });
        parametros.put("articulos", articulos);
        return new ModelAndView(parametros,"seleccion-articulos.hbs");
    }

    public Response postSeleccionArticulos(Request request, Response response){
        String medioDePago = request.queryParams("medioDePago");
        int total = Integer.parseInt(request.queryParams("total"));
        System.out.println(medioDePago);
        System.out.println(total);
        response.redirect("/crearEgreso5");
        return response;
    }

    /*public Response guardar(Request request, Response response){
        OperacionEgreso operacionEgreso = new OperacionEgreso();
        asignarAtributosA(operacionEgreso, request);
        this.repoOperacionesEgresos.agregar(operacionEgreso);
        response.redirect("/home");
        return response;
    }

    private void asignarAtributosA(OperacionEgreso operacionEgreso, Request request){
        if(request.queryParams("proveedor") != null){
            int telefono = new Integer(request.queryParams("telefono"));
            operacionEgreso.setTelefono(telefono);
        }

        if(request.queryParams("nombre") != null){
            operacionEgreso.setNombre(request.queryParams("nombre"));
        }

        if(request.queryParams("email") != null){
            operacionEgreso.setEmail(request.queryParams("email"));
        }

        if(request.queryParams("nombreDeUsuario") != null){
            operacionEgreso.setNombreDeUsuario(request.queryParams("nombreDeUsuario"));
        }

        if(request.queryParams("apellido") != null){
            operacionEgreso.setApellido(request.queryParams("apellido"));
        }

        if(request.queryParams("legajo") != null){
            int legajo = new Integer(request.queryParams("legajo"));
            operacionEgreso.setLegajo(legajo);
        }

        if(request.queryParams("fechaDeNacimiento") != null && !request.queryParams("fechaDeNacimiento").isEmpty()){
            LocalDate fechaDeNacimiento = LocalDate.parse(request.queryParams("fechaDeNacimiento"));
            operacionEgreso.setFechaDeNacimiento(fechaDeNacimiento);
        }

        if(request.queryParams("rol") != null){
            Repositorio<Rol> repoRol = FactoryRepositorio.get(Rol.class);
            Rol unRol = repoRol.buscar(new Integer(request.queryParams("rol")));
            operacionEgreso.setRol(unRol);
        }
    }*/
}