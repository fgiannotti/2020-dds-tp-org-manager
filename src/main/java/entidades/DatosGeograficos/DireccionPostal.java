package entidades.DatosGeograficos;

import db.Converters.CiudadConverter;
import db.Converters.EntidadPersistente;
import db.Converters.PaisConverter;
import db.Converters.ProvinciaConverter;

import javax.persistence.*;

@Entity
@Table(name="direcciones_postales")
public class DireccionPostal extends EntidadPersistente {
    @OneToOne(cascade = {CascadeType.ALL})
    private Direccion direccion;
    @Column(name = "ciudad", columnDefinition = "VARCHAR(255)")
    @Convert(converter = CiudadConverter.class)
    private Ciudad ciudad;
    @Column(name = "provincia", columnDefinition = "VARCHAR(255)")
    @Convert(converter = ProvinciaConverter.class)
    private Provincia provincia;
    @Column(name = "pais", columnDefinition = "VARCHAR(255)")
    @Convert(converter = PaisConverter.class)
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
