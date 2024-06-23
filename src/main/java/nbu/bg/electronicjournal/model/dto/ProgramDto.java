package nbu.bg.electronicjournal.model.dto;

import lombok.*;
import nbu.bg.electronicjournal.model.entity.Semester;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgramDto {

    @NotNull
    private Semester semester;

    @NotNull
    private Long gradeId;

    @FutureOrPresent
    private LocalDate start;

    @Future
    private LocalDate end;

    @NotNull
    private List<QualificationDto> subjectsTeached;
}
