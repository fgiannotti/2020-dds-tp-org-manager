package Organizaciones;

import Usuarios.Usuario;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Juridica extends Organizacion {
    protected String razonSocial;
    protected Integer cuit;
    protected Integer dirPostal;
    protected Integer codigoInscripcion;
    protected HashSet<Base> entidadesHijas;

    public void addEntidadHija(Base... base){
    }

    public Juridica(String nombreFicticio, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Base _entidadHija) {
        super(nombreFicticio);
        this.razonSocial = Objects.requireNonNull(razonSocial, "La razon social no puede ser nula");
        this.cuit = Objects.requireNonNull(cuit, "El cuit no puede ser nulo");
        this.dirPostal = Objects.requireNonNull(dirPostal, "La direccion postal no puede ser nula");
        this.codigoInscripcion = Objects.requireNonNull(codigoInscripcion, "El codigo de inscripcion no puede ser nulo");
        this.entidadesHijas = new HashSet<Base>();
        entidadesHijas.add(_entidadHija);
    }

    public String getRazonSocial(){
        return this.razonSocial;
    }

    public String getNombreFicticio(){
        return super.getNombreFicticio();
    }
}
