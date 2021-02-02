package entidades.DatosGeograficos;

public class Pais {
    public String id;
    public String name;
    public String locale;
    public String currency_id;

    public Pais(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

    public Pais() {
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", locale='" + locale + '\'' +
                ", currency_id='" + currency_id + '\'' +
                '}';
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }
}

