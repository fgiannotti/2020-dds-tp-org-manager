package entidades.Estrategias;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;
@Entity
public class FiltroPorEstado extends Filtro{
    @Column
    private Boolean estado;

    public FiltroPorEstado(){}
    public FiltroPorEstado(Boolean estadoLectura, BandejaDeEntrada bandejaDeEntrada){
        super(bandejaDeEntrada);
        this.estado = estadoLectura;
    }

    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return (List<Resultado>) resultados.stream().filter(resultado -> resultado.getFueLeido() == estado).collect(Collectors.toList());
    }

}
