package entidades.Estrategias;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;

public interface Validador {
    public Boolean validar(OperacionEgreso unEgreso);
    public Boolean cargaCorrecta(OperacionEgreso unEgreso);
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public void guardarResultados(OperacionEgreso unEgreso,Boolean carga, Boolean detalle, Boolean criterio ) ;
}
