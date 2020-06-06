package Organizaciones;

import Usuarios.Usuario;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Juridica extends Organizacion {
    protected String razonSocial;
    protected Integer cuit;
    protected Integer dirPostal;
    protected Integer codigoInscripcion;
    protected HashSet<Base> entidadesHijas;

    public void addEntidadHija(Base... base){
    }

    public Juridica(String nombreFicticio, Usuario usuario, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Base _entidadHija) {
        super(nombreFicticio);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.dirPostal = dirPostal;
        this.codigoInscripcion = codigoInscripcion;
        this.entidadesHijas = new HashSet<Base>();
        entidadesHijas.add(_entidadHija);
    }
}
