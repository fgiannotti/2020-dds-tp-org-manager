package Items;

import java.util.List;

public class Item {
    private Float precioTotal;
    private String descripcion;
    private String nombre;
    private List<Articulo> articulos;

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
