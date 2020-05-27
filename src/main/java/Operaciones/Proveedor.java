package Operaciones;

public class Proveedor {
    private String nombre_apellido_razon;
    private String documento;
    private String direccionPostal;

    public Proveedor(String nombre_apellido_razon, String documento, String direccionPostal) {
        this.nombre_apellido_razon = nombre_apellido_razon;
        this.documento = documento;
        this.direccionPostal = direccionPostal;
    }

    public String getNombre_apellido_razon() {
        return nombre_apellido_razon;
    }

    public void setNombre_apellido_razon(String nombre_apellido_razon) {
        this.nombre_apellido_razon = nombre_apellido_razon;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "nombre_apellido_razon='" + nombre_apellido_razon + '\'' +
                ", documento='" + documento + '\'' +
                ", direccionPostal='" + direccionPostal + '\'' +
                '}';
    }
}
