package entidades.Organizaciones;

import entidades.DatosGeograficos.DireccionPostal;
import entidades.Estrategias.CategorizadorEmpresa;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@DiscriminatorValue("empresa")
public class Empresa extends Juridica {
    @Column(name="cantidad_personal")
    @Type(type = "org.hibernate.type.IntegerType")
    private int cantidadPersonal;
    @Transient
    private Actividad actividad;
    @Column(name="promedio_ventas")
    private float promedioVentas;

    @Column(name="tipo_empresa")
    @Enumerated
    private TipoEmpresa tipo;
    @Transient
    private CategorizadorEmpresa categorizadorEmpresa;

    public Empresa(String nombreFicticio, String razonSocial, Integer cuit, DireccionPostal dirPostal, Integer codigoInscripcion, Integer cantidadPersonal, Actividad actividad, Float promedioVentas) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion, null);
        this.actividad = actividad;
        this.categorizadorEmpresa = new CategorizadorEmpresa();
        try {
            this.tipo = this.categorizadorEmpresa.categorizar(cantidadPersonal, actividad, promedioVentas);
        }
        catch (RuntimeException e){
            throw (e);
        }
    }

    public Empresa(){
        super.entidadesHijas = new ArrayList<Base>();
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }

    public TipoEmpresa getTipo() {
        return tipo;
    }

    public Integer getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(Integer cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Float getPromedioVentas() {
        return promedioVentas;
    }

    public void setPromedioVentas(Float promedioVentas) {
        this.promedioVentas = promedioVentas;
    }

    public void setTipo(TipoEmpresa tipo) {
        this.tipo = tipo;
    }

    public CategorizadorEmpresa getCategorizadorEmpresa() {
        return categorizadorEmpresa;
    }

    public void setCategorizadorEmpresa(CategorizadorEmpresa categorizadorEmpresa) {
        this.categorizadorEmpresa = categorizadorEmpresa;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "cantidadPersonal=" + cantidadPersonal +
                ", actividad=" + actividad +
                ", promedioVentas=" + promedioVentas +
                ", tipo=" + tipo +
                ", razonSocial='" + razonSocial + '\'' +
                ", cuit=" + cuit +
                ", dirPostal=" + dirPostal +
                ", codigoInscripcion=" + codigoInscripcion +
                ", entidadesHijas=" + entidadesHijas +
                ", nombreFicticio='" + this.getNombreFicticio() + '\'' +
                '}';
    }
}
