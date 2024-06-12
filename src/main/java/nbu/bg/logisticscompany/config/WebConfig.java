package nbu.bg.logisticscompany.config;

import nbu.bg.logisticscompany.utility.StringToSchoolRegisterDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToSchoolRegisterDtoConverter stringToSchoolRegisterDtoConverter;

    public WebConfig(StringToSchoolRegisterDtoConverter stringToSchoolRegisterDtoConverter) {
        this.stringToSchoolRegisterDtoConverter = stringToSchoolRegisterDtoConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSchoolRegisterDtoConverter);
    }
}
