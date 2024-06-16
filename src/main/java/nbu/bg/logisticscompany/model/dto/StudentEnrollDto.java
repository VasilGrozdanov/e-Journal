package nbu.bg.logisticscompany.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentEnrollDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long gradeId;

}
