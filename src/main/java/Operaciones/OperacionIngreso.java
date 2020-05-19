package Operaciones;

public class OperacionIngreso implements Operacion {
    private int montoTotal;
    private String descripcion;

    public int getMontoTotal(){
        return montoTotal;
    }

    public void setMontoTotal( int newMonto ){
        montoTotal = newMonto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void realizarOperacion(){

    }
}
