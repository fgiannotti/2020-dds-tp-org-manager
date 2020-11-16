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
    private int montoTotal;
    @Column
    private String descripcion;
    @OneToMany(mappedBy = "ingreso", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<OperacionEgreso> operacionEgresos = new ArrayList<OperacionEgreso>();
    @Column(name = "fecha_operacion", columnDefinition = "DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaOperacion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    public OperacionIngreso(int montoTotal, String descripcion){
        this.montoTotal = Objects.requireNonNull(montoTotal, "El monto total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public OperacionIngreso(int montoTotal, String descripcion,LocalDate fechaOperacion, Organizacion organizacion){
        this.montoTotal = Objects.requireNonNull(montoTotal, "El monto total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.fechaOperacion = fechaOperacion;
        this.organizacion = organizacion;
    }

    public OperacionIngreso(){
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

    @Override
    public boolean isEgreso() {
        return false;
    }

    @Override
    public boolean isIngreso() {
        return true;
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


}
