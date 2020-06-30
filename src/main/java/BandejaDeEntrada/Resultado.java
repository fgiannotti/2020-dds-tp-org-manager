package BandejaDeEntrada;

import Operaciones.Proveedor;

import java.time.LocalDate;

public class Resultado {
    private int numeroOperacion;
    private Proveedor proveedor;
    private Boolean correspondeCargaCorrecta;
    private Boolean correspondeDetalle;
    private Boolean correspondeCriterio;
    private String estadoValidacion;
    private LocalDate fechaValidacion;

    public void mostrarResultado() {
        System.out.println("Numero Operacion: "+numeroOperacion+
                " Carga Correcta: "+ String.valueOf(correspondeCargaCorrecta)+
                " Corresponde Detalle: "+ String.valueOf(correspondeDetalle)+
                " Corresponde Criterio: "+ String.valueOf(correspondeCriterio)+
                "Fecha de Validacion: "+fechaValidacion.toString());
    }

    public String getEstadoValidacion(){ return this.estadoValidacion; }

    public LocalDate getFechaValidacion() { return fechaValidacion; }
}
