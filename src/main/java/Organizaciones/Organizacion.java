package Organizaciones;

import Usuarios.Usuario;

public abstract class Organizacion {
    protected String nombre_ficticio;

    public String getNombre_ficticio() {
        return nombre_ficticio;
    }

    public void setNombre_ficticio(String nombre_ficticio) {
        this.nombre_ficticio = nombre_ficticio;
    }


    public Organizacion(String nombre_ficticio, Usuario user) {
        this.nombre_ficticio = nombre_ficticio;
    }
}
