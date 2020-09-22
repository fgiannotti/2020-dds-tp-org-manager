package Organizaciones;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
@DiscriminatorValue("base")
public class Base extends Organizacion {
    @Column
    private String descripcion;
    @Transient//@OneToMany(name="entidad_padre")
    private Juridica entidadPadre;

    public Base() {
    }

    public Base(String nombre_ficticio, String descripcion, Juridica padre) {
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

    public Juridica getEntidadPadre() {
        return entidadPadre;
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



