package Items;

import java.util.List;
import java.util.Objects;

public class Item {
    private Float precioTotal;
    private String descripcion;
    private String nombre;
    private List<Articulo> articulos;

    public Item(Float precioTotal, String descripcion, String nombre, List<Articulo> articulos){
        this.precioTotal = Objects.requireNonNull(precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.articulos = Objects.requireNonNull(articulos, "Los articulos no pueden ser nulos");
    }

    public Float getPrecioTotal() {
        return precioTotal;
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
                "precioTotal=" + precioTotal +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", articulos=" + articulos +
                '}';
    }
}
