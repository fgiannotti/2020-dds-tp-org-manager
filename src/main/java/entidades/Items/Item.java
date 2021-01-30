package entidades.Items;

import entidades.Operaciones.Comprobante;
import db.Converters.EntidadPersistente;
import entidades.Operaciones.Presupuesto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item extends EntidadPersistente {
    @Column
    private String descripcion;

    @Column
    private String nombre;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "item_id")
    private List<Articulo> articulos = new ArrayList<Articulo>();

    public Item() {
    }

    public Item(String descripcion, String nombre, List<Articulo> articulos) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.articulos = articulos;
    }

    public Item(String descripcion, String nombre, List<Articulo> articulos, Presupuesto presupuestoOpcional, Comprobante comprobanteOpcional) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.articulos = articulos;
    }

    public Float getPrecioTotal() {
        double precio = articulos.stream().mapToDouble(Articulo -> Articulo.getPrecioTotal()).sum();
        return (float) precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean estoyEnEstosItemsDelPresupuesto(List<Item> items) {
        return items.stream().anyMatch(item -> this.igual(item));
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    private boolean igual(Item item) {
        List<Articulo> articulosDelItem = item.getArticulos();
        return articulosDelItem.stream().allMatch(articulo -> articulo.estoyEn(this.articulos));
    }

    @Override
    public String toString() {
        return "Item{" +
                "descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", articulos=" + articulos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getDescripcion().equals(item.getDescripcion()) &&
                getNombre().equals(item.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescripcion(), getNombre());
    }
}

