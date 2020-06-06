package Operaciones;

import Items.Item;
import MedioDePago.MedioDePago;

import java.util.Date;
import java.util.List;

public class OperacionEgreso implements Operacion{
    private int numeroOperacion;
    private int montoTotal;
    private String descripcion;
    private Proveedor proveedor;
    private MedioDePago medioDePago;
    private Date fechaOperacion;
    private String tipoDocumento;
    private Comprobante comprobante;
    private List<Item> items;

    public OperacionEgreso(int montoTotal, String descripcion, Proveedor proveedor, MedioDePago medioDePago, Date fechaOperacion, String tipoDocumento, Comprobante comprobante, List<Item> items){
        this.numeroOperacion = getNuevoNumeroOperacion();
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.medioDePago = medioDePago;
        this.fechaOperacion = fechaOperacion;
        this.tipoDocumento = tipoDocumento;
        this.comprobante = comprobante;
        this.items = items;
    }

    private int getNuevoNumeroOperacion() {
        return this.hashCode();
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

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Date getFecha(){
        return this.fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public MedioDePago getMedioDePago() {
        return this.medioDePago;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Item> getItems(){
        return this.items;
    }

    public void adjuntarDocumento(Comprobante comprobante, int numero_operacion) {
        this.comprobante = comprobante;
        //this.comprobante.setOrganizacion();
        this.comprobante.setItems(this.items);                  //  Al adjuntar el comprobante al
    }                                                           //  documento ambos deben tener los mismos items

    public Comprobante getDocumento() {
        return this.comprobante;
    }

    public int getNumeroOperacion() {
        return numeroOperacion;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }
    
    public void verItems(){
        for (Item item: items) {
            System.out.println(item.toString());
        }
    }

    public void realizarOperacion(){

    }

    public void registrarEgreso(int numero_operacion, MedioDePago medio_pago){

    }
}
