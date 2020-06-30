package Operaciones;

import Estrategias.Criterio;
import Items.Articulo;
import Items.Item;
import MedioDePago.MedioDePago;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperacionEgreso implements Operacion{
    private int numeroOperacion;
    private Proveedor proveedor;
    private Date fechaOperacion;
    private MedioDePago medioDePago;
    private String tipoDocumento;
    private Comprobante comprobante ;
    private int montoTotal;
    private String descripcion;
    private List<Item> items;
    private List<Presupuesto> presupuestosPreliminares;
    private Articulo articulo;
    private Integer cantidadMinimaDePresupuestos;
    private Criterio criterio;

    public OperacionEgreso(int montoTotal, String descripcion, Proveedor proveedor, MedioDePago medioDePago, Date fechaOperacion, String tipoDocumento, Comprobante comprobante, List<Item> items, Integer cantidadMinimaDePresupuestos,Criterio criterio){
        this.numeroOperacion = getNuevoNumeroOperacion();
        this.montoTotal = Objects.requireNonNull(montoTotal, "El monto total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proveedor no puede ser nulo");
        this.medioDePago = Objects.requireNonNull(medioDePago, "El medio de pago no puede ser nulo");
        this.fechaOperacion = Objects.requireNonNull(fechaOperacion, "La fecha de operacion no puede ser nula");
        this.tipoDocumento = Objects.requireNonNull(tipoDocumento, "El tipo de documento no puede ser nulo");
        this.comprobante = comprobante;
        this.items = Objects.requireNonNull(items, "Los items no pueden ser nulos");
        this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
        this.criterio = criterio;
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

    public Comprobante getComprobante() {
        return comprobante;
    }


    public List<Presupuesto> getPresupuestosPreliminares() {
        return presupuestosPreliminares;
    }

    public void setPresupuestosPreliminares(List<Presupuesto> presupuestosPreliminares) {
        this.presupuestosPreliminares = presupuestosPreliminares;
    }

    public Integer getCantidadMinimaDePresupuestos() {
        return this.cantidadMinimaDePresupuestos;
    }

    public Criterio getCriterio() {
        return this.criterio;
    }

    public Boolean presupuestoMenorValor(Presupuesto presupuesto) {
        return !this.getPresupuestosPreliminares().stream()
                .anyMatch(presupuesto1 -> presupuesto1.getTotal() < presupuesto.getTotal());
    }

}
