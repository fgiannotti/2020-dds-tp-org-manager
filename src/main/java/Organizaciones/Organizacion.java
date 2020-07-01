package Organizaciones;

import Operaciones.Operacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Organizacion {
    private String nombreFicticio;
    private List<Operacion> operacionesRealizadas = new ArrayList<Operacion>();
    private List<CriterioDeEmpresa> criterios = new ArrayList<CriterioDeEmpresa>();

    public List<CriterioDeEmpresa> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<CriterioDeEmpresa> criterios) {
        this.criterios = criterios;
    }

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


}
