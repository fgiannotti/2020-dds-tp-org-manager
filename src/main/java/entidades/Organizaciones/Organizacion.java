package entidades.Organizaciones;

import db.Converters.EntidadPersistente;
import entidades.Configuracion.Configuracion;
import entidades.Configuracion.ConfiguracionApi;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Vinculador.VinculadorApi;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@Table(name = "organizaciones")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion extends EntidadPersistente {
    @Column(name="nombre_ficticio")
    private String nombreFicticio;
    @Transient
    private List<Operacion> operacionesRealizadas = new ArrayList<Operacion>();

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<OperacionIngreso> ingresos;

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    protected Organizacion() {}

    public String getJsonVincular(){
        JSONArray jsonIngreso= this.crearJsonIngreso();
        JSONArray jsonEgreso= this.crearJsonEgreso();
        JSONObject json= new JSONObject();
        json.put("Ingresos",jsonIngreso);
        json.put("Egresos",jsonEgreso);
        ConfiguracionApi configApi = new ConfiguracionApi();
        json.put("Configuracion",configApi.getJsonConfig());
        return json.toString();
    }

    public void realizarVinculacion(){
        VinculadorApi vinculador = new VinculadorApi();
        Configuracion config = new Configuracion();
        JSONObject response = vinculador.Post_JSON(this.getJsonVincular(), config.getApiVinculador());
        this.vincularRelaciones(response);
    }

    protected String fechaToString(LocalDate fecha){
        return fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void vincular(JSONObject jsonViculaciones){
        Integer idIngreso = (Integer) jsonViculaciones.get("IDIngreso");
        JSONArray jsonEgresos = (JSONArray) jsonViculaciones.get("IDSEgresos");
        Optional<Operacion> operacionIngreso;
        operacionIngreso = this.getOperacionesRealizadas().stream().filter(operacion -> operacion.isIngreso() && operacion.getId()==idIngreso).findFirst();
        OperacionIngreso operacionIngreso2 = (OperacionIngreso) operacionIngreso.get();
        jsonEgresos.forEach((jsonConId) -> {
            System.out.println(jsonConId.toString());
            Optional<Operacion> operacionEgreso;
            operacionEgreso = this.getOperacionesRealizadas().stream().filter(operacion -> operacion.isEgreso() && operacion.getId()==Integer.parseInt(String.valueOf(jsonConId))).findFirst();
            OperacionEgreso operacionEgreso2 = (OperacionEgreso) operacionEgreso.get();

            operacionIngreso2.agregarOperacionEgresos(operacionEgreso2);
        });
    }

    public void vincularRelaciones(JSONObject jsonRelaciones){
        JSONArray jsonVinculos = (JSONArray) jsonRelaciones.get("Relaciones");
        jsonVinculos.forEach((jsonVinculo) -> {
            this.vincular((JSONObject) jsonVinculo);
        });
    }

    protected JSONArray jsonOperacional(Stream<Operacion> operacionStream){

        JSONArray jsonDeOperaciones = new JSONArray();
        operacionStream.forEach((operacion)-> {
            JSONObject jsonDeOperacion = new JSONObject();
            jsonDeOperacion.put("id",String.valueOf(operacion.getId()));
            jsonDeOperacion.put("fecha",this.fechaToString(operacion.getFecha()));
            jsonDeOperacion.put("monto",String.valueOf(operacion.getMontoTotal()));
            jsonDeOperaciones.put(jsonDeOperacion);
        });

        return jsonDeOperaciones;
    }

    protected JSONArray crearJsonEgreso(){
        return this.jsonOperacional(this.getOperacionesRealizadas().stream().filter(Operacion::isEgreso));
    }

    protected JSONArray crearJsonIngreso(){
        return this.jsonOperacional(this.getOperacionesRealizadas().stream().filter(Operacion::isIngreso));
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public Organizacion(String nombreFicticio) {
        this.nombreFicticio = Objects.requireNonNull(nombreFicticio, "El nombre ficticio no puede ser nulo");
        this.operacionesRealizadas = new ArrayList<Operacion>();
    }

    public void agregarOperacion(Operacion operacion){  this.operacionesRealizadas.add(operacion); }

    public void agregarCriterio(CriterioDeEmpresa criterio){  this.criterios.add(criterio); }

    public List<Operacion> getOperacionesRealizadas(){
        return this.operacionesRealizadas;
    }

    public void crearCriterioDeEmpresa(String nombre, List<CriterioDeEmpresa> criteriosHijos, List<Categoria> categorias){
        CriterioDeEmpresa nuevoCriterio = new CriterioDeEmpresa(nombre, criteriosHijos, categorias);
        this.agregarCriterio(nuevoCriterio);
    }

    public void crearCategoria(CriterioDeEmpresa criterioDeEmpresa, String descripcion){
        if(!this.criterios.contains(criterioDeEmpresa)){
            throw new RuntimeException("No es uno de mis criterios");
        }
        Categoria categoria = new Categoria(descripcion);
        criterioDeEmpresa.agregarCategoria(categoria);
    }

    public void setCriterios(List<CriterioDeEmpresa> criterios) {
        this.criterios = criterios;
    }

    public List<CriterioDeEmpresa> getCriterios() {
        return criterios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
