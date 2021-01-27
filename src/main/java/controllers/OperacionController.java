package controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import db.EntityManagerHelper;
import entidades.Estrategias.Criterio;
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
import repositorios.*;
import scala.Int;
import server.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
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
    private Map<String,Proveedor> proveedorElegidoCache = new HashMap<>();

    private Map<String, List<MedioDePago>> mediosDePagoCache = new HashMap<>();
    public Map<String, Usuario> cacheUsuarios = new HashMap<>();
    private Map<String, List<Categoria>> categoriasCache = new HashMap<>();
    private RepoCategorias repoCategorias = new RepoCategorias();

    private Usuario buscarEnCache(String sessionID) {
        return cacheUsuarios.get(sessionID);
    }
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

        Map<String, Object> parametros = new HashMap<>();
        EntityManagerHelper.createQuery("from Proveedor").getResultList().forEach((a) -> {
            proveedores.add((Proveedor) a);
        });

        builder.nuevoEgreso();
        parametros.put("proveedores", proveedores);

        return new ModelAndView(parametros, "index-crear-egreso.hbs");
    }
    public Response postProveedorFechaYCantMin(Request request, Response response) {
        String proveedor = request.queryParams("proveedor");
        Proveedor unProveedorEntero = (Proveedor) EntityManagerHelper.createQuery("from Proveedor where nombreApellidoRazon = '" + proveedor + "'").getResultList().get(0);
        builder.asignarProveedor(unProveedorEntero);
        proveedorElegidoCache.put(request.session().id(),unProveedorEntero);
        String fecha = request.queryParams("fecha");
        int cantidadPresupuestos = Integer.parseInt(request.queryParams("cantidadMinima"));
        int valorTotal = Integer.parseInt(request.queryParams("valorTotal"));

        String desc = request.queryParams("descripcion");

        LocalDate unaFecha = LocalDate.parse(fecha);
        builder.asignarFechaPresupuestosMinYValor(unaFecha, cantidadPresupuestos, valorTotal);
        builder.asignarDescripcion(desc);
        response.redirect("/crearEgreso2");
        return response;
    }

    // /crearEgreso2
    public ModelAndView seleccionarPresupuesto(Request request, Response response) {
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
        } catch (Exception ignored) {
        }

        if (presupuestoFound == null) {
            presupuestoFound = repoPresupuestos.find(presupuestoID);
        }

        builder.asignarPresupuestosPreliminares(new ArrayList<Presupuesto>(Arrays.asList(presupuestoFound)));
        response.redirect("/crearEgreso3");
        return response;
    }
    // /crearEgreso3
    public ModelAndView medioDePago(Request request, Response response) throws UserNotFoundException {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }
        String userName = request.cookie("user");
        Usuario user = repoUsuarios.buscarPorNombre(userName);
        cacheUsuarios.put(request.session().id(), user);
        List<MedioDePago> mediosDePago = new ArrayList<>();

        EntityManagerHelper.createQuery("FROM OperacionEgreso").getResultList().forEach((a) -> {
            OperacionEgreso op = (OperacionEgreso) a;
            if (op.getOrganizacion().getId() == user.getOrganizacion().getId()) {
                mediosDePago.add(op.getMedioDePago());
            }
        });
        List<MedioDePago> mpsCache = mediosDePagoCache.getOrDefault(request.session().id(), new ArrayList<>());
        mediosDePago.addAll(mpsCache);
        builder.asignarOrganizacion(user.getOrganizacion());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mediosDePago", mediosDePago);
        return new ModelAndView(parametros, "crear-medio-pago.hbs");
    }

    public ModelAndView agregarMedioPago(Request request, Response response) throws UserNotFoundException {
        String nombreMP = request.queryParams("newMedioDePagoNombre");
        String numeroMP = request.queryParams("newMedioDePagoNumero");
        int tipoMP = Integer.parseInt(request.queryParams("medioDePagoTipo"));

        MedioDePago mp = null;
        switch (tipoMP) {
            case 1:
                mp = new Debito(nombreMP, numeroMP);
            case 2:
                mp = new Credito(nombreMP, numeroMP);
            case 3:
                mp = new ATM(nombreMP, numeroMP);
            case 4:
                mp = new Ticket(nombreMP, numeroMP);
            case 5:
                mp = new AccountMoney(nombreMP);
            default:
                mp = new Credito(nombreMP, numeroMP);

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
        List<MedioDePago> mediosDePagoCacheados = mediosDePagoCache.getOrDefault(request.session().id(), new ArrayList<>());

        MedioDePago medioDePagoEncontrado = null;
        for (MedioDePago mp : mediosDePagoCacheados) {
            if (mp.getId() == medioDePagoID) {
                medioDePagoEncontrado = mp;
                medioDePagoEncontrado.setId(0);
                break;
            }
        }
        if (medioDePagoEncontrado == null) {
            medioDePagoEncontrado = (MedioDePago) EntityManagerHelper.createQuery("FROM MedioDePago WHERE id = '" + medioDePagoID + "'").getSingleResult();
        }

        builder.asignarMedioDePago(medioDePagoEncontrado);
        boolean tieneComprobante = Boolean.parseBoolean(request.queryParams("tieneComprobante"));
        if (tieneComprobante) {
            response.redirect("/crearEgreso6");
        } else {
            response.redirect("/crearEgreso7");
        }
        return response;
    }

    // /crearEgreso6
    public ModelAndView cargarComprobante(Request request, Response response) {
        //Router.CheckIfAuthenticated(request,response);

        Map<String, Object> parametros = new HashMap<>();
        int proveedorID = proveedorElegidoCache.get(request.session().id()).getId();
        List<Presupuesto> presus = repoPresupuestos.findByProv(proveedorID);

        parametros.put("presupuestos",presus);
        return new ModelAndView(parametros, "cargar-comprobante.hbs");
    }

    public Response postCargarComprobante(Request request, Response response) {
        String tipoComp = request.queryParams("tipoComprobante");
        int nroComp = Integer.parseInt(request.queryParams("numeroComprobante"));
        String presupuestoID = request.queryParams("presupuestoID");

        Presupuesto presuElegido = repoPresupuestos.find(Integer.parseInt(presupuestoID));
        Comprobante comp = new Comprobante(nroComp,tipoComp,presuElegido.getItems());

        builder.unEgreso.setItems(presuElegido.getItems());
        builder.asignarComprobante(comp);

        response.redirect("/crearEgreso7");
        return response;
    }

    // /crearEgreso7
    public ModelAndView cargarCriterio(Request request, Response response) throws UserNotFoundException {
        if (!request.cookie("id").equals(request.session().id())) {
            response.redirect("/");
        }

        Usuario user = cacheUsuarios.get(request.session().id());
        if (user == null) {
            user = repoUsuarios.buscarPorNombre(request.cookie("user"));
        }

        List<CriterioDeEmpresa> criterioDeEmpresas = new ArrayList<>();
        EntityManagerHelper.createQuery("FROM CriterioDeEmpresa WHERE org_id= '" + user.getOrganizacion().getId() + "'").getResultList().forEach((a) -> {
            criterioDeEmpresas.add((CriterioDeEmpresa) a);
        });

        List<Categoria> allCats = new ArrayList<>();
        List<Categoria> catsCache = categoriasCache.get(request.session().id());
        if (catsCache != null){
            allCats.addAll(catsCache);
        }
        EntityManagerHelper.createQuery("FROM Categoria").getResultList().forEach((a) -> {
            allCats.add((Categoria) a);
        });

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("criterios", criterioDeEmpresas);
        parametros.put("categorias", allCats);
        return new ModelAndView(parametros, "cargar-criterio.hbs");
    }

    public ModelAndView postCargarCriterio(Request request, Response response) {
        String nombreCriterio = request.queryParams("nombreCriterio");
        String[] bodyParams = request.body().split("&");
        List<Categoria> categorias = getCategoriasFromCheckbox(bodyParams,categoriasCache.getOrDefault(request.session().id(), new ArrayList<>()));

        builder.asignarCategorias(categorias);
        builder.generarNroOperacion();
        boolean saveOK =true;
        try {
            builder.confirmarEgreso();
        }catch (Exception e){
            System.err.println(e.getMessage());
            saveOK = false;
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("saveEgreso",true);
        parametros.put("saveOK",saveOK);
        return new ModelAndView(parametros, "index-menu-revisor.hbs");
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

    public ModelAndView agregarCategoria2(Request request, Response response) throws UserNotFoundException {
        String nombreCat = request.queryParams("newCategoria");
        CriterioDeEmpresa criterio = (CriterioDeEmpresa) EntityManagerHelper.createQuery(
                "From CriterioDeEmpresa where id = '" + Integer.parseInt(request.queryParams("criterio")) + "'").getSingleResult();
        Categoria categoriaNueva = new Categoria(nombreCat, criterio);

        List<Categoria> categoriasFound = categoriasCache.getOrDefault(request.session().id(), new ArrayList<>());

        //seteo un id unico para encontrarlo dsp
        categoriaNueva.setId(UUID.randomUUID().hashCode());
        categoriasFound.add(categoriaNueva);

        categoriasCache.put(request.session().id(), categoriasFound);

        return this.cargarCriterio(request, response);
    }

    public ModelAndView cachePresupuesto(Request request, Response response) throws UserNotFoundException {
        List<Articulo> articulos = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        Integer itemCount = request.queryParams("itemCount") != null ? Integer.parseInt(request.queryParams("itemCount")) + 1 : 1;
        System.err.println("items al presu:" + itemCount);
        Integer i = 0;
        float valorTotalPresu = 0;
        while (i <= itemCount) {
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
            } catch (NullPointerException | NumberFormatException e) {
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

        return this.seleccionarPresupuesto(request, response);
    }

    private List<CriterioDeEmpresa> getCriteriosFromCheckbox(String[] bodyParams) {

        ArrayList<CriterioDeEmpresa> criterios = new ArrayList<>();
        //obtengo los ingresos y egresos del body
        for (String param : bodyParams) {
            String[] paramFieldValue = param.split("=");
            if (paramFieldValue[0].equals("criterio")) {
                Integer criterioID = Integer.parseInt(paramFieldValue[1]);
                criterios.add((CriterioDeEmpresa) EntityManagerHelper.createQuery(
                        "From CriterioDeEmpresa where id = '" + Integer.parseInt(paramFieldValue[1]) + "'").getSingleResult()
                );

            }
        }

        return criterios;
    }

    private List<Categoria> getCategoriasFromCheckbox(String[] bodyParams, List<Categoria> cache) {

        ArrayList<Categoria> categorias = new ArrayList<>();
        //o lo geteo de cache o de db
        for (String param : bodyParams) {
            String[] paramFieldValue = param.split("=");
            if (paramFieldValue[0].equals("categoria")) {
                Integer categoriaID = Integer.parseInt(paramFieldValue[1]);
                Categoria catFound = findCategoriaInCache(cache,categoriaID);
                if (catFound == null){
                    catFound = (Categoria) EntityManagerHelper.createQuery(
                            "From Categoria where id = '" + categoriaID + "'").getSingleResult();
                }

                categorias.add(catFound);
            }
        }

        return categorias;
    }
    private Categoria findCategoriaInCache(List<Categoria> cats, Integer idMatch){
        for (Categoria cat:cats){
            if (cat.getId() == idMatch){
                return cat;
            }
        }
        return null;
    }

    //no se usa
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
