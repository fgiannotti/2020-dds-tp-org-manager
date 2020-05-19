package Operaciones;

import Items.Item;
import MedioDePago.MedioDePago;

import java.util.Date;
import java.util.List;

public class OperacionEgreso implements Operacion{
    private int montoTotal;
    private String descripcion;
    private Proveedor proveedor;
    private MedioDePago medioDePago;
    private Date fechaOperacion;
    private String tipoDocumento;
    private Comprobante comprobante;
    private List<Item> items;

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

    public void registrarEgreso(int numero_operacion, MedioDePago medio_pago){

    }

    public void adjuntarDocumento(Documento documento, int numero_operacion){

    }

}
