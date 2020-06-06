package Organizaciones;

import Usuarios.Usuario;

import java.util.Arrays;

public class OSC extends Juridica {
    public OSC(String nombreFicticio, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion, null);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }
}
