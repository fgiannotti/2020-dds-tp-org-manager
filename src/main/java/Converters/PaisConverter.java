package Converters;

import DatosGeograficos.Pais;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaisConverter implements AttributeConverter<Pais, String> {

    @Override
    public String convertToDatabaseColumn(Pais pais) {
        return pais.name;
    }

    @Override
    public Pais convertToEntityAttribute(String pais_nombre) {
        Pais pais = new Pais();
        pais.setName(pais_nombre);
        return pais;
    }
}