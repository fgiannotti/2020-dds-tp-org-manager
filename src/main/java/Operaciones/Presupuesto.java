package Operaciones;

import Items.Item;
import Organizaciones.Categoria;
import converters.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="presupuestos")
public class Presupuesto extends EntidadPersistente {
    @Transient
    private List<Item> items = new ArrayList<Item>();
    @Column
    private Integer cantidad;
    @Column
    private Float total;
    @Transient
    private Comprobante documento;
    @Transient
    private Proveedor proveedor;
    @Transient
    private List<Categoria> categorias = new ArrayList<Categoria>();

    public Presupuesto(List<Item> items, Integer cantidad, Float total, Comprobante documento, Proveedor proveedor) {
        this.items = items;
        this.cantidad = cantidad;
        this.total = total;
        this.documento = documento;
        this.proveedor = proveedor;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Comprobante getDocumento() {
        return documento;
    }

    public void setDocumento(Comprobante documento) {
        this.documento = documento;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

}
