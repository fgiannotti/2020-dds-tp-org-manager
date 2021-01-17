package entidades.Operaciones;

import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="proveedores")
public class Proveedor extends EntidadPersistente {
    @Override
    public String toString() {
        return "Proveedor{" +
                "nombreApellidoRazon='" + nombreApellidoRazon + '\'' +
                ", documento='" + documento + '\'' +
                ", direccionPostal='" + direccionPostal + '\'' +
                ", presupuesto=" + presupuesto +
                ", egreso=" + egreso.getId() +
                '}';
    }

    @Column
    private String nombreApellidoRazon;
    @Column
    private String documento;
    @Column
    private String direccionPostal;
    @OneToOne(cascade = {CascadeType.ALL})
    private Presupuesto presupuesto;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "operacion_id", referencedColumnName = "id")
    private OperacionEgreso egreso;

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

    public OperacionEgreso getEgreso() {
        return egreso;
    }

    public void setEgreso(OperacionEgreso egreso) {
        this.egreso = egreso;
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

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }


}
