package Organizaciones;

public enum Actividad{
    CONSTRUCCION(15230000, 90310000, 503880000, 755740000),
    SERVICIOS(8500000, 50950000,425170000,607210000),
    COMERCIO(29740000, 	178860000, 1502750000, 2146810000),
    INDUSTRIAYMINERIA(26540000, 190410000, 1190330000, 1739590000),
    AGROPECUARIO(12890000, 48480000, 345430000, 547890000);

    private final int topeMicro;
    private final int topePeq;
    private final int topeMedTram1;
    private final int topeMedTram2;

    Actividad(int topeMicro, int topePeq, int topeMedTram1, int topeMedTram2) {
        this.topeMicro = topeMicro;
        this.topePeq = topePeq;
        this.topeMedTram1 = topeMedTram1;
        this.topeMedTram2 = topeMedTram2;
    }

    public int getTopeMicro() {
        return topeMicro;
    }

    public int getTopePeq() {
        return topePeq;
    }

    public int getTopeMedTram1() {
        return topeMedTram1;
    }

    public int getTopeMedTram2() {
        return topeMedTram2;
    }
}