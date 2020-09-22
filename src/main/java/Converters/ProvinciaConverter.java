package Converters;

import DatosGeograficos.Provincia;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProvinciaConverter implements AttributeConverter<Provincia, String> {

    @Override
    public String convertToDatabaseColumn(Provincia pais) {
        return pais.name;
    }

    @Override
    public Provincia convertToEntityAttribute(String prov_nombre) {
        Provincia provincia = new Provincia(prov_nombre);
        return provincia;
    }
}