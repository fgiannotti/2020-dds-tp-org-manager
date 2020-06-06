package Organizaciones;

import Usuarios.Usuario;
import java.util.Arrays;

public class Empresa extends Juridica {
    private Integer cantidadPersonal;
    private Actividad actividad;
    private Float promedioVentas;
    private TipoEmpresa tipo;
    private Categorizador categorizador;

    public Empresa(String nombreFicticio, Usuario usuario, String razonSocial, Integer cuit, Integer dirPostal, Integer codigoInscripcion, Integer cantidadPersonal, Actividad actividad, Float promedioVentas) {
        super(nombreFicticio, usuario, razonSocial, cuit, dirPostal, codigoInscripcion, null);
        this.categorizador = new Categorizador();
        this.tipo = this.categorizador.categorizar(cantidadPersonal, actividad, promedioVentas);
    }

    public Empresa(String nombreFicticio, Usuario usuario, String razonSocial, Integer cuit, Integer dirPostal, Integer cantidadPersonal, Actividad actividad, Float promedioVentas) {
    this(nombreFicticio, usuario, razonSocial, cuit, dirPostal,0, cantidadPersonal, actividad, promedioVentas);
    }

    public void addEntidadHija(Base... base){
        entidadesHijas.addAll(Arrays.asList(base));
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
                ", nombreFicticio='" + nombreFicticio + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}