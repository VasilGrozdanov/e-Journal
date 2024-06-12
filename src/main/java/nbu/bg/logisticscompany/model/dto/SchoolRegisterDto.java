package nbu.bg.logisticscompany.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SchoolRegisterDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

}
