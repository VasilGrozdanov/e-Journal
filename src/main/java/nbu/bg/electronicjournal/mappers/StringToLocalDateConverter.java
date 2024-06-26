package nbu.bg.electronicjournal.mappers;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        try {
            List<Integer> ints = Arrays.stream(source.split("-")).map(Integer::valueOf).collect(Collectors.toList());
            return LocalDate.of(ints.get(0), ints.get(1), ints.get(2));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + source, e);
        }
    }
}
