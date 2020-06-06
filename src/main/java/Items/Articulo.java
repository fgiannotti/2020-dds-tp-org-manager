package Items;

import Operaciones.Proveedor;
import jdk.nashorn.internal.runtime.PrototypeObject;

import java.util.List;

public class Articulo {
    private String nombre; //No esta en el diagrama
    private Float precioTotal;
    private String descripcion;
    private Proveedor proveedor;

    public Articulo (String nombre, Float precioTotal, String descripcion, Proveedor proveedor){
        this.nombre = nombre;
        this.precioTotal = precioTotal;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }
}
