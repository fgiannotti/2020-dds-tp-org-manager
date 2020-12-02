package entidades.Estrategias;

import db.Converters.EntidadPersistente;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="filtros")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Filtro extends EntidadPersistente {
    @ManyToOne(cascade = {CascadeType.ALL})
    public BandejaDeEntrada bandeja;

    public Filtro(BandejaDeEntrada bandeja){ this.bandeja = bandeja;}

    public Filtro(){}
    public List<Resultado> filtrar(List<Resultado> resultados){return new ArrayList<Resultado>();}
}
