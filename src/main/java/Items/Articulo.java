package Items;

import Operaciones.Proveedor;

import java.util.Objects;

public class Articulo {
    private String nombre; //No esta en el diagrama
    private Float precioTotal;
    private String descripcion;
    private Proveedor proveedor;

    public Articulo (String nombre, Float precioTotal, String descripcion, Proveedor proveedor){
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull(precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proovedor no puede ser nulo");
    }

    public Articulo (String nombre, int precioTotal, String descripcion, Proveedor proveedor){
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull((float)precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.proveedor = Objects.requireNonNull(proveedor, "El proovedor no puede ser nulo");
    }

    public String getNombre() {
        return nombre;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }
}
