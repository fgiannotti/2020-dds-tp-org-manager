package Usuarios;

import Operaciones.Operacion;
import Organizaciones.*;

public class Usuario {
    private ClaseUsuario claseUsuario;
    private Organizaciones.Organizacion organizacionALaQuePertenece;
    private String nombre;
    private String password;

    public void cambiarClaseA (ClaseUsuario nuevaClase) {
        this.setClaseUsuario(nuevaClase);
    }

    public Usuario (String nombre, String password, Organizacion organizacion, ClaseUsuario clase) {
        this.nombre = nombre;
        this.password = password;
        this.organizacionALaQuePertenece = organizacion;
        this.claseUsuario = clase;
    }

    public void agregarOperacion (Operacion operacion) {
        this.organizacionALaQuePertenece.agregarOperacion(operacion);
    }


//////////////  Getters y setters   ////////////
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClaseUsuario getClaseUsuario() {
        return claseUsuario;
    }

    public void setClaseUsuario(ClaseUsuario claseUsuario) {
        this.claseUsuario = claseUsuario;
    }

    public Organizacion getOrganizacionALaQuePertenece() {
        return organizacionALaQuePertenece;
    }
}
