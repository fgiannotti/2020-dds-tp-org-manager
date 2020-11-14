package entidades.BandejaDeEntrada;

import db.Converters.EntidadPersistente;
import entidades.Operaciones.Proveedor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "resultados")
public class Resultado extends EntidadPersistente {
    @Column(name = "numero_operacion")
    private int numeroOperacion;
    @Transient
    private List<Proveedor> proveedores;
    @Column(name = "corresponde_carga_correcta")
    private Boolean correspondeCargaCorrecta;
    @Column(name = "corresponde_detalle")
    private Boolean correspondeDetalle;
    @Column(name = "corresponde_criterio")
    private Boolean correspondeCriterio;
    @Column(name = "fue_leido")
    private Boolean fueLeido;
    @Column(name = "fecha_validacion")
    private LocalDate fechaValidacion;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bandeja_id", referencedColumnName = "id")
    private BandejaDeEntrada bandeja;

    public Resultado(int numeroOperacion, List<Proveedor> proveedores, Boolean correspondeCargaCorrecta, Boolean correspondeDetalle, Boolean correspondeCriterio, Boolean fueLeido, LocalDate fechaValidacion,BandejaDeEntrada bandeja) {
        this.numeroOperacion = numeroOperacion;
        this.proveedores = proveedores;
        this.correspondeCargaCorrecta = correspondeCargaCorrecta;
        this.correspondeDetalle = correspondeDetalle;
        this.correspondeCriterio = correspondeCriterio;
        this.fueLeido = fueLeido;
        this.fechaValidacion = fechaValidacion;
        this.bandeja = bandeja;
    }
    public Resultado(){}
    public void mostrarResultado() {
        this.fueLeido = true;
        System.out.println("Numero Operacion: "+numeroOperacion+
                " Carga Correcta: "+ String.valueOf(correspondeCargaCorrecta)+
                " Corresponde Detalle: "+ String.valueOf(correspondeDetalle)+
                " Corresponde Criterio: "+ String.valueOf(correspondeCriterio)+
                " Fecha de Validacion: "+fechaValidacion.toString());
    }

    public Boolean getFueLeido(){ return this.fueLeido; }

    public LocalDate getFechaValidacion() { return fechaValidacion; }
}
