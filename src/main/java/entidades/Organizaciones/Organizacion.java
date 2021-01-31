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
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "organizaciones")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="nombre_ficticio",unique = true)
    private String nombreFicticio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organizacion)) return false;
        Organizacion that = (Organizacion) o;
        return getId() == that.getId() &&
                getNombreFicticio().equals(that.getNombreFicticio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombreFicticio());
    }

    protected Organizacion() {}

    public String getJsonVincular(){
        //JSONArray jsonIngreso= this.crearJsonIngreso();
        //JSONArray jsonEgreso= this.crearJsonEgreso();
        JSONObject json= new JSONObject();
        //json.put("Ingresos",jsonIngreso);
        //json.put("Egresos",jsonEgreso);
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

    public void vincular(JSONObject jsonViculaciones, List<OperacionIngreso> ingresos,List<OperacionEgreso> egresos){
        Integer idIngreso = (Integer) jsonViculaciones.get("IDIngreso");
        JSONArray jsonEgresos = (JSONArray) jsonViculaciones.get("IDSEgresos");
        Optional<OperacionIngreso> operacionIngreso;

        operacionIngreso = ingresos.stream().filter(operacion -> operacion.getId()==idIngreso).findFirst();
        OperacionIngreso operacionIngreso2 = operacionIngreso.get();

        jsonEgresos.forEach((jsonConId) -> {
            Optional<OperacionEgreso> operacionEgreso;
            operacionEgreso = egresos.stream().filter(operacion -> operacion.getId()==Integer.parseInt(String.valueOf(jsonConId))).findFirst();
            OperacionEgreso operacionEgreso2 = operacionEgreso.get();

            operacionIngreso2.agregarOperacionEgreso(operacionEgreso2);
        });
    }

    public void vincularRelaciones(JSONObject jsonRelaciones){
        JSONArray jsonVinculos = (JSONArray) jsonRelaciones.get("Relaciones");
        jsonVinculos.forEach((jsonVinculo) -> {
            this.vincular((JSONObject) jsonVinculo,null,null);
        });
    }

    public JSONArray jsonOperacionalEgreso(Stream<OperacionEgreso> operacionStream){

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

    public JSONArray jsonOperacionalIngreso(Stream<OperacionIngreso> operacionStream){

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

    /*protected JSONArray crearJsonEgreso(){
        return this.jsonOperacionalEgreso(this.getEgresos().stream());
    }

    protected JSONArray crearJsonIngreso(){
        return this.jsonOperacionalIngreso(this.getIngresos().stream());
    }*/

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public Organizacion(String nombreFicticio) {
        this.nombreFicticio = Objects.requireNonNull(nombreFicticio, "El nombre ficticio no puede ser nulo");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
