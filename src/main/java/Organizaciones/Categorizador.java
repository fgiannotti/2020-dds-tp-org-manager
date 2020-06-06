package Organizaciones;

public class Categorizador{

    private TipoEmpresa tipo;

    public Categorizador(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        int promedioEnInt = promedioVentas.intValue();
        return actividad.categorizar(promedioEnInt);
    }

}
