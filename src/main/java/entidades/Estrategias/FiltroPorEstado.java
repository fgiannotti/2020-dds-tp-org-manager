package entidades.Estrategias;

import entidades.BandejaDeEntrada.Resultado;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorEstado implements Filtro{
    private Boolean estado;
    public FiltroPorEstado(Boolean estadoLectura){
        this.estado = estadoLectura;
    }
    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return (List<Resultado>) resultados.stream().filter(resultado -> resultado.getFueLeido() == estado).collect(Collectors.toList());
    }

}
