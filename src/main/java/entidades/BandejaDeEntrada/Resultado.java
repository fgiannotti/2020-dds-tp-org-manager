package entidades.BandejaDeEntrada;

import db.Converters.EntidadPersistente;
import entidades.Operaciones.Proveedor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "resultados")
public class Resultado extends EntidadPersistente {
    @Column(name = "numero_operacion")
    private int numeroOperacion;
    @Column(name = "corresponde_carga_correcta")
    private Boolean correspondeCargaCorrecta;
    @Column(name = "corresponde_detalle")
    private Boolean correspondeDetalle;
    @Column(name = "corresponde_criterio")
    private Boolean correspondeCriterio;
    @Column(name = "fue_leido")
    private Boolean fueLeido;
    @Column(name = "fecha_validacion")
    private LocalDateTime fechaValidacion;
    @Column
    private String descripcion;

    @Transient
    private Proveedor proveedorElegido;

    public Resultado(int numeroOperacion, Proveedor proveedorElegido, Boolean correspondeCargaCorrecta, Boolean correspondeDetalle, Boolean correspondeCriterio, Boolean fueLeido, LocalDateTime fechaValidacion,String descripcion) {
        this.numeroOperacion = numeroOperacion;
        this.proveedorElegido = proveedorElegido;
        this.correspondeCargaCorrecta = correspondeCargaCorrecta;
        this.correspondeDetalle = correspondeDetalle;
        this.correspondeCriterio = correspondeCriterio;
        this.fueLeido = fueLeido;
        this.fechaValidacion = fechaValidacion;
        this.descripcion = descripcion;
    }

    public Resultado(){}

    public Boolean getFueLeido(){ return this.fueLeido; }

    @Override
    public String toString() {
        return "{" +
                "numeroOperacion=" + numeroOperacion +
                ", correspondeCargaCorrecta=" + correspondeCargaCorrecta +
                ", correspondeDetalle=" + correspondeDetalle +
                ", correspondeCriterio=" + correspondeCriterio +
                ", fueLeido=" + fueLeido +
                ", fechaValidacion=" + fechaValidacion +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    public LocalDateTime getFechaValidacion() { return fechaValidacion; }

    public int getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(int numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public Boolean getCorrespondeCargaCorrecta() {
        return correspondeCargaCorrecta;
    }

    public void setCorrespondeCargaCorrecta(Boolean correspondeCargaCorrecta) {
        this.correspondeCargaCorrecta = correspondeCargaCorrecta;
    }

    public Boolean getCorrespondeDetalle() {
        return correspondeDetalle;
    }

    public void setCorrespondeDetalle(Boolean correspondeDetalle) {
        this.correspondeDetalle = correspondeDetalle;
    }

    public Boolean getCorrespondeCriterio() {
        return correspondeCriterio;
    }

    public void setCorrespondeCriterio(Boolean correspondeCriterio) {
        this.correspondeCriterio = correspondeCriterio;
    }

    public void setFueLeido(Boolean fueLeido) {
        this.fueLeido = fueLeido;
    }

    public void setFechaValidacion(LocalDateTime fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proveedor getProveedorElegido() {
        return proveedorElegido;
    }

    public void setProveedorElegido(Proveedor proveedorElegido) {
        this.proveedorElegido = proveedorElegido;
    }
}
