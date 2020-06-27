package Organizaciones;

import Operaciones.Operacion;
import Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Organizacion {
    private String nombreFicticio;
    private List<Operacion> operacionesRealizadas;

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

    public void agregarOperacion(Operacion operacion){
        this.operacionesRealizadas.add(operacion);
    }

    public List<Operacion> getOperacionesRealizadas(){
        return this.operacionesRealizadas;
    }
}
