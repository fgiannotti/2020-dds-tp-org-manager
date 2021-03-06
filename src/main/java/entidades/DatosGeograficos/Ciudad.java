package entidades.DatosGeograficos;

public class Ciudad {
    public String id;
    public String name;

    public Ciudad(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ciudad {" +
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
