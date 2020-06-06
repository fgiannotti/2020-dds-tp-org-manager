package Organizaciones;

import Usuarios.Usuario;

public class Base extends Organizacion {
    private String descripcion;
    private Juridica entidadPadre;

    public Base(String nombre_ficticio, Usuario usuario, String descripcion, Juridica padre) {
        super(nombre_ficticio, usuario);
        this.descripcion = descripcion;
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
                ", usuario=" + usuario +
                '}';
    }
}


