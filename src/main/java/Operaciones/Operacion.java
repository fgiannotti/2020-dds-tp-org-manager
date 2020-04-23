package Operaciones;

import Items.Item;
import MedioDePago.MedioDePago;

import java.util.Date;
import java.util.List;

public class Operacion {
    private Proveedor proveedor;
    private MedioDePago medioDePago;
    private Date fechaOperacion;
    private String tipoDocumento;
    private Comprobante comprobante;
    private Float valorTotal;
    private List<Item> items;
}
