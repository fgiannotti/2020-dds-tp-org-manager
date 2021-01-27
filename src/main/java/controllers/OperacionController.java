package controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.*;
import entidades.Operaciones.*;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.CriterioDeEmpresa;
import entidades.Organizaciones.Organizacion;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;
import org.apache.commons.compress.utils.IOUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import repositorios.RepoOperacionesEgresos;
import repositorios.RepoPresupuestos;
import repositorios.RepoUsuarios;
import repositorios.UserNotFoundException;
import scala.Int;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;

public class OperacionController {
    private RepoOperacionesEgresos repoOperacionesEgresos = new RepoOperacionesEgresos();
    private RepoPresupuestos repoPresupuestos = new RepoPresupuestos();
    private RepoUsuarios repoUsuarios = new RepoUsuarios();
    private EgresoBuilder builder = new EgresoBuilder();
    private Map<String, String> egresosFileName = new HashMap<>();
    private Map<String, Proveedor> proveedorCache = new HashMap<>();
    private Map<String, List<Presupuesto>> presupuestoCache = new HashMap<>();
    private Map<String,  List<MedioDePago>> mediosDePagoCache = new HashMap<>();

    public ModelAndView verEgreso(Request request, Response response) {
        String egresoID = request.params("id");
        OperacionEgreso operacionEgreso = repoOperacionesEgresos.get(new Integer(egresoID));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("egreso", operacionEgreso);
        boolean tieneIngreso = operacionEgreso.getIngreso() != null;
        parametros.put("tiene-ingreso", tieneIngreso);
        if (tieneIngreso) {
            parametros.put("ingreso", operacionEgreso.getIngreso());
        }
        parametros.put("presupuestos", operacionEgreso.getPresupuestosPreliminares());
        //GET FILE
        parametros.put("egresoID", egresoID);
        Boolean tieneFile = egresosFileName.get(egresoID) != null;
        parametros.put("tieneFile", tieneFile);
        if (tieneFile) {
            parametros.put("fileName", egresosFileName.get(egresoID));
        }
        System.err.println(egresosFileName.get(egresoID));

        return new ModelAndView(parametros, "egreso.hbs");
    }

    public Response upload(Request req, Response response) throws ServletException, IOException {
        Map<String, Object> parametros = new HashMap<>();
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("src/main/resources/public/files/"));
        Part filePart;
        try {
            filePart = req.raw().getPart("myfile");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            throw (e);
        }
        String egresoID = req.params("id");
        try (InputStream inputStream = filePart.getInputStream()) {
            String fileName = filePart.getSubmittedFileName();
            OutputStream outputStream = new FileOutputStream("src/main/resources/public/files/" + fileName);
            egresosFileName.put(egresoID, fileName);

            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("File uploaded and saved.");
        response.redirect("/egreso/" + egresoID);
        return response;

    }


    public ModelAndView inicio(Request request, Response response) {
        Router.CheckIfAuthenticated(request, response);

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        List<Comprobante> comprobantes = new ArrayList<Comprobante>();
        List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
        List<Item> items = new ArrayList<Item>();
        List<Articulo> articulos = new ArrayList<Articulo>();
        List<OperacionIngreso> ingresos = new ArrayList<OperacionIngreso>();

        Map<String, Object> parametros = new HashMap<>();
        EntityManagerHelper.createQuery("from Proveedor").getResultList().forEach((a) -> {
            proveedores.add((Proveedor) a);
        });
        parametros.put("proveedores", proveedores);
        EntityManagerHelper.createQuery("from Comprobante").getResultList().forEach((a) -> {
            comprobantes.add((Comprobante) a);
        });
        parametros.put("comprobantes", comprobantes);
        EntityManagerHelper.createQuery("from Presupuesto").getResultList().forEach((a) -> {
            presupuestos.add((Presupuesto) a);
        });
        parametros.put("presupuestos", presupuestos);
        EntityManagerHelper.createQuery("from Articulo").getResultList().forEach((a) -> {
            articulos.add((Articulo) a);
        });
        parametros.put("articulos", articulos);
        EntityManagerHelper.createQuery("from Item").getResultList().forEach((a) -> {
            items.add((Item) a);
        });
        parametros.put("items", items);
        EntityManagerHelper.createQuery("from OperacionIngreso").getResultList().forEach((a) -> {
            ingresos.add((OperacionIngreso) a);
        });
        parametros.put("ingresos", ingresos);
        builder.nuevoEgreso();
        parametros.put("proveedores", proveedores);

        return new ModelAndView(parametros, "index-crear-egreso.hbs");
    }

