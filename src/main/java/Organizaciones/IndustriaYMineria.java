package Organizaciones;

public class IndustriaYMineria extends Actividad{
    private final String nombre = "IndustriaYMineria";
    private final long topeMicro = 33_920_000;
    private final long topePeq = 243_290_000;
    private final long topeMedTram1 = 1_651_750_000;
    private final long topeMedTram2 = 2_540_380_000L;
    private final int topeMicroPersonal = 15 ;
    private final int topePeqPersonal = 60;
    private final int topeMedTram1Personal = 235;
    private final int topeMedTram2Personal = 655;

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

    public IndustriaYMineria() {}
}
