package Operaciones;

import java.util.List;
import java.util.Objects;

public class OperacionIngreso implements Operacion {
    private int montoTotal;
    private String descripcion;
    private List<OperacionEgreso> operacionEgresos;

    public OperacionIngreso(int montoTotal, String descripcion){
        this.montoTotal = Objects.requireNonNull(montoTotal, "El monto total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

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

    public void realizarOperacion(){}

    public List<OperacionEgreso> getOperacionEgresos() {
        return operacionEgresos;
    }

    public void setOperacionEgresos(List<OperacionEgreso> operacionEgresos) {
        this.operacionEgresos = operacionEgresos;
    }

    public void agregarOperacionEgresos(OperacionEgreso unaOperacionEgresos) {
        this.operacionEgresos.add(unaOperacionEgresos);
    }
}
