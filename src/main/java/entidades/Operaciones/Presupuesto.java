package entidades.Operaciones;

import entidades.Items.Item;
import entidades.Organizaciones.Categoria;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
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

    @Override
    public String toString() {
        return "Presupuesto{" +
                "items=" + items +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", documento=" + documento +
                ", proveedor=" + proveedor +
                ", categorias=" + categorias +
                '}';
    }

    @Transient
    private Comprobante documento;
    @Transient
    private Proveedor proveedor;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "presupuesto_x_categoria",
            joinColumns = { @JoinColumn(name = "presupuesto_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "categoria_id", referencedColumnName = "id") })
    private List<Categoria> categorias = new ArrayList<Categoria>();

    public Presupuesto(){

    }

    public Presupuesto(List<Item> items, Integer cantidad, Float total, Comprobante documento, Proveedor proveedor,List<Categoria> categoriasOpcionales) {
        this.items = items;
        this.cantidad = cantidad;
        this.total = total;
        this.documento = documento;
        this.proveedor = proveedor;
        if (categoriasOpcionales != null){
            this.categorias = categoriasOpcionales;
        }
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

}
