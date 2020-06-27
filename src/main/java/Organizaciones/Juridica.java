package Organizaciones;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Juridica extends Organizacion {
    protected String razonSocial;
    protected long cuit;
    protected Integer dirPostal;
    protected Integer codigoInscripcion;
    protected HashSet<Base> entidadesHijas;

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }

    public Juridica(String nombreFicticio, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Base _entidadHija) {
        super(nombreFicticio);
        this.razonSocial = Objects.requireNonNull(razonSocial, "La razon social no puede ser nula");
        this.cuit = Objects.requireNonNull(cuit, "El cuit no puede ser nulo");
        this.dirPostal = Objects.requireNonNull(dirPostal, "La direccion postal no puede ser nula");
        this.codigoInscripcion = Objects.requireNonNull(codigoInscripcion, "El codigo de inscripcion no puede ser nulo");
        entidadesHijas = new HashSet<Base>();
    }

    public Juridica(){
    }

    public String getRazonSocial(){
        return this.razonSocial;
    }

    public String getNombreFicticio(){
        return super.getNombreFicticio();
    }

    public Integer getDirPostal() {
        return dirPostal;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public void setDirPostal(Integer dirPostal) {
        this.dirPostal = dirPostal;
    }

    public void setCodigoInscripcion(Integer codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public void setEntidadesHijas(HashSet<Base> entidadesHijas) {
        this.entidadesHijas = entidadesHijas;
    }
}
