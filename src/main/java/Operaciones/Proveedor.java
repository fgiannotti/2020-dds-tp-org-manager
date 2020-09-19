package Operaciones;

import converters.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name="proveedores")
public class Proveedor extends EntidadPersistente {
    @Column
    private String nombre_apellido_razon;
    @Column
    private String documento;
    @Column
    private String direccionPostal;
    @Transient
    private Presupuesto presupuesto;

    public Proveedor(String nombre_apellido_razon, String documento, String direccionPostal) {
        this.nombre_apellido_razon = Objects.requireNonNull(nombre_apellido_razon, "El nombre_apellido_razon no puede ser nulo");
        this.documento = Objects.requireNonNull(documento, "El documento no puede ser nulo");
        this.direccionPostal = Objects.requireNonNull(direccionPostal, "La direccion postal no puede ser nula");
    }

    public Proveedor() {
    }

    public String getNombre_apellido_razon() {
        return nombre_apellido_razon;
    }

    public void setNombre_apellido_razon(String nombre_apellido_razon) {
        this.nombre_apellido_razon = nombre_apellido_razon;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }


    @Override
    public String toString() {
        return "Proveedor{" +
                "nombre_apellido_razon='" + nombre_apellido_razon + '\'' +
                ", documento='" + documento + '\'' +
                ", direccionPostal='" + direccionPostal + '\'' +
                '}';
    }
}
