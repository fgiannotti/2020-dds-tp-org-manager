package entidades.Estrategias;

import db.Converters.LocalDateAttributeConverter;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class FiltroPorFecha extends Filtro{
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDateTime fecha;

    public FiltroPorFecha(LocalDateTime fecha, BandejaDeEntrada bandejaDeEntrada){
        super(bandejaDeEntrada);
        this.fecha = fecha;
    }
    public FiltroPorFecha(){}

    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return resultados.stream().filter(resultado -> resultado.getFechaValidacion().isBefore(this.fecha)).collect(Collectors.toList());
    }

}
