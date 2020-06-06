package Operaciones;

import Items.Item;
import Organizaciones.Organizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comprobante {
    private int numero_comprobante;
    private List<Item> items;

    public Comprobante(List<Item> items) {
        this.numero_comprobante = this.hashCode();
        this.items = new ArrayList<Item>() {};
        this.items.addAll(items);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}



