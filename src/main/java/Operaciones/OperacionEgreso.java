package Operaciones;

import Estrategias.Criterio;
import Items.Articulo;
import Items.Item;
import MedioDePago.MedioDePago;
import Organizaciones.Categoria;
import Organizaciones.Organizacion;
import Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="egresos")
public class OperacionEgreso extends EntidadPersistente implements Operacion {
    @Column
    private int numeroOperacion;

    @OneToMany(mappedBy = "egreso", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Proveedor> proveedores;

    @Column(name = "fecha_operacion", columnDefinition = "DATE")
    private Date fechaOperacion;

    @OneToOne(cascade = {CascadeType.ALL})
    private MedioDePago medioDePago;

    @Column
    private String tipoDocumento;

    @OneToOne(cascade = {CascadeType.ALL})
    private Comprobante comprobante;

    @Column
    private int montoTotal;

    @Column
    private String descripcion;

    @Transient
    private List<Item> items = new ArrayList<Item>();

    @Transient
    private List<Presupuesto> presupuestosPreliminares = new ArrayList<Presupuesto>();

    @Transient
    private Articulo articulo;

    @Column
    private Integer cantidadMinimaDePresupuestos;

    @ManyToOne
    @JoinColumn(name = "ingreso_id", referencedColumnName = "id")
    private OperacionIngreso ingreso;

    @Enumerated(EnumType.STRING)
    @Column
    private Criterio criterio;

    @Transient
    private List<Categoria> categorias = new ArrayList<Categoria>();

    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    public OperacionEgreso(){
    }

    public OperacionEgreso(int montoTotal, String descripcion, List<Proveedor> proveedores, MedioDePago medioDePago, Date fechaOperacion, String tipoDocumento, Comprobante comprobante, List<Item> items, Integer cantidadMinimaDePresupuestos,Criterio criterio){
        this.presupuestosPreliminares = new ArrayList<Presupuesto>();
        this.numeroOperacion = getNuevoNumeroOperacion();
        this.montoTotal = montoTotal;
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedores = Objects.requireNonNull(proveedores, "El proveedor no puede ser nulo");
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

    @Override
    public boolean isEgreso() {
        return true;
    }

    @Override
    public boolean isIngreso() {
        return false;
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

    public List<Proveedor> getProveedores() {
        return this.proveedores;
    }

    public MedioDePago getMedioDePago() {
        return this.medioDePago;
    }

    public void setMedioDePago(MedioDePago mp) { this.medioDePago = mp; }

    public void setProveedor(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
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
        return this.getPresupuestosPreliminares().stream()
                .noneMatch(presupuesto1 -> presupuesto1.getTotal() < presupuesto.getTotal());
    }

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    public void agregarPresupuesto(Presupuesto presupuesto){
        this.presupuestosPreliminares.add(presupuesto);
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
