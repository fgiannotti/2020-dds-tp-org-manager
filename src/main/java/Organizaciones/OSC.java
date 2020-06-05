package Organizaciones;

import Usuarios.Usuario;

import java.util.Arrays;

public class OSC extends Juridica {
    public OSC(String nombreFicticio, Usuario usuario, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion) {
        super(nombreFicticio, usuario, razonSocial, cuit, dirPostal, codigoInscripcion);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }
}
