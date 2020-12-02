package repositorios.Builders;

import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Organizaciones.Organizacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class OperacionIngresoBuilder {
    private int montoTotal;
    private String descripcion;
    private List<OperacionEgreso> operacionEgresos = new ArrayList<OperacionEgreso>();
    private LocalDate fechaOperacion;
    private Organizacion organizacion;

    private final OperacionIngreso operacionIngreso;
    public OperacionIngreso getOperacionIngreso() {return operacionIngreso;}

    //Constructor
    public OperacionIngresoBuilder(){ this.operacionIngreso = new OperacionIngreso();}

    public OperacionIngresoBuilder agregarMontoTotal(int montoTotal){
        this.operacionIngreso.setMontoTotal(montoTotal);
        return this;
    }

    public OperacionIngresoBuilder agregarDescripcion(String descripcion){
        this.operacionIngreso.setDescripcion(descripcion);
        return this;
    }

    public OperacionIngresoBuilder agregarEgresos(List<OperacionEgreso> nuevosEgresos){
        List<OperacionEgreso> egresos = this.operacionIngreso.getOperacionEgresos();
        egresos.addAll(nuevosEgresos);
        this.operacionIngreso.setOperacionEgresos(egresos);

        return this;
    }

    public OperacionIngresoBuilder agregarFechaOperacion(LocalDate fecha){
        this.operacionIngreso.setFechaOperacion(fecha);
        return this;
    }

    public OperacionIngresoBuilder agregarOrganizacion(Organizacion organizacion){
        this.operacionIngreso.setOrganizacion(organizacion);
        return this;
    }
    public OperacionIngreso build() throws Exception {

        /*if ( isNull(this.operacionIngreso.getOperacionEgresos()) || this.operacionIngreso.getOperacionEgresos().size()==0) {
            throw new Exception("No se asignaron egresos al ingreso");
        }*/

        if (isNull(this.operacionIngreso.getFechaOperacion())) {
            throw new Exception("No se un asigno fecha");
        }

        if (this.operacionIngreso.getMontoTotal() == 0) {
            throw new Exception("No se asigno monto total");
        }

        if (isNull(this.operacionIngreso.getOrganizacion())) {
            throw new Exception("No se asigno organizacion");
        }

        return this.operacionIngreso;

    }
}
