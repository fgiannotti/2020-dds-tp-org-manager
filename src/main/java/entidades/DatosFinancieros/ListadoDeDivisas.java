package entidades.DatosFinancieros;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListadoDeDivisas {
    public List<Divisa> divisas;

    public Optional<Divisa> getDivisaById(String id){
        return this.divisas.stream()
                .filter(p -> p.id == id)
                .findFirst();
    }

    public Optional<Divisa> getDivisaBySymbol(String symbol){
        return this.divisas.stream()
                .filter(p -> p.symbol == symbol)
                .findFirst();
    }

    public Optional<Divisa> getDivisaByDescription(String description){
        return this.divisas.stream()
                .filter(p -> p.description == description)
                .findFirst();
    }

    public ListadoDeDivisas(List<Divisa> divisas) {
        this.divisas = new ArrayList<Divisa>();
        this.divisas = divisas;
    }

    @Override
    public String toString() { return this.divisas.toString(); }
}
