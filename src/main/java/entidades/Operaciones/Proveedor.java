package entidades.Operaciones;

import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="proveedores")
public class Proveedor extends EntidadPersistente {

    @Column
    private String nombreApellidoRazon;
    @Column
    private String documento;
    @Column
    private String direccionPostal;

    public Proveedor(String nombreApellidoRazon, String documento, String direccionPostal) {
        this.nombreApellidoRazon = Objects.requireNonNull(nombreApellidoRazon, "El nombreApellidoRazon no puede ser nulo");
        this.documento = Objects.requireNonNull(documento, "El documento no puede ser nulo");
        this.direccionPostal = Objects.requireNonNull(direccionPostal, "La direccion postal no puede ser nula");
    }

    public Proveedor() {
    }

    public String getNombreApellidoRazon() {
        return nombreApellidoRazon;
    }

    public void setNombreApellidoRazon(String nombreApellidoRazon) {
        this.nombreApellidoRazon = nombreApellidoRazon;
    }

    public String getnombreApellidoRazon() {
        return nombreApellidoRazon;
    }

    public void setnombreApellidoRazon(String nombreApellidoRazon) {
        this.nombreApellidoRazon = nombreApellidoRazon;
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

    @Override
    public String toString() {
        return "Proveedor{" +
                "nombreApellidoRazon='" + nombreApellidoRazon + '\'' +
                ", documento='" + documento + '\'' +
                ", direccionPostal='" + direccionPostal + '\'' +
                '}';
    }
}
