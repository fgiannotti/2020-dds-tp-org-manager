package entidades.Usuarios;

import db.Converters.EntidadPersistente;
import entidades.Operaciones.Operacion;
import entidades.Organizaciones.Organizacion;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Usuario extends EntidadPersistente {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;
    @Column(name = "nombre",unique = true)
    private String nombre;
    @Column(name = "contraseña")
    private String password;
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    
    public Usuario(){}

    public Usuario (String nombre, String password, Organizacion organizacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.password = Objects.requireNonNull(password, "La contraseña no puede ser nula");
        this.organizacion = Objects.requireNonNull(organizacion, "La organizacion no puede ser nula");
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

    public Organizacion getOrganizacionALaQuePertenece() {
        return organizacion;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
