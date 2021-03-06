package entidades.Organizaciones;

import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="criterios_empresas")
public class CriterioDeEmpresa extends EntidadPersistente {
    @Column
    private String nombre;
    @Transient
    private List<CriterioDeEmpresa> criteriosHijos = new ArrayList<CriterioDeEmpresa>();

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "org_id")
    private Organizacion organizacion;


    public CriterioDeEmpresa(String nombre, List<CriterioDeEmpresa> criteriosMenoresOpcionales, Organizacion organizacion) {
        this.nombre = nombre;
        this.criteriosHijos = criteriosMenoresOpcionales != null ? criteriosMenoresOpcionales : this.criteriosHijos;
        this.organizacion = organizacion;
    }

    public CriterioDeEmpresa(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CriterioDeEmpresa> getCriteriosHijos() {
        return criteriosHijos;
    }

    public void setCriteriosHijos(List<CriterioDeEmpresa> criteriosHijos) {
        this.criteriosHijos = criteriosHijos;
    }

    public void agregarCriterio(CriterioDeEmpresa unCriterio){
        this.criteriosHijos.add(unCriterio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriterioDeEmpresa)) return false;
        CriterioDeEmpresa that = (CriterioDeEmpresa) o;
        return getNombre().equals(that.getNombre()) &&
                Objects.equals(organizacion, that.organizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }
}
