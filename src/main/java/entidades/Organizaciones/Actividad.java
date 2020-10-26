package entidades.Organizaciones;

public abstract class Actividad{

    private String nombre;

    public abstract long getTopeMicro();

    public abstract long getTopePeq();

    public abstract long getTopeMedTram1();

    public abstract long getTopeMedTram2();

    public abstract int getTopeMedTram2Personal();

    public abstract int getTopeMedTram1Personal();

    public abstract int getTopePequeniaPersonal();

    public abstract int getTopeMicroPersonal();

    public abstract String getNombre();
}
