package is.hi.hbv501g.hbv1.Converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converts Integer Lists to Integer Arrays and vice versa.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-11-09
 * @version 1.0
 */
@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, Integer[]>
{
    /**
     * Convert Integer List to Integer Array
     *
     * @param integerList List to convert.
     * @return            Array of items in list.
     */
    @Override
    public Integer[] convertToDatabaseColumn(List<Integer> integerList)
    {
        return integerList.toArray(Integer[]::new);
    }


    /**
     * Converts from Integer array to Integer List.
     *
     * @param integerArray Array to turn into a list.
     * @return             Integer list from array.
     */
    @Override
    public List<Integer> convertToEntityAttribute(Integer[] integerArray)
    {
        return new ArrayList<>(Arrays.asList(integerArray));
    }
}
