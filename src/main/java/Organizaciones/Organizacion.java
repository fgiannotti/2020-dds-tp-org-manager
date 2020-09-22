package Organizaciones;

import Operaciones.Operacion;
import Operaciones.OperacionIngreso;
import Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organizaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Organizacion extends EntidadPersistente {
    @Column(name="nombre_ficticio")
    private String nombreFicticio;
    @Transient
    private List<Operacion> operacionesRealizadas = new ArrayList<Operacion>();

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<OperacionIngreso> ingresos;


    protected Organizacion() {
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
}
