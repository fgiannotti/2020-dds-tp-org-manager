package Estrategias;

public interface Validador {
    public Boolean cargaCorrecta();
    public Boolean compararDetalles();
    public Boolean elegirPorCriterio();
    public Void guardarResultados();
}
