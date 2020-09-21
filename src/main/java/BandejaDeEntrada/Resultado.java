package BandejaDeEntrada;

import Operaciones.Proveedor;

import java.time.LocalDate;
import java.util.List;

public class Resultado {
    private int numeroOperacion;
    private List<Proveedor> proveedores;
    private Boolean correspondeCargaCorrecta;
    private Boolean correspondeDetalle;
    private Boolean correspondeCriterio;
    private Boolean fueLeido;
    private LocalDate fechaValidacion;

    public Resultado(int numeroOperacion, List<Proveedor> proveedores, Boolean correspondeCargaCorrecta, Boolean correspondeDetalle, Boolean correspondeCriterio, Boolean fueLeido, LocalDate fechaValidacion) {
        this.numeroOperacion = numeroOperacion;
        this.proveedores = proveedores;
        this.correspondeCargaCorrecta = correspondeCargaCorrecta;
        this.correspondeDetalle = correspondeDetalle;
        this.correspondeCriterio = correspondeCriterio;
        this.fueLeido = fueLeido;
        this.fechaValidacion = fechaValidacion;
    }

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