    public ModelAndView fechaYCantidad(Request request, Response response) {
        Router.CheckIfAuthenticated(request, response);
        builder.nuevoEgreso();

        Map<String, Object> parametros = new HashMap<>();
        List<Proveedor> proveedores = new ArrayList<>();
        EntityManagerHelper.createQuery("from Proveedor").getResultList().forEach((a) -> {
            proveedores.add((Proveedor) a);
        });
        parametros.put("proveedores", proveedores);
        return new ModelAndView(parametros, "index-crear-egreso.hbs");
    }

    public Response postProveedorFechaYCantMin(Request request, Response response) {
        String proveedor = request.queryParams("proveedor");
        Proveedor unProveedorEntero = (Proveedor) EntityManagerHelper.createQuery("from Proveedor where nombreApellidoRazon = '" + proveedor + "'").getResultList().get(0);
        builder.asignarProveedor(unProveedorEntero);

        String fecha = request.queryParams("fecha");
        int cantidadPresupuestos = Integer.parseInt(request.queryParams("cantidadMinima"));
        int valorTotal = Integer.parseInt(request.queryParams("valorTotal"));


        LocalDate unaFecha = LocalDate.parse(fecha);
        builder.asignarFechaPresupuestosMinYValor(unaFecha, cantidadPresupuestos, valorTotal);
        response.redirect("/crearEgreso2");
        return response;
    }


    public ModelAndView seleccionarProveedor(Request request, Response response) {
        Router.CheckIfAuthenticated(request, response);

        Map<String, Object> parametros = new HashMap<>();
        List<Presupuesto> presupuestos = new ArrayList<>();
        Proveedor proveedor = builder.unEgreso.getProveedores().get(0);
        EntityManagerHelper.createQuery("FROM Presupuesto WHERE proveedor_id = '" + proveedor.getId() + "'").getResultList().forEach((a) -> {
            presupuestos.add((Presupuesto) a);
        });
        proveedorCache.put(request.session().id(), proveedor);
        List<Presupuesto> presupuestosCacheList = (List<Presupuesto>) presupuestoCache.getOrDefault(request.session().id(), new ArrayList<Presupuesto>());
        presupuestos.addAll(presupuestosCacheList);

        parametros.put("proveedorElegido", proveedor);
        parametros.put("presupuestos", presupuestos);

        return new ModelAndView(parametros, "index-seleccionar-proveedores.hbs");
    }

    public Response postSeleccionarPresupuesto(Request request, Response response) {
        int presupuestoID = Integer.parseInt(request.queryParams("presupuestoID"));
        List<Presupuesto> presupuestos = presupuestoCache.get(request.session().id());
        Presupuesto presupuestoFound = null;
        try {
            for (Presupuesto p : presupuestos) {
                if (p.getIdCacheado().equals(request.session().id())) {
                    presupuestoFound = p;
                }
            }
        }catch (Exception ignored){}

        if(presupuestoFound == null){
            presupuestoFound = repoPresupuestos.find(presupuestoID);
        }

        builder.asignarPresupuestosPreliminares(new ArrayList<Presupuesto>(Arrays.asList(presupuestoFound)));
        response.redirect("/crearEgreso3");
        return response;
    }

    public ModelAndView medioDePago(Request request, Response response) throws UserNotFoundException {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        String userName = request.cookie("user");
        Usuario user = repoUsuarios.buscarPorNombre(userName);
        List<MedioDePago> mediosDePago = new ArrayList<>();

        EntityManagerHelper.createQuery("FROM OperacionEgreso").getResultList().forEach((a) -> {
            OperacionEgreso op = (OperacionEgreso) a;
            if (op.getOrganizacion().getId() == user.getOrganizacion().getId()){
                mediosDePago.add(op.getMedioDePago());
            }
        });
        List<MedioDePago> mpsCache = mediosDePagoCache.getOrDefault(request.session().id(),new ArrayList<>());
        mediosDePago.addAll(mpsCache);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mediosDePago",mediosDePago);
        return new ModelAndView(parametros, "crear-medio-pago.hbs");
    }

