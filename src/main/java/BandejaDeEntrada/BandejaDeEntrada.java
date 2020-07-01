package BandejaDeEntrada;

import Estrategias.Filtro;

import java.util.ArrayList;
import java.util.List;

public class BandejaDeEntrada {
    private List<Resultado> resultados;
    private List<Filtro> filtros;

    public BandejaDeEntrada(List<Filtro> filtros){
        resultados = new ArrayList<Resultado>();
        this.filtros = filtros;
    }

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

    public void setFiltros(List<Filtro> filtros) { this.filtros = filtros; }
}