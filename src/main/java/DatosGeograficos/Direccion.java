package DatosGeograficos;

import Converters.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="direcciones")
public class Direccion extends EntidadPersistente {
    @Column
    private String calle;
    @Column
    private Integer altura;
    @Column
    private Integer piso;

    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", altura=" + altura +
                ", piso=" + piso +
                '}';
    }

    public Direccion(){}

    public Direccion(String calle, Integer altura, Integer piso) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
    }
}
