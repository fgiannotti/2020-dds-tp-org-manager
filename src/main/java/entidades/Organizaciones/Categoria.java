package entidades.Organizaciones;

import db.Converters.EntidadPersistente;
import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorias")
public class Categoria extends EntidadPersistente {

    @Column
    private String descripcion;
    //{CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE}
    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "criterio_id", referencedColumnName = "id")
    private CriterioDeEmpresa criterio;

    public Categoria() {}

    public Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria(String descripcion, CriterioDeEmpresa criterio) {
        this.descripcion = descripcion;
        this.criterio = criterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CriterioDeEmpresa getCriterio() {
        return criterio;
    }

    public void setCriterio(CriterioDeEmpresa criterio) {
        this.criterio = criterio;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "descripcion='" + descripcion + '\'' +
                ", criterio=" + criterio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return getDescripcion().equals(categoria.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescripcion(), getCriterio());
    }
}
