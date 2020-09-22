package DatosGeograficos;

import converters.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="direcciones")
public class DireccionPostal extends EntidadPersistente {
    @Transient //TODO
    private Direccion direccion;
    @Transient
    private Ciudad ciudad;
    @Transient
    private Provincia provincia;
    @Transient
    private Pais pais;

    public DireccionPostal() {
    }

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
