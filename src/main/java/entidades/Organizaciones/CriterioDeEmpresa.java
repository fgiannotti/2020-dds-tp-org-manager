package entidades.Organizaciones;

import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="criterios_empresas")
public class CriterioDeEmpresa extends EntidadPersistente {
    @Column
    private String nombre;
    @Transient
    private List<CriterioDeEmpresa> criteriosHijos = new ArrayList<CriterioDeEmpresa>();
    @OneToMany(mappedBy = "criterio", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Categoria> categorias = new ArrayList<Categoria>();

    @ManyToOne(cascade = {CascadeType.ALL})
    private Organizacion organizacion;

    public CriterioDeEmpresa(String nombre, List<CriterioDeEmpresa> criteriosMenoresOpcionales, List<Categoria> categoriasOpcionales) {
        this.nombre = nombre;
        this.criteriosHijos = criteriosMenoresOpcionales != null ? criteriosMenoresOpcionales : this.criteriosHijos;
        this.categorias = categoriasOpcionales != null ? categoriasOpcionales : this.categorias;
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void agregarCriterio(CriterioDeEmpresa unCriterio){
        this.criteriosHijos.add(unCriterio);
    }

    public void agregarCategoria(Categoria unaCategoria){
        this.categorias.add(unaCategoria);
    }
}
