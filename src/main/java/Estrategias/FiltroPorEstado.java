package Estrategias;

import BandejaDeEntrada.Resultado;

import java.util.List;

public class FiltroPorEstado implements Filtro{

    @Override
    public List<Resultado> filtrar(List<Resultado> resultados){
        return (List<Resultado>) resultados.stream().filter(resultado -> resultado.getFueLeido());
    }

}