    public ModelAndView agregarMedioPago(Request request, Response response) throws UserNotFoundException {
        String nombreMP = request.queryParams("newMedioDePagoNombre");
        String numeroMP = request.queryParams("newMedioDePagoNumero");
        int tipoMP = Integer.parseInt(request.queryParams("medioDePagoTipo"));

        MedioDePago mp = null;
        switch (tipoMP){
            case 1:
                mp = new Debito(nombreMP,numeroMP);
            case 2:
                mp = new Credito(nombreMP,numeroMP);
            case 3:
                mp = new ATM(nombreMP,numeroMP);
            case 4:
                mp = new Ticket(nombreMP,numeroMP);
            case 5:
                mp = new AccountMoney(nombreMP);
            default:
                mp = new Credito(nombreMP,numeroMP);

        }

        List<MedioDePago> mediosDePagoFound = mediosDePagoCache.get(request.session().id());
        if (mediosDePagoFound == null) {
            mediosDePagoFound = new ArrayList<>();
        }
        //seteo un id unico para encontrarlo dsp
        mp.setId(UUID.randomUUID().hashCode());
        mediosDePagoFound.add(mp);

        mediosDePagoCache.put(request.session().id(), mediosDePagoFound);
        return this.medioDePago(request, response);
    }

    public Response postMedioDePago(Request request, Response response) {
        int medioDePagoID = Integer.parseInt(request.queryParams("medioDePago"));
        List<MedioDePago> mediosDePagoFound = mediosDePagoCache.getOrDefault(request.session().id(),new ArrayList<>());

        MedioDePago medioDePagoEncontrado = null;
        for(MedioDePago mp: mediosDePagoFound){
            if (mp.getId() == medioDePagoID){
                medioDePagoEncontrado = mp;
                medioDePagoEncontrado.setId(0);
                break;
            }
        }
        if (medioDePagoEncontrado==null){
            medioDePagoEncontrado = (MedioDePago) EntityManagerHelper.createQuery("FROM MedioDePago WHERE id = '" + medioDePagoID + "'").getSingleResult();
        }

        builder.asignarMedioDePago(medioDePagoEncontrado);
        boolean tieneComprobante = Boolean.parseBoolean(request.queryParams("tieneComprobante"));
        if (tieneComprobante){
            response.redirect("/crearEgreso6");
        }else{
            response.redirect("/crearEgreso7");
        }
        return response;
    }

    public ModelAndView seleccionArticulos(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<Articulo> articulos = new ArrayList<Articulo>();
        EntityManagerHelper.createQuery("from Articulo").getResultList().forEach((a) -> {
            articulos.add((Articulo) a);
        });
        parametros.put("articulos", articulos);
        return new ModelAndView(parametros, "seleccion-articulos.hbs");
    }

    public Response postSeleccionArticulos(Request request, Response response) {
        String nombre = request.queryParams("nombreArticulo");
        Articulo articulo = (Articulo) EntityManagerHelper.createQuery("from Articulo where nombre = '" + nombre + "'").getResultList().get(0);
        builder.asignarArticulo(articulo);
        response.redirect("/crearEgreso6");
        return response;
    }

