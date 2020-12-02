package entidades.Organizaciones;

public class Servicios extends Actividad {
        private final String nombre = "Servicios";
        private final long topeMicro = 9_900_000;
        private final long topePeq = 59_710_000;
        private final long topeMedTram1 = 494_200_000;
        private final long topeMedTram2 = 705_790_000;
        private final int topeMicroPersonal = 7 ;
        private final int topePeqPersonal = 30;
        private final int topeMedTram1Personal = 165;
        private final int topeMedTram2Personal = 535;

        @Override
        public long getTopeMicro() { return topeMicro; }

        @Override
        public long getTopePeq() { return topePeq; }

        @Override
        public long getTopeMedTram1() { return topeMedTram1; }

        @Override
        public long getTopeMedTram2() { return topeMedTram2; }

        @Override
        public int getTopeMicroPersonal() { return topeMicroPersonal; }

        @Override
        public int getTopePequeniaPersonal() { return topePeqPersonal; }

        @Override
        public int getTopeMedTram1Personal() { return topeMedTram1Personal; }

        @Override
        public int getTopeMedTram2Personal() { return topeMedTram2Personal; }

        @Override
        public String getNombre() {
                return nombre;
        }

        public Servicios() {}
}
