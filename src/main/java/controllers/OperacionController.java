package controllers;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.*;
import entidades.Organizaciones.CriterioDeEmpresa;
import entidades.Organizaciones.Organizacion;
import repositorios.RepoOperacionesEgresos;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperacionController {
    private RepoOperacionesEgresos repoOperacionesEgresos = new RepoOperacionesEgresos();
    private EgresoBuilder builder = new EgresoBuilder();

    public ModelAndView inicio(Request request, Response response){
        Router.CheckIfAuthenticated(request, response);

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
        builder.nuevoEgreso();
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
        return response;
    }

    public Response postFechaYCantidad(Request request, Response response){
        String fecha = request.queryParams("fecha");
        int cantidadPresupuestos = Integer.parseInt(request.queryParams("cantidadMinima"));
        int valorTotal = Integer.parseInt(request.queryParams("valorTotal"));
        LocalDate unaFecha = LocalDate.parse(fecha);
        builder.asignarFechaPresupuestosMinYValor(unaFecha, cantidadPresupuestos, valorTotal);
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
        Proveedor unProveedorEntero = (Proveedor)EntityManagerHelper.createQuery("from Proveedor where nombre_apellido_razon = '" + proveedor+"'").getResultList().get(0);
        builder.asignarProveedor(unProveedorEntero);
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
        MedioDePago unMedio = (MedioDePago) EntityManagerHelper.createQuery("from MedioDePago where id = " + medioDePago).getResultList().get(0);
        builder.asignarMedioDePago(unMedio);
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
        String nombre = request.queryParams("nombreArticulo");
        Articulo articulo = (Articulo) EntityManagerHelper.createQuery("from Articulo where nombre = " + nombre+"'").getResultList().get(0);
        builder.asignarArticulo(articulo);
        response.redirect("/crearEgreso6");
        return response;
    }

    public ModelAndView cargarComprobante(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"cargar-comprobante.hbs");
    }

    public Response postCargarComprobante(Request request, Response response){
        String tipoComprobante = request.queryParams("tipoComprobante");
        String stringNum = request.queryParams("numeroComprobante");
        if(stringNum == "") {
            int numeroComprobante = Integer.parseInt(request.queryParams("numeroComprobante"));
            System.out.println(numeroComprobante);
        }
        System.out.println(tipoComprobante);
        response.redirect("/crearEgreso7");
        return response;
    }

    public ModelAndView cargarCriterio(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"cargar-criterio.hbs");
    }

    public Response postCargarCriterio(Request request, Response response){
        String nombreCriterio = request.queryParams("nombreCriterio");
        String categoria = request.queryParams("categoria");
        if (nombreCriterio != "" && categoria != ""){
            CriterioDeEmpresa unCriterio = new CriterioDeEmpresa();
            //builder.asignarCriterio(unCriterio);
        }
        System.out.println(nombreCriterio);
        System.out.println(categoria);
        response.redirect("/crearEgreso8");
        return response;
    }

    public ModelAndView cargarCriterioComplejo(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();
        EntityManagerHelper.createQuery("from CriterioDeEmpresa").getResultList().forEach((a) -> { criterios.add((CriterioDeEmpresa)a); });
        parametros.put("criterios", criterios);
        return new ModelAndView(parametros,"crearComplejos.hbs");
    }

    public Response postCargarCriterioComplejo(Request request, Response response){
        String nombreCriterio = request.queryParams("nombreCriterio");
        String primerCriterio = request.queryParams("primerCriterio");
        String segundoCriterio = request.queryParams("segundoCriterio");
        System.out.println(nombreCriterio);
        System.out.println(primerCriterio);
        System.out.println(segundoCriterio);
        response.redirect("/crearEgreso9");
        return response;
    }

    public ModelAndView cargarOrganizacion(Request request, Response response){
        if(!request.cookie("id").equals(request.session().id())){
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<Organizacion> organizaciones = new ArrayList<Organizacion>();
        EntityManagerHelper.createQuery("from Organizacion").getResultList().forEach((a) -> { organizaciones.add((Organizacion)a); });
        parametros.put("organizaciones", organizaciones);

        List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();
        EntityManagerHelper.createQuery("from CriterioDeEmpresa").getResultList().forEach((a) -> { criterios.add((CriterioDeEmpresa) a); });
        parametros.put("criterios", criterios);
        return new ModelAndView(parametros,"cargar-organizacion.hbs");
    }

    public Response postCargarOrganizacion(Request request, Response response){
        String nombreCriterio = request.queryParams("nombreCriterio");
        String primerCriterio = request.queryParams("organizacion");
        String segundoCriterio = request.queryParams("segundoCriterio");
        System.out.println(nombreCriterio);
        System.out.println(primerCriterio);
        System.out.println(segundoCriterio);
        //builder.asignarOrganizacion(organizacion);
        builder.confirmarEgreso();
        response.redirect("/");
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
