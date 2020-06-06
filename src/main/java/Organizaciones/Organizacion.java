package Organizaciones;

import Usuarios.Usuario;

public abstract class Organizacion {

    protected String nombreFicticio;

    protected Usuario usuario;

    public String getNombre_ficticio() {
        return nombreFicticio;
    }

    public void setNombre_ficticio(String nombre_ficticio) {
        this.nombreFicticio = nombre_ficticio;
    }

    public Organizacion(String nombre_ficticio, Usuario usuario) {
        this.nombreFicticio = nombre_ficticio;
        this.usuario = usuario;
    }
}
