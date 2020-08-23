package DatosGeograficos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListadoDePaises {
    public List<Pais> paises;

    public Optional<Pais> getPaisById(String id){
        return this.paises.stream()
                .filter(p -> p.id.equals(id))
                .findFirst();
    }

    public Optional<Pais> getPaisByName(String name){
        return this.paises.stream()
                .filter(p -> p.name.equals(name))
                .findFirst();
    }

    public Optional<Pais> getPaisByCurrency(String currency){
        return this.paises.stream()
                .filter(p -> p.currency_id.equals(currency))
                .findFirst();
    }

    @Override
    public String toString() {
        return this.paises.toString();
    }

    public ListadoDePaises(List<Pais> paises) {
        this.paises = new ArrayList<Pais>();
        this.paises = paises;
    }
}
