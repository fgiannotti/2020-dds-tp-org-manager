package Organizaciones;

public class Categorizador{

    private TipoEmpresa tipo;

    public Categorizador(){
    }

    public TipoEmpresa categorizar(Integer cantidadPersonal, Actividad actividad, Float promedioVentas){
        int promedioEnInt = promedioVentas.intValue();
        if(promedioEnInt<=actividad.getTopeMicro()) {
            tipo = new Micro();
            return tipo;
        }else if(actividad.getTopeMicro() < promedioEnInt && promedioEnInt <= actividad.getTopePeq()) {
            tipo = new Pequenia();
            return tipo;
        }else if(actividad.getTopePeq() < promedioEnInt && promedioEnInt  <= actividad.getTopeMedTram1()) {
            tipo = new MedianaTramo1();
            return tipo;
        }else if(actividad.getTopeMedTram1() < promedioEnInt && promedioEnInt <= actividad.getTopeMedTram2()) {
            tipo = new MedianaTramo2();
            return tipo;
        }else{
            throw new RuntimeException("Empresa no puede ser categorizada, las ventas anuales superan el maximo ventas para Mediana Tramo 2");
        }
    }

}
