package nbu.bg.logisticscompany.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentEnrollDto {

    @NotBlank
    private Long studentId;

    @NotBlank
    private Long gradeId;

}
