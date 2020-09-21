package Items;

import Operaciones.Comprobante;
import converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="items")
public class Item extends EntidadPersistente {
    @Column
    private String descripcion;
    @Column
    private String nombre;
    @OneToMany(mappedBy = "item", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Articulo> articulos = new ArrayList<Articulo>();
    @ManyToOne
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private Comprobante comprobante;

    public Item(){
    }

    public Item(String descripcion, String nombre, List<Articulo> articulos){
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.articulos = Objects.requireNonNull(articulos, "Los articulos no pueden ser nulos");
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

    @Override
    public String toString() {
        return "Item{" +
                "precioTotal=" + this.getPrecioTotal() +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", articulos=" + articulos +
                '}';
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
}
