package entidades.Operaciones;

import db.Converters.EntidadPersistente;
import db.Converters.LocalDateAttributeConverter;
import entidades.Organizaciones.Organizacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ingresos")
public class OperacionIngreso extends EntidadPersistente implements Operacion {
    @Column(name="monto_total")
    private float montoTotal;
    @Column
    private String descripcion;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ingreso_id")
    private List<OperacionEgreso> operacionesEgreso = new ArrayList<OperacionEgreso>();

    @Column(name = "fecha_operacion", columnDefinition = "DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaOperacion;

    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    public OperacionIngreso(float montoTotal, String descripcion){
        this.montoTotal = montoTotal;
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public OperacionIngreso(float montoTotal, String descripcion,LocalDate fechaOperacion, Organizacion organizacion){
        this.montoTotal = montoTotal;
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.fechaOperacion = fechaOperacion;
        this.organizacion = organizacion;
    }

    public OperacionIngreso(){
    }

    public float getMontoTotal(){
        return montoTotal;
    }

    public void setMontoTotal(float newMonto ){
        montoTotal = newMonto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean isEgreso() {
        return false;
    }

    @Override
    public boolean isIngreso() {
        return true;
    }

    public void realizarOperacion(){}

    public List<OperacionEgreso> getOperacionesEgreso() {
        return operacionesEgreso;
    }

    public void setOperacionesEgreso(List<OperacionEgreso> operacionEgresos) {
        this.operacionesEgreso = operacionEgresos;
    }

    public void agregarOperacionEgreso(OperacionEgreso unaOperacionEgresos) {
        this.operacionesEgreso.add(unaOperacionEgresos);
    }

    @Override
    public String toString() {
        return "OperacionIngreso{" +
                "montoTotal=" + montoTotal +
                ", descripcion='" + descripcion + '\'' +
                ", operacionesEgreso=" + operacionesEgreso.size() +
                ", fechaOperacion=" + fechaOperacion +
                ", organizacion=" + organizacion +
                '}';
    }

    public LocalDate getFecha() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperacionIngreso)) return false;
        OperacionIngreso that = (OperacionIngreso) o;
        return Float.compare(that.getMontoTotal(), getMontoTotal()) == 0 &&
                getDescripcion().equals(that.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMontoTotal(), getDescripcion());
    }
}
