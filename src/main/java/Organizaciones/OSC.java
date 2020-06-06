package Organizaciones;

import Usuarios.Usuario;

import java.util.Arrays;

public class OSC extends Juridica {
    public OSC(String nombreFicticio, Usuario usuario, String razonSocial, Long cuit, Integer dirPostal, Integer codigoInscripcion) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }
}
