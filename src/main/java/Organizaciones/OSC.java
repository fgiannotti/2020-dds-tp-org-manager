package Organizaciones;

import DatosGeograficos.DireccionPostal;

import java.util.Arrays;

public class OSC extends Juridica {
    public OSC(String nombreFicticio, String razonSocial, Integer cuit, DireccionPostal dirPostal, Integer codigoInscripcion) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion, null);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }
}
