package Operaciones;

import java.util.Date;

public interface Operacion {

    public int getId();
    public void realizarOperacion();
    public int getMontoTotal();
    public void setMontoTotal( int newMonto );
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public boolean isEgreso();
    public boolean isIngreso();
    public Date getFecha();
}
