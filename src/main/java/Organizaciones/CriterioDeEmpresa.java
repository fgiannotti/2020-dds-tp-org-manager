package Organizaciones;

import java.util.List;

public class CriterioDeEmpresa {
    private String nombre;
    private List<CriterioDeEmpresa> criteriosHijos;
    private List<Categoria> categorias;

    public CriterioDeEmpresa(String nombre, List<CriterioDeEmpresa> criteriosMenores, List<Categoria> categorias) {
        this.nombre = nombre;
        this.criteriosHijos = criteriosMenores;
        this.categorias = categorias;
    }

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
}
