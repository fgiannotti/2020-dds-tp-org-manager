package Operaciones;

import Items.Item;
import converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comprobantes")
public class Comprobante extends EntidadPersistente {

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



