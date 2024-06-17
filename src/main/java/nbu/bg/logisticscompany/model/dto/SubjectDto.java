package nbu.bg.logisticscompany.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectDto {

    @NotBlank
    private String signature;

    @NotBlank
    private String name;
}
