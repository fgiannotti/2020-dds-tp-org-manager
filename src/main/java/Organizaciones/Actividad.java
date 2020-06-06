package Organizaciones;

import java.util.Objects;

public enum Actividad{
    CONSTRUCCION(15230000, 90310000, 503880000, 755740000, 12, 45, 200, 590),
    SERVICIOS(8500000, 50950000,425170000,607210000, 7, 30, 165, 535),
    COMERCIO(29740000, 	178860000, 1502750000, 2146810000, 7, 35, 125, 345),
    INDUSTRIAYMINERIA(26540000, 190410000, 1190330000, 1739590000, 15, 60, 235, 655),
    AGROPECUARIO(12890000, 48480000, 345430000, 547890000, 5, 10, 50, 215);

    private final int topeMicro;
    private final int topePeq;
    private final int topeMedTram1;
    private final int topeMedTram2;
    private final int topeMicroPersonal;
    private final int topePeqPersonal;
    private final int topeMedTram1Personal;
    private final int topeMedTram2Personal;

     Actividad(int topeMicro, int topePeq, int topeMedTram1, int topeMedTram2, int topeMicroPersonal, int topePeqPersonal, int topeMedTram1Personal, int topeMedTram2Personal) {
        this.topeMicro = Objects.requireNonNull(topeMicro, "El topeMicro no puede ser nulo");
        this.topePeq = Objects.requireNonNull(topePeq, "El topePeq no puede ser nulo");
        this.topeMedTram1 = Objects.requireNonNull(topeMedTram1, "el topeMedTram1 no pude ser nulo");
        this.topeMedTram2 = Objects.requireNonNull(topeMedTram2, "El topeMedTram2 no puede ser nulo");
        this.topeMicroPersonal = Objects.requireNonNull(topeMicroPersonal, "El topeMicroPersonal no puede ser nulo");
        this.topePeqPersonal = Objects.requireNonNull(topePeqPersonal, "El topePeqPersonal no puede ser nulo");
        this.topeMedTram1Personal = Objects.requireNonNull(topeMedTram1Personal, "el topeMedTram1Personal no pude ser nulo");
        this.topeMedTram2Personal = Objects.requireNonNull(topeMedTram2Personal, "El topeMedTram2Personal no puede ser nulo");
    }

    public int getTopeMicro() {return topeMicro;}

    public int getTopePeq() {
        return topePeq;
    }

    public int getTopeMedTram1() {
        return topeMedTram1;
    }

    public int getTopeMedTram2() {
        return topeMedTram2;
    }

    public int getTopeMedTram2Personal() {
        return topeMedTram2Personal;
    }

    public int getTopeMedTram1Personal() {
        return topeMedTram1Personal;
    }

    public int getTopePequeniaPersonal() {
        return topePeqPersonal;
    }

    public int getTopeMicroPersonal() {
        return topeMicroPersonal;
    }
}
