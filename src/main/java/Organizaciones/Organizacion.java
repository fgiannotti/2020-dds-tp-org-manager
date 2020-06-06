package Organizaciones;

import Usuarios.Usuario;

import java.util.Objects;

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
        this.nombreFicticio = Objects.requireNonNull(nombre_ficticio, "El nombre ficticio no puede ser nulo");
        this.usuario = Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
    }
}
