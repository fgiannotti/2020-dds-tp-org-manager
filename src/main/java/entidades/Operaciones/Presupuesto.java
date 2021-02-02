package entidades.Operaciones;

import entidades.Items.Item;
import entidades.Organizaciones.Categoria;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="presupuestos")
public class Presupuesto extends EntidadPersistente {
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "presupuesto_id")
    private List<Item> items = new ArrayList<Item>();
    @Column
    private Integer cantidad;
    @Column
    private Float total;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL})
    @JoinTable(
            name = "presupuesto_x_categoria",
            joinColumns = { @JoinColumn(name = "presupuesto_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "categoria_id", referencedColumnName = "id") })
    private List<Categoria> categorias = new ArrayList<Categoria>();

    public Presupuesto(){ }


    public Presupuesto(List<Item> items, Integer cantidad, Float total, Proveedor proveedor,List<Categoria> categoriasOpcionales) {
        this.items = items;
        this.cantidad = cantidad;
        this.total = total;
        this.proveedor = proveedor;
        if (categoriasOpcionales != null){
            this.categorias = categoriasOpcionales;
        }else{
            this.categorias = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Presupuesto{" +
                "items=" + items +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", proveedor=" + proveedor +
                ", categorias=" + categorias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Presupuesto)) return false;
        Presupuesto that = (Presupuesto) o;
        return Objects.equals(getItems(), that.getItems()) &&
                getCantidad().equals(that.getCantidad()) &&
                getTotal().equals(that.getTotal()) &&
                getProveedor().equals(that.getProveedor()) &&
                Objects.equals(getCategorias(), that.getCategorias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCantidad(), getTotal(), getProveedor());
    }
}
