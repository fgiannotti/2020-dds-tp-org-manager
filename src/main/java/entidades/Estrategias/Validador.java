package entidades.Estrategias;

import entidades.BandejaDeEntrada.Resultado;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;
import java.util.List;

public interface Validador {
    public List<Object> validar(OperacionEgreso unEgreso);
    public Boolean cargaCorrecta(OperacionEgreso unEgreso);
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public void guardarResultados(OperacionEgreso unEgreso, Boolean carga, Boolean detalle, Boolean criterio, String descripcion ) ;
}
