package Operaciones;

public class Presupuesto {

    private String producto;
    private Integer cantidad;
    private Float precio;
    private Float total;
    private Comprobante documento;
    private Proveedor proveedor;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Comprobante getDocumento() {
        return documento;
    }

    public void setDocumento(Comprobante documento) {
        this.documento = documento;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

}
