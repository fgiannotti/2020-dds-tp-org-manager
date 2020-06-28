package Organizaciones;

public class Comercio extends Actividad {
    private final String nombre = "Comercio";
    private final long topeMicro = 36_320_000;
    private final long topePeq = 247_200_000;
    private final long topeMedTram1 = 1_821_760_000;
    private final long topeMedTram2 = 2_602_540_000L;
    private final int topeMicroPersonal = 7 ;
    private final int topePeqPersonal = 35;
    private final int topeMedTram1Personal = 125;
    private final int topeMedTram2Personal = 345;

    @Override
    public long getTopeMicro() {
        return topeMicro;
    }

    @Override
    public long getTopePeq() {
        return topePeq;
    }

    @Override
    public long getTopeMedTram1() { return topeMedTram1; }

    @Override
    public long getTopeMedTram2() {
        return topeMedTram2;
    }

    @Override
    public int getTopeMicroPersonal() {
        return topeMicroPersonal;
    }

    @Override
    public int getTopePequeniaPersonal() {
        return topePeqPersonal;
    }

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

    public Comercio() {}
}
