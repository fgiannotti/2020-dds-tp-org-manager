package Organizaciones;

import Estrategias.CategorizadorEmpresa;

import java.util.Arrays;
import java.util.HashSet;

public class Empresa extends Juridica {
    private Integer cantidadPersonal;
    private Actividad actividad;
    private Float promedioVentas;
    private TipoEmpresa tipo;
    private CategorizadorEmpresa categorizadorEmpresa;

    public Empresa(String nombreFicticio, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Integer cantidadPersonal, Actividad actividad, Float promedioVentas) {
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
        super.entidadesHijas = new HashSet<>();
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
