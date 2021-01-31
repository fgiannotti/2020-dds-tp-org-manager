package entidades.Operaciones;

import java.time.LocalDate;

public interface Operacion {

    public int getId();
    public void realizarOperacion();
    public float getMontoTotal();
    public void setMontoTotal(float newMonto );
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public boolean isEgreso();
    public boolean isIngreso();
    public LocalDate getFecha();
}
