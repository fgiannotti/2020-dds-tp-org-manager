package Operaciones;

import Items.Item;
import MedioDePago.MedioDePago;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperacionEgreso implements Operacion{
    private int montoTotal;
    private String descripcion;
    private Proveedor proveedor;
    private MedioDePago medioDePago;
    private Date fechaOperacion;
    private String tipoDocumento;
    private Comprobante comprobante;
    private List<Item> items;

    public OperacionEgreso(int montoTotal, String descripcion, Proveedor proveedor, MedioDePago medioDePago, Date fechaOperacion, String tipoDocumento, Comprobante comprobante, List<Item> items){
        this.montoTotal = Objects.requireNonNull(montoTotal, "El monto total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proveedor no puede ser nulo");
        this.medioDePago = Objects.requireNonNull(medioDePago, "El medio de pago no puede ser nulo");
        this.fechaOperacion = Objects.requireNonNull(fechaOperacion, "La fecha de operacion no puede ser nula");
        this.tipoDocumento = Objects.requireNonNull(tipoDocumento, "El tipo de documento no puede ser nulo");
        this.comprobante = Objects.requireNonNull(comprobante, "El comprobante no puede ser nulo");
        this.items = Objects.requireNonNull(items, "Los items no pueden ser nulos");

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

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }


    public void adjuntarDocumento(Comprobante comprobante, int numero_operacion) {
        this.comprobante = comprobante;
        this.comprobante.setNumero_comprobante(String.valueOf(numero_operacion));
        //this.comprobante.setOrganizacion();
        this.comprobante.setItems(this.items);                  //  Al adjuntar el comprobante al
    }                                                           //  documento ambos deben tener los mismos items

    public Comprobante getDocumento() {
        return this.comprobante;
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
