package entidades.Estrategias;

import entidades.BandejaDeEntrada.Resultado;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorFecha implements Filtro{
    private  LocalDate fecha;

    public FiltroPorFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return resultados.stream().filter(resultado -> resultado.getFechaValidacion().isBefore(this.fecha)).collect(Collectors.toList());
    }

}
