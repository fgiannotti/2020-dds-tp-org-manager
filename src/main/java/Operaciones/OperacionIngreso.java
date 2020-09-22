package Operaciones;

import Organizaciones.Organizacion;
import converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="egresos")
public class OperacionIngreso  extends EntidadPersistente {
    @Column(name="monto_total")
    private int montoTotal;
    @Column
    private String descripcion;
    @OneToMany(mappedBy = "ingreso", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<OperacionEgreso> operacionEgresos = new ArrayList<OperacionEgreso>();

    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

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
