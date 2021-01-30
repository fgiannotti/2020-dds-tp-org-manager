package entidades.Operaciones;

import entidades.Items.Item;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jdk.nashorn.internal.objects.NativeArray.length;

@Entity
@Table(name = "comprobantes")
public class Comprobante extends EntidadPersistente {

    @Column(name = "numero_comprobante")
    private int numeroComprobante;

    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "comprobante_id")
    private List<Item> items = new ArrayList<Item>();

    public Comprobante(int numeroComprobante, String tipoComprobante, List<Item> items) {
        this.numeroComprobante = numeroComprobante;
        this.tipoComprobante = tipoComprobante;
        this.items = items;
    }

    public Comprobante(List<Item> items) {
        this.numeroComprobante = this.hashCode();
        this.items = items;
    }

    public Comprobante() {
        this.numeroComprobante = this.hashCode();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(int numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "numeroComprobante=" + numeroComprobante +
                ", items=" + items.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comprobante)) return false;
        Comprobante that = (Comprobante) o;
        return getNumeroComprobante() == that.getNumeroComprobante() &&
                tipoComprobante.equals(that.tipoComprobante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroComprobante(), tipoComprobante);
    }
}



