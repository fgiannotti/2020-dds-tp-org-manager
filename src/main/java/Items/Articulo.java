package Items;

import Operaciones.Proveedor;
import Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="articulos")
public class Articulo extends EntidadPersistente {
    @Column
    private String nombre; //No esta en el diagrama
    @Column(name = "precio_total")
    private Float precioTotal;
    @Column
    private String descripcion;
    @Transient
    private Proveedor proveedor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public Articulo() {
    }

    public Articulo(String nombre, Float precioTotal, String descripcion, Proveedor proveedor) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull(precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proovedor no puede ser nulo");
    }

    public Articulo(String nombre, int precioTotal, String descripcion, Proveedor proveedor) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull((float) precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proovedor no puede ser nulo");
    }

    public String getNombre() {
        return nombre;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public boolean estoyEn(List<Articulo> articulos) {
        return articulos.stream().anyMatch(articulo ->
                articulo.getNombre().equalsIgnoreCase(this.nombre) && articulo.getPrecioTotal().equals(this.precioTotal));
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}