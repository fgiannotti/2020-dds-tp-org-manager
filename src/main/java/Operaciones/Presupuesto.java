package Operaciones;

import Items.Item;
import Organizaciones.Categoria;

import java.util.List;

public class Presupuesto {
    private List<Item> items;
    private Integer cantidad;
    private Float precio;
    private Float total;
    private Comprobante documento;
    private Proveedor proveedor;
    private List<Categoria> categorias;

    public Presupuesto(List<Item> items, Integer cantidad, Float precio, Float total, Comprobante documento, Proveedor proveedor) {
        this.items = items;
        this.cantidad = cantidad;
        this.precio = precio;
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

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
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
