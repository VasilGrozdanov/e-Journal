package nbu.bg.logisticscompany.utility;

import nbu.bg.logisticscompany.model.dto.SchoolRegisterDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSchoolRegisterDtoConverter implements Converter<String, SchoolRegisterDto> {

    @Override
    public SchoolRegisterDto convert(String source) {
        try {
            String separator = ", ";
            return new SchoolRegisterDto(source.substring(0, source.indexOf(separator)),
                    source.substring(source.indexOf(separator) + separator.length()));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid format for SchoolRegisterDto", e);
        }
    }
}
