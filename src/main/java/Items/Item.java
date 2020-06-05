package Items;

import java.util.List;

public class Item {
    private Float precioTotal;
    private String descripcion;
    private String nombre;
    private List<Articulo> articulos;

    public Item(Float precioTotal, String descripcion, String nombre, List<Articulo> articulos){
        this.precioTotal = precioTotal;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.articulos = articulos;
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
