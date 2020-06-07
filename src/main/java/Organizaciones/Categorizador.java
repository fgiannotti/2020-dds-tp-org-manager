package Organizaciones;

public class Categorizador{

    private TipoEmpresa tipo;

    public Categorizador(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        int promedioEnInt = promedioVentas.intValue();

        if(promedioEnInt > actividad.getTopeMedTram2() && cantidadPersonal > actividad.getTopeMedTram2Personal()){
            throw new RuntimeException("Empresa no puede ser categorizada, las ventas anuales superan el maximo ventas para Mediana Tramo 2");
        }else if(promedioEnInt > actividad.getTopeMedTram1() && cantidadPersonal < actividad.getTopeMedTram2Personal()){
            return TipoEmpresa.MEDIANATRAMO2;
        }else if(promedioEnInt > actividad.getTopePeq() && cantidadPersonal < actividad.getTopeMedTram1Personal()){
            return TipoEmpresa.MEDIANATRAMO1;
        }else if(promedioEnInt > actividad.getTopeMicro() && cantidadPersonal < actividad.getTopePequeniaPersonal()){
            return TipoEmpresa.PEQUENIA;
        }else if (promedioEnInt >= 0 && cantidadPersonal < actividad.getTopeMicroPersonal()){
            return TipoEmpresa.MICRO;
        }else{
            throw new RuntimeException("No corresponde la cantidad de personal con la cantidad de facturacion por tramo");
        }
    }

}
