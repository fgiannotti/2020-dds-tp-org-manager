package entidades.Organizaciones;

import entidades.DatosGeograficos.DireccionPostal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "juridicas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Juridica extends Organizacion {
    @Column(name="razon_social")
    protected String razonSocial;
    @Column
    protected String cuit;
    @OneToOne(cascade = {CascadeType.ALL})
    protected DireccionPostal dirPostal;
    @Column(name="codigo_inscripcion")
    protected Integer codigoInscripcion;
    @OneToMany(mappedBy = "entidadPadre", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    protected List<Base> entidadesHijas = new ArrayList<Base>();

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }

    public Juridica(String nombreFicticio, String razonSocial, String cuit, DireccionPostal dirPostal, Integer codigoInscripcion, Base entidadHija) {
        super(nombreFicticio);
        this.razonSocial = Objects.requireNonNull(razonSocial, "La razon social no puede ser nula");
        this.cuit = Objects.requireNonNull(cuit, "El cuit no puede ser nulo");
        this.dirPostal = dirPostal;
        this.codigoInscripcion = Objects.requireNonNull(codigoInscripcion, "El codigo de inscripcion no puede ser nulo");
        if (entidadHija != null){
            entidadesHijas.add(entidadHija);
        }
    }

    public Juridica(){
    }

    public String getRazonSocial(){
        return this.razonSocial;
    }

    public String getNombreFicticio(){
        return super.getNombreFicticio();
    }

    public DireccionPostal getDirPostal() {
        return dirPostal;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setDirPostal(DireccionPostal dirPostal) {
        this.dirPostal = dirPostal;
    }

    public void setCodigoInscripcion(Integer codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public void setEntidadesHijas(List<Base> entidadesHijas) {
        this.entidadesHijas = entidadesHijas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Juridica)) return false;
        if (!super.equals(o)) return false;
        Juridica juridica = (Juridica) o;
        return getRazonSocial().equals(juridica.getRazonSocial()) &&
                getCuit().equals(juridica.getCuit()) &&
                codigoInscripcion.equals(juridica.codigoInscripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRazonSocial(), getCuit(), codigoInscripcion);
    }
}
