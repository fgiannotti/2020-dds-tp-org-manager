package entidades.DatosFinancieros;

public class Divisa {
    public String id;
    public String description;
    public String symbol;
    public Integer decimal_places;

    @Override
    public String toString() {
        return "\nDivisa\n{\n" +
                "id='" + id + '\'' + "\n" +
                "description='" + description + '\'' + "\n" +
                "symbol='" + symbol + '\'' + "\n" +
                "decimal_places=" + decimal_places + "\n" +
                '}';
    }
}
