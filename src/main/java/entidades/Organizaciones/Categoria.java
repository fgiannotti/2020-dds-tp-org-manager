package entidades.Organizaciones;

import db.Converters.EntidadPersistente;
import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria extends EntidadPersistente {

    @Column
    private String descripcion;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "criterio_id", referencedColumnName = "id")
    private CriterioDeEmpresa criterio;

    public Categoria() {
    }

    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.MERGE)
    private List<Presupuesto> presupuestos;

    @Transient
    private List<OperacionEgreso> egresos;

    public Categoria(String descripcion) {
        this.descripcion = descripcion;
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

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public List<OperacionEgreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<OperacionEgreso> egresos) {
        this.egresos = egresos;
    }
}
