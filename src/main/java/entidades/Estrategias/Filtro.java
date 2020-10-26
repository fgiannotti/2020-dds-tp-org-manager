package entidades.Estrategias;

import entidades.BandejaDeEntrada.Resultado;

import java.util.List;

public interface Filtro {
    public List<Resultado> filtrar(List<Resultado> resultados);
}
