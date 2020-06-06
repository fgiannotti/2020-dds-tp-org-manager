package Organizaciones;

public class Categorizador{

    private TipoEmpresa tipo;

    public Categorizador(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        int promedioEnInt = promedioVentas.intValue();

        if(promedioEnInt > actividad.getTopeMedTram2()){
            throw new RuntimeException("Empresa no puede ser categorizada, las ventas anuales superan el maximo ventas para Mediana Tramo 2");
        }else if(promedioEnInt > actividad.getTopeMedTram1()){
            return TipoEmpresa.MEDIANATRAMO2;
        }else if(promedioEnInt > actividad.getTopePeq()){
            return TipoEmpresa.MEDIANATRAMO1;
        }else if(promedioEnInt > actividad.getTopeMicro()){
            return TipoEmpresa.PEQUENIA;
        }else if (promedioEnInt >= 0){
            return TipoEmpresa.MICRO;
        }else{
            throw new RuntimeException("Ventas es menor a cero");
        }
    }

}
