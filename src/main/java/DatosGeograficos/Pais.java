package DatosGeograficos;

public class Pais {
    public String id;
    public String name;
    public String locale;
    public String currency_id;

    @Override
    public String toString() {
        return "\nPais\n{\n" +
                "id='" + id + '\'' + "\n" +
                "name='" + name + '\'' + "\n" +
                "locale='" + locale + '\'' + "\n" +
                "currency_id='" + currency_id + '\'' + "\n" +
                '}';
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }
}

