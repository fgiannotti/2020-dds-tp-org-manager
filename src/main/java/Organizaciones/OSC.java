package Organizaciones;

import DatosGeograficos.DireccionPostal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
@DiscriminatorValue("osc")
public class OSC extends Juridica {

    public OSC(){}
    public OSC(String nombreFicticio, String razonSocial, Integer cuit, DireccionPostal dirPostal, Integer codigoInscripcion) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion, null);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }
}
