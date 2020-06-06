package Items;

import java.util.List;
import java.util.Objects;

public class Item {
    private String descripcion;
    private String nombre;
    private List<Articulo> articulos;

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
}
