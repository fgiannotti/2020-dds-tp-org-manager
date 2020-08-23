package DatosGeograficos;

import java.util.List;
import java.util.Optional;

public class ListadoDeCiudades {
    public String name;
    public Pais country;
    public Object geo_information;
    public List<Ciudad> cities;

    public Optional<Ciudad> getCiudadById(String id){
        return this.cities.stream()
                .filter(p -> p.id.equals(id))
                .findFirst();
    }

    public Optional<Ciudad> getCiudadByName(String nombre) {
        return this.cities.stream()
                .filter(p -> p.name.equals(nombre))
                .findFirst();
    }

}
