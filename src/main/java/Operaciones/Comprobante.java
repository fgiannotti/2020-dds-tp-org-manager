package Operaciones;

import Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Comprobante {
    public int getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(int numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    private int numeroComprobante;
    private List<Item> items;

    public Comprobante(List<Item> items) {
        this.numeroComprobante = this.hashCode();
        this.items = new ArrayList<Item>() {};
        this.items.addAll(items);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}



