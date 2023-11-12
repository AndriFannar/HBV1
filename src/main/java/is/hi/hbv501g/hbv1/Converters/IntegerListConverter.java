package is.hi.hbv501g.hbv1.Converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, Integer[]>
{
    @Override
    public Integer[] convertToDatabaseColumn(List<Integer> integerList)
    {
        return integerList.toArray(Integer[]::new);
    }

    @Override
    public List<Integer> convertToEntityAttribute(Integer[] integerArray)
    {
        return new ArrayList<>(Arrays.asList(integerArray));
    }
}
