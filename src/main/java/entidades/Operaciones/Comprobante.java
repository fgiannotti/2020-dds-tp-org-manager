package entidades.Operaciones;

import entidades.Items.Item;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeArray.length;

@Entity
@Table(name="comprobantes")
public class Comprobante extends EntidadPersistente {
    @Override
    public String toString() {
        return "Comprobante{" +
                "numeroComprobante=" + numeroComprobante +
                ", items=" + length(items) +
                '}';
    }

    @Column(name="numero_comprobante")
    private int numeroComprobante;

    @OneToMany(mappedBy = "comprobante", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<Item>();

    public Comprobante(List<Item> items) {
        this.numeroComprobante = this.hashCode();
        this.items = new ArrayList<Item>() {};
        this.items.addAll(items);
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

}



