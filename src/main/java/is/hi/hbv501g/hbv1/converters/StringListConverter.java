package is.hi.hbv501g.hbv1.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String[]>
{
    @Override
    public String[] convertToDatabaseColumn(List<String> stringList)
    {
        return stringList.toArray(String[]::new);
    }

    @Override
    public List<String> convertToEntityAttribute(String[] stringList)
    {
        return new ArrayList<>(Arrays.asList(stringList));
    }
}
