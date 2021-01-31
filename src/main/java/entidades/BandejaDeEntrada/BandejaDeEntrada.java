package entidades.BandejaDeEntrada;

import db.Converters.EntidadPersistente;
import entidades.Estrategias.Filtro;
import entidades.Usuarios.Revisor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bandejas_de_entrada")
public class BandejaDeEntrada extends EntidadPersistente {
    @OneToMany(mappedBy = "bandeja", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Resultado> resultados = new ArrayList<Resultado>();
    @OneToMany(mappedBy = "bandeja", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Filtro> filtros = new ArrayList<Filtro>();

    public BandejaDeEntrada(List<Filtro> filtros){
        resultados = new ArrayList<Resultado>();
        this.filtros = filtros;
    }
    public BandejaDeEntrada(){}
    public void mostrarMensajes() {
        List<Resultado> resultadosFiltrados = resultados;
        for(Filtro f: filtros){
            resultadosFiltrados = f.filtrar(resultadosFiltrados);
        }
        resultadosFiltrados.forEach(resultado -> resultado.mostrarResultado());
    }

    public void guardarResultado(Resultado resultado){
        resultados.add(resultado);
    }

    public List<Resultado> getResultadosFiltrados() {
        List<Resultado> resultadosFiltrados = resultados;
        for(Filtro f: filtros){
            resultadosFiltrados = f.filtrar(resultadosFiltrados);
        }
        return resultadosFiltrados;
    }
    public void setFiltros(List<Filtro> filtros) { this.filtros = filtros; }
    public List<Filtro> getFiltros() { return this.filtros; }

}
