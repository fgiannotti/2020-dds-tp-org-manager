package Estrategias;

import BandejaDeEntrada.Resultado;

import java.util.List;

public interface Filtro {
    public List<Resultado> filtrar(List<Resultado> resultados);
}
