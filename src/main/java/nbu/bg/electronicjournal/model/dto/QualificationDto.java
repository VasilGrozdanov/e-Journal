package nbu.bg.electronicjournal.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QualificationDto {

    @NotNull
    private Long teacherId;

    @NotBlank
    private String subjectSignature;

}
