package Organizaciones;

import Usuarios.Usuario;
import java.util.Arrays;

public class Empresa extends Juridica {
    private Integer cantidadPersonal;
    private Actividad actividad;
    private Float promedioVentas;
    private TipoEmpresa tipo;
    private Categorizador categorizador;

    public Empresa(String nombreFicticio, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Integer cantidadPersonal, Actividad actividad, Float promedioVentas) {
        super(nombreFicticio, razonSocial, cuit, dirPostal, codigoInscripcion, null);
        this.categorizador = new Categorizador();
        try {
            this.tipo = this.categorizador.categorizar(cantidadPersonal, actividad, promedioVentas);
        }
        catch (RuntimeException e){
            throw (e);
        }
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
    }

    public TipoEmpresa getTipo() {
        return tipo;
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
