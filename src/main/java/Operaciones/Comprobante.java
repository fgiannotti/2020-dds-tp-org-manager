package Operaciones;

import Items.Item;
import Organizaciones.Organizacion;

import java.util.List;
import java.util.Objects;

public class Comprobante {
    private String numero_comprobante;
    private Organizacion organizacion;
    private List<Item> items;

    public Comprobante(String numero_comprobante, Organizacion organizacion) {
       this.numero_comprobante = Objects.requireNonNull(numero_comprobante, "El numero de comprobante no puede ser nulo");
       this.organizacion = Objects.requireNonNull(organizacion, "La organizacion no puede ser nula");
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



