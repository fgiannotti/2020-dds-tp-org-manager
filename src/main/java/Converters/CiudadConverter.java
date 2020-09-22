package Converters;

import DatosGeograficos.Ciudad;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CiudadConverter implements AttributeConverter<Ciudad, String> {

    @Override
    public String convertToDatabaseColumn(Ciudad ciudad) {
        return ciudad.name;
    }

    @Override
    public Ciudad convertToEntityAttribute(String ciudad_nombre) {
        Ciudad ciudad = new Ciudad();
        ciudad.setName(ciudad_nombre);
        return ciudad;
    }
}