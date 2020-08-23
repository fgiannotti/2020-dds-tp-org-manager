package DatosGeograficos;

public class Direccion {
    private String calle;
    private Integer altura;
    private Integer piso;

    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", altura=" + altura +
                ", piso=" + piso +
                '}';
    }

    public Direccion(String calle, Integer altura, Integer piso) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
    }
}
