package Organizaciones;

import java.util.List;

public class Criterio {
    private String nombre;
    private List<Criterio> criteriosMenores;
    private List<Categoria> categorias;

    public Criterio(String nombre, List<Criterio> criteriosMenores, List<Categoria> categorias) {
        this.nombre = nombre;
        this.criteriosMenores = criteriosMenores;
        this.categorias = categorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Criterio> getCriteriosMenores() {
        return criteriosMenores;
    }

    public void setCriteriosMenores(List<Criterio> criteriosMenores) {
        this.criteriosMenores = criteriosMenores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
