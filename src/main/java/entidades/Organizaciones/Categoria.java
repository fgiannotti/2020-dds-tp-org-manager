package entidades.Organizaciones;

import db.Converters.EntidadPersistente;
import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Presupuesto> presupuestos = new ArrayList<>();

    @Transient
    private List<OperacionEgreso> egresos = new ArrayList<>();

    public Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria(String descripcion, CriterioDeEmpresa criterio, List<Presupuesto> presupuestosOpcionales, List<OperacionEgreso> egresosOpcionales) {
        this.descripcion = descripcion;
        this.criterio = criterio;
        if (presupuestosOpcionales != null){
            this.presupuestos = presupuestosOpcionales;
        }else{
            this.presupuestos = new ArrayList<>();
        }
        if (egresosOpcionales != null){
            this.egresos = egresosOpcionales;
        }else{
            this.egresos = new ArrayList<>();
        }
    }
    public void agregarEgreso(OperacionEgreso egreso){
        this.egresos.add(egreso);
    }
    public void agregarPresupuesto(Presupuesto presupuesto){
        this.presupuestos.add(presupuesto);
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
