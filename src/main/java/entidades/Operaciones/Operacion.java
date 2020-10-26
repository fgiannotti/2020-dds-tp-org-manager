package entidades.Operaciones;

import java.time.LocalDate;

public interface Operacion {

    public int getId();
    public void realizarOperacion();
    public int getMontoTotal();
    public void setMontoTotal( int newMonto );
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public boolean isEgreso();
    public boolean isIngreso();
    public LocalDate getFecha();
}
