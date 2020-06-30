package BandejaDeEntrada;

import Estrategias.Filtro;

import java.util.List;

public class BandejaDeEntrada {
    private List<Resultado> resultados;
    private List<Filtro> filtros;

    public void mostraMensajes() {
        List<Resultado> resultadosFiltrados = resultados;
        for(Filtro f: filtros){
            resultadosFiltrados = f.filtrar(resultadosFiltrados);
        }
        resultadosFiltrados.forEach(resultado -> resultado.mostrarResultado());
    }

    public void guardarResultado (Resultado resultado){
        resultados.add(resultado);
    }
}
