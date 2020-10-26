package entidades.Organizaciones;

import db.Converters.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name="categorias")
public class Categoria extends EntidadPersistente {
    @Column
    private String descripcion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "criterio_id", referencedColumnName = "id")
    private CriterioDeEmpresa criterio;

    public Categoria(){
    }

    public Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
