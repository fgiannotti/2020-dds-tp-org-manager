package entidades.Usuarios;

import db.Converters.EntidadPersistente;
import entidades.Operaciones.Operacion;
import entidades.Organizaciones.Organizacion;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="usuarios")
public class Usuario extends EntidadPersistente {
    @Transient
    private ClaseUsuario claseUsuario;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contraseña")
    private String password;

    public Usuario(){}

    public void cambiarClaseA (ClaseUsuario nuevaClase) {
        this.setClaseUsuario(nuevaClase);
    }

    public Usuario (String nombre, String password, Organizacion organizacion, ClaseUsuario clase) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.password = Objects.requireNonNull(password, "La contraseña no puede ser nula");
        this.organizacion = Objects.requireNonNull(organizacion, "La organizacion no puede ser nula");
        this.claseUsuario = clase;
    }

    public void agregarOperacion (Operacion operacion) {
        this.organizacion.agregarOperacion(operacion);
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
        return organizacion;
    }
}
