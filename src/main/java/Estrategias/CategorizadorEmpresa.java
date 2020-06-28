package Estrategias;

import Organizaciones.Actividad;
import Organizaciones.TipoEmpresa;

public class CategorizadorEmpresa {

    private TipoEmpresa tipo;

    public CategorizadorEmpresa(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        long promedioEnLong = promedioVentas.longValue();

        if(promedioEnLong > actividad.getTopeMedTram2() && cantidadPersonal > actividad.getTopeMedTram2Personal()){
            throw new RuntimeException("Empresa no puede ser categorizada, las ventas anuales superan el maximo ventas para Mediana Tramo 2");
        }else if(promedioEnLong > actividad.getTopeMedTram1() && cantidadPersonal < actividad.getTopeMedTram2Personal()){
            return TipoEmpresa.MEDIANATRAMO2;
        }else if(promedioEnLong > actividad.getTopePeq() && cantidadPersonal < actividad.getTopeMedTram1Personal()){
            return TipoEmpresa.MEDIANATRAMO1;
        }else if(promedioEnLong > actividad.getTopeMicro() && cantidadPersonal < actividad.getTopePequeniaPersonal()){
            return TipoEmpresa.PEQUENIA;
        }else if (promedioEnLong >= 0 && cantidadPersonal < actividad.getTopeMicroPersonal()){
            return TipoEmpresa.MICRO;
        }else{
            throw new RuntimeException("No corresponde la cantidad de personal con la cantidad de facturacion por tramo");
        }
    }

}
