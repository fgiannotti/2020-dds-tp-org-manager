package Organizaciones;

import Usuarios.Usuario;

import java.util.Objects;

public class Base extends Organizacion {
    private String descripcion;
    private Juridica entidadPadre;

    public Base(String nombre_ficticio, Usuario usuario, String descripcion, Juridica padre) {
        super(nombre_ficticio);
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.entidadPadre = padre;
        padre.addEntidadHija(this);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEntidadPadre(Juridica org_padre) {
        this.entidadPadre = org_padre;
    }

    @Override
    public String toString() {
        return "Base{" +
                "descripcion='" + descripcion + '\'' +
                ", org_padre=" + entidadPadre +
                ", nombre_ficticio='" + descripcion + '\'' +
                '}';
    }
}



