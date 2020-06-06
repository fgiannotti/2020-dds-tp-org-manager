package Organizaciones;

import Operaciones.Operacion;
import Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public abstract class Organizacion {
    private String nombreFicticio;
    private List<Operacion> operacionesRealizadas;

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public Organizacion(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
        this.operacionesRealizadas = new ArrayList<Operacion>();
    }

    public void agregarOperacion(Operacion operacion){
        this.operacionesRealizadas.add(operacion);
    }

    public List<Operacion> getOperacionesRealizadas(){
        return this.operacionesRealizadas;
    }
}
