package Operaciones;

public interface Operacion {
    public void realizarOperacion();
    public int getMontoTotal();
    public void setMontoTotal( int newMonto );
    public String getDescripcion();
    public void setDescripcion(String descripcion);
}
