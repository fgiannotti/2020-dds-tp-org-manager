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

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public void getFecha(){
        return this.fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Proveedor getProveedor() {
        retur this.proveedor.toString();
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }


    public void adjuntarDocumento(Comprobante comprobante, int numero_operacion) {
        this.comprobante = comprobante;
        this.comprobante.setNumero_comprobante(numero_operacion);
        this.comprobante.setOrganizacion();
        this.comprobante.setItems(this.items);                  //  Al adjuntar el comprobante al
    }                                                           //  documento ambos deben tener los mismos items

    public Comprobante getDocumento() {
        return this.comprobante.toString();
    }

    public addItem(Item item){
        this.items.add(item)
    }

    public removeItem(Item item){
        this.items.remove(item):
    }
    
    public verItems(){
        for (Item target: item) {
            System.out.println(item.toString());
        }
    }

    public void realizarOperacion(){

    }

    public void registrarEgreso(int numero_operacion, MedioDePago medio_pago){

    }
}
