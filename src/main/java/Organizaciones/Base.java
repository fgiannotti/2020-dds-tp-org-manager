package Organizaciones;

import Usuarios.Usuario;

public class Base extends Organizacion {
    private String descripcion;
    private Juridica org_padre;

    public Base(String nombre_ficticio, String descripcion, Juridica org_padre) {
        super(nombre_ficticio);
        this.descripcion = descripcion;
        this.org_padre = org_padre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setOrg_padre(Juridica org_padre) {
        this.org_padre = org_padre;
    }
}
