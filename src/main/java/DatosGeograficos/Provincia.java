package DatosGeograficos;

public class Provincia {
    public String id;
    public String name;

    @Override
    public String toString() {
        return "Provincia {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }
}
