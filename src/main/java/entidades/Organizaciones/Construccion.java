package entidades.Organizaciones;

public class Construccion extends Actividad {
    private final String nombre = "Construccion";
    private final long topeMicro = 19_450_000;
    private final long topePeq = 115_370_000;
    private final long topeMedTram1 = 643_710_000;
    private final long topeMedTram2 = 965_460_000;
    private final int topeMicroPersonal = 12 ;
    private final int topePeqPersonal = 45;
    private final int topeMedTram1Personal = 200 ;
    private final int topeMedTram2Personal = 590 ;

    @Override
    public long getTopeMicro() {
        return topeMicro;
    }

    @Override
    public long getTopePeq() {
        return topePeq;
    }

    @Override
    public long getTopeMedTram1() {
        return topeMedTram1;
    }

    @Override
    public long getTopeMedTram2() {
        return topeMedTram2;
    }

    @Override
    public int getTopeMicroPersonal() {
        return topeMicroPersonal;
    }

    @Override
    public int getTopePequeniaPersonal() { return topePeqPersonal; }

    @Override
    public int getTopeMedTram1Personal() {
        return topeMedTram1Personal;
    }

    @Override
    public int getTopeMedTram2Personal() {
        return topeMedTram2Personal;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public Construccion() {}
}
