package Organizaciones;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bases")
public class Base extends Organizacion {
    @Column
    private String descripcion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "juridica_id", referencedColumnName = "id")
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



