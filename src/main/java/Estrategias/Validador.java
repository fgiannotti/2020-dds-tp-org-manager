package Estrategias;

import Operaciones.OperacionEgreso;
import Operaciones.Presupuesto;

public interface Validador {
    public Boolean validar(OperacionEgreso unEgreso);
    public Boolean cargaCorrecta(OperacionEgreso unEgreso);
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto);
    public Void guardarResultados();
}
