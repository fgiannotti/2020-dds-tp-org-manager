package Organizaciones;

import Usuarios.Usuario;

public abstract class Organizacion {

    protected String nombreFicticio;

    protected Usuario usuario;

    public String getNombre_ficticio() {
        return nombreFicticio;
    }

    public void setNombre_ficticio(String nombre_ficticio) {
        this.nombre_ficticio = nombre_ficticio;
    }

    public Organizacion(String nombre_ficticio, Usuario usuario) {
        this.nombre_ficticio = nombre_ficticio;
        this.usuario = usuario;
    }
}
