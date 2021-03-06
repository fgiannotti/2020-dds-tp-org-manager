package entidades.Items;

import entidades.Operaciones.Proveedor;
import db.Converters.EntidadPersistente;

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

    public Articulo() {
    }

    public Articulo(String nombre, Float precioTotal, String descripcion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull(precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public Articulo(String nombre, int precioTotal, String descripcion, Proveedor proveedor) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull((float) precioTotal, "El precio total no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public Articulo(String nombre, Float precioTotal) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.precioTotal = Objects.requireNonNull(precioTotal, "El precio total no puede ser nulo");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Articulo)) return false;
        Articulo articulo = (Articulo) o;
        return getNombre().equals(articulo.getNombre()) &&
                getPrecioTotal().equals(articulo.getPrecioTotal()) &&
                Objects.equals(getDescripcion(), articulo.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getPrecioTotal(), getDescripcion());
    }

}