package entidades.Organizaciones;

public class Agropecuario extends Actividad{
    private final String nombre = "Agropecuario";
    private final long topeMicro = 17_260_000;
    private final long topePeq = 71_960_000;
    private final long topeMedTram1 = 426_720_000;
    private final long topeMedTram2 = 676_810_000;
    private final int topeMicroPersonal = 5;
    private final int topePeqPersonal = 10;
    private final int topeMedTram1Personal = 50;
    private final int topeMedTram2Personal = 215;

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

    public Agropecuario() {}
}
