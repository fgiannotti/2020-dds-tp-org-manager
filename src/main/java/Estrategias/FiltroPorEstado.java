package Estrategias;

import BandejaDeEntrada.Resultado;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorEstado implements Filtro{
    private  String estado;

    public FiltroPorEstado(String estado){
        this.estado = estado;
    }

    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return resultados.stream().filter(resultado ->
                resultado.getEstadoValidacion().equalsIgnoreCase(estado)).collect(Collectors.toList());
    }

}
