package entidades.Operaciones;

import db.Converters.LocalDateAttributeConverter;
import entidades.Estrategias.Criterio;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.MedioDePago;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="egresos")
public class OperacionEgreso extends EntidadPersistente implements Operacion {
    @Column
    private int numeroOperacion;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Proveedor> proveedores = new ArrayList<>();

    @Column(name = "fecha_operacion", columnDefinition = "DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaOperacion;

    @OneToOne(cascade=CascadeType.ALL)
    private MedioDePago medioDePago;

    @Column
    private String tipoDocumento;

    @OneToOne(cascade = {CascadeType.ALL})
    private Comprobante comprobante;

    @Column
    private float montoTotal;

    @Column
    private String descripcion;

    @Transient
    private List<Item> items = new ArrayList<Item>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Presupuesto> presupuestosPreliminares = new ArrayList<Presupuesto>();

    @Transient
    private Articulo articulo;

    @Column
    private Integer cantidadMinimaDePresupuestos;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ingreso_id", referencedColumnName = "id")
    private OperacionIngreso ingreso;

    @Enumerated(EnumType.STRING)
    @Column
    private Criterio criterio;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "egresos_x_categorias", joinColumns = @JoinColumn(name = "egreso_id",referencedColumnName = "id",unique = false),
            inverseJoinColumns = @JoinColumn(name = "categoria_id",referencedColumnName = "id",unique = false))
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    private Organizacion organizacion;

    public OperacionEgreso(){ }

    public OperacionEgreso(int montoTotal, String descripcion, List<Proveedor> proveedores, MedioDePago medioDePago, LocalDate fechaOperacion, String tipoDocumento, Comprobante comprobante, List<Item> items, Integer cantidadMinimaDePresupuestos, Criterio criterio, List<Presupuesto> presupuestosPreliminaresOpcionales){
        this.presupuestosPreliminares = presupuestosPreliminaresOpcionales != null ? presupuestosPreliminaresOpcionales : this.presupuestosPreliminares;
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
    public OperacionIngreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(OperacionIngreso ingreso) {
        this.ingreso = ingreso;
    }

    public OperacionEgreso(float montoTotal, String descripcion, List<Proveedor> proveedores,
                           MedioDePago medioDePago, LocalDate fechaOperacion, String tipoDocumento,
                           Comprobante comprobante, List<Item> items, Integer cantidadMinimaDePresupuestos,
                           Criterio criterio,Organizacion organizacion,
                           List<Presupuesto> presupuestosPreliminaresOpcionales,List<Categoria> categoriasOpcionales){

        this.categorias = categoriasOpcionales != null ? categoriasOpcionales : this.categorias;
        this.presupuestosPreliminares = presupuestosPreliminaresOpcionales != null ? presupuestosPreliminaresOpcionales : this.presupuestosPreliminares;
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
        this.organizacion = organizacion;
    }

    @Override
    public boolean isEgreso() {
        return true;
    }

    @Override
    public boolean isIngreso() {
        return false;
    }

    public void adjuntarDocumento(Comprobante comprobante, int numero_operacion) {
        this.comprobante = comprobante;
        //this.comprobante.setOrganizacion();
        this.comprobante.setItems(this.items);                  //  Al adjuntar el comprobante al
    }                                                           //  documento ambos deben tener los mismos items

    @Override
    public String toString() {
        return "OperacionEgreso{" +
                "numeroOperacion=" + numeroOperacion +
                ", proveedores=" + proveedores +
                ", fechaOperacion=" + fechaOperacion +
                ", medioDePago=" + medioDePago +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", comprobante=" + comprobante +
                ", montoTotal=" + montoTotal +
                ", descripcion='" + descripcion + '\'' +
                ", items=" + items +
                ", presupuestosPreliminares=" + presupuestosPreliminares +
                ", articulo=" + articulo +
                ", cantidadMinimaDePresupuestos=" + cantidadMinimaDePresupuestos +
                ", ingreso=" + ingreso +
                ", criterio=" + criterio +
                ", categorias=" + categorias +
                ", organizacion=" + organizacion +
                '}';
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

    public void realizarOperacion(){}
    public void registrarEgreso(int numero_operacion, MedioDePago medio_pago){ }


    public Comprobante getDocumento() {
        return this.comprobante;
    }

    public int getNumeroOperacion() {
        return numeroOperacion;
    }

    public Comprobante getComprobante() { return comprobante; }

    public List<Presupuesto> getPresupuestosPreliminares() { return presupuestosPreliminares; }

    public void setPresupuestosPreliminares(List<Presupuesto> presupuestosPreliminares) { this.presupuestosPreliminares = presupuestosPreliminares; }

    public Integer getCantidadMinimaDePresupuestos() {
        return this.cantidadMinimaDePresupuestos;
    }

    public Criterio getCriterio() {
        return this.criterio;
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

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setNumeroOperacion(int numeroOperacion) { this.numeroOperacion = numeroOperacion; }

    public void setProveedores(List<Proveedor> proveedores) { this.proveedores = proveedores; }

    public LocalDate getFechaOperacion() { return fechaOperacion; }

    public void setItems(List<Item> items) { this.items = items; }

    public void setCantidadMinimaDePresupuestos(Integer cantidadMinimaDePresupuestos) { this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos; }

    public void setCriterio(Criterio criterio) { this.criterio = criterio; }

    public List<Categoria> getCategorias() { return categorias; }

    public void setCategorias(List<Categoria> categorias) {this.categorias = categorias; }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public LocalDate getFecha(){
        return this.fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public List<Proveedor> getProveedores() {
        return this.proveedores;
    }

    public MedioDePago getMedioDePago() {
        return this.medioDePago;
    }

    public void setMedioDePago(MedioDePago mp) { this.medioDePago = mp; }

    public List<Item> getItems(){
        return this.items;
    }

    private int getNuevoNumeroOperacion() {
        return this.hashCode();
    }

    public float getMontoTotal(){
        return montoTotal;
    }

    public void setMontoTotal(float newMonto ){
        montoTotal = newMonto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
