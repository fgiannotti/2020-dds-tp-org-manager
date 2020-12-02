package entidades.DatosGeograficos;

import java.util.List;
import java.util.Optional;

public class ListadoDeProvincias {
    public String name;
    public String locale;
    public String currency_id;
    public String decimal_separator;
    public String thousands_separator;
    public String time_zone;
    public Object geo_information;
    public List<Provincia> states;

    public Optional<Provincia> getProvinciaById(String id){
        return this.states.stream()
                .filter(p -> p.id.equals(id))
                .findFirst();
    }

    public Optional<Provincia> getProvinciaByName(String nombre) {
        return this.states.stream()
                .filter(p -> p.name.equals(nombre))
                .findFirst();
    }
    }
