package Operaciones;

import Items.Item;
import Organizaciones.Organizacion;

import java.util.List;

public class Comprobante {
    private String numero_comprobante;
    private Organizacion organizacion;
    private List<Item> items;

    public Comprobante(String numero_comprobante, Organizacion organizacion) {
        this.numero_comprobante = numero_comprobante;
        this.organizacion = organizacion;
    }

    public void setNumero_comprobante(String numero_comprobante) {
        this.numero_comprobante = numero_comprobante;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

}