    public ModelAndView cargarComprobante(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "cargar-comprobante.hbs");
    }

    public Response postCargarComprobante(Request request, Response response) {
        String tipoComprobante = request.queryParams("tipoComprobante");
        String stringNum = request.queryParams("numeroComprobante");
        if (stringNum.equals("")) {
            int numeroComprobante = Integer.parseInt(request.queryParams("numeroComprobante"));
            System.out.println(numeroComprobante);
        }
        System.out.println(tipoComprobante);
        response.redirect("/crearEgreso7");
        return response;
    }

    public ModelAndView cargarCriterio(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "cargar-criterio.hbs");
    }

    public Response postCargarCriterio(Request request, Response response) {
        String nombreCriterio = request.queryParams("nombreCriterio");
        String categoria = request.queryParams("categoria");
        if (!nombreCriterio.equals("") && categoria != "") {
            CriterioDeEmpresa unCriterio = new CriterioDeEmpresa();
            //builder.asignarCriterio(unCriterio);
        }
        System.out.println(nombreCriterio);
        System.out.println(categoria);
        response.redirect("/crearEgreso8");
        return response;
    }

    public ModelAndView cargarCriterioComplejo(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();
        EntityManagerHelper.createQuery("from CriterioDeEmpresa").getResultList().forEach((a) -> {
            criterios.add((CriterioDeEmpresa) a);
        });
        parametros.put("criterios", criterios);
        return new ModelAndView(parametros, "crearComplejos.hbs");
    }

    public Response postCargarCriterioComplejo(Request request, Response response) {
        String nombreCriterio = request.queryParams("nombreCriterio");
        String primerCriterio = request.queryParams("primerCriterio");
        String segundoCriterio = request.queryParams("segundoCriterio");
        System.out.println(nombreCriterio);
        System.out.println(primerCriterio);
        System.out.println(segundoCriterio);
        response.redirect("/crearEgreso9");
        return response;
    }

    public ModelAndView cargarOrganizacion(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        Map<String, Object> parametros = new HashMap<>();
        List<Organizacion> organizaciones = new ArrayList<Organizacion>();
        EntityManagerHelper.createQuery("from Organizacion").getResultList().forEach((a) -> {
            organizaciones.add((Organizacion) a);
        });
        parametros.put("organizaciones", organizaciones);

        List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();
        EntityManagerHelper.createQuery("from CriterioDeEmpresa").getResultList().forEach((a) -> {
            criterios.add((CriterioDeEmpresa) a);
        });
        parametros.put("criterios", criterios);
        return new ModelAndView(parametros, "cargar-organizacion.hbs");
    }

    public Response postCargarOrganizacion(Request request, Response response) {
        String nombreCriterio = request.queryParams("nombreCriterio");
        String primerCriterio = request.queryParams("organizacion");
        String segundoCriterio = request.queryParams("segundoCriterio");
        System.out.println(nombreCriterio);
        System.out.println(primerCriterio);
        System.out.println(segundoCriterio);
        //builder.asignarOrganizacion(organizacion);
        builder.confirmarEgreso();
        response.redirect("/home");
        return response;
    }

    public ModelAndView cachePresupuesto(Request request, Response response) throws UserNotFoundException {
        List<Articulo> articulos = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        Integer itemCount = request.queryParams("itemCount") != null ? Integer.parseInt(request.queryParams("itemCount"))+1:1;
        System.err.println("items al presu:"+ itemCount);
        Integer i = 0;
        float valorTotalPresu = 0;
        while(i<=itemCount){
            try {
                String itemName = request.queryParams("itemName" + i);
                Integer cantidadItem = Integer.valueOf(request.queryParams("cantidadItem" + i));
                Float valorItem = Float.valueOf(request.queryParams("valorItem" + i));
                String descripcionItem = request.queryParams("descripcionItem" + i);

                Articulo articulo = new Articulo(descripcionItem, valorItem, descripcionItem);
                Item item = new Item(descripcionItem, itemName, new ArrayList<>(Arrays.asList(articulo)));
                valorTotalPresu += valorItem * cantidadItem;
                articulos.add(articulo);
                items.add(item);
            }catch(NullPointerException | NumberFormatException e){
                break;
            }
            i++;
        }

        Proveedor proveedor = proveedorCache.get(request.session().id());

        Presupuesto presupuesto = new Presupuesto(items, items.size(), valorTotalPresu, proveedor, null);
        presupuesto.setId(UUID.randomUUID().hashCode());
        presupuesto.setIdCacheado(request.session().id());

        List<Presupuesto> presupuestosCacheList = presupuestoCache.getOrDefault(request.session().id(), new ArrayList<Presupuesto>());

        presupuestosCacheList.add(presupuesto);
        presupuestoCache.put(request.session().id(), presupuestosCacheList);

        return this.seleccionarProveedor(request, response);
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
        /*public Response crearOperacionEgreso(Request request, Response response) {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        OperacionEgreso operacionEgreso = new OperacionEgreso();
        return response;
    }*/
}
