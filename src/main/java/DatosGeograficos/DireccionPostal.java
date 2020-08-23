package DatosGeograficos;

public class DireccionPostal {

    private Direccion direccion;
    private Ciudad ciudad;
    private Provincia provincia;
    private Pais pais;

    public DireccionPostal(Direccion direccion, Ciudad ciudad, Provincia provincia, Pais pais) {
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "DireccionPostal{" +
                "direccion=" + direccion +
                ", ciudad=" + ciudad +
                ", provincia=" + provincia +
                ", pais=" + pais +
                '}';
    }
}
