package Organizaciones;

public class Categorizador{

    private TipoEmpresa tipo;

    public Categorizador(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        switch (promedioVentas){
            case (promedioVentas<=actividad.getTopeMicro()):
                tipo = new Micro();
                return tipo;
            case (actividad.getTopeMicro() < promedioVentas && promedioVentas <= actividad.getTopePeq()):
                tipo = new Pequeña();
                return tipo;
            case (actividad.getTopePeq() < promedioVentas && promedioVentas  <= actividad.getTopeMedTram1()):
                tipo = new MedianaTramo1();
                return tipo;
            case (actividad.getTopeMedTram1() < promedioVentas && promedioVentas <= actividad.getTopeMedTram2()):
                tipo = new MedianaTramo2();
                return tipo;
            default:
                throw new RuntimeException("Empresa no puede ser categorizada, las ventas anuales superan el maximo ventas para Mediana Tramo 2");
        }
    }

}
