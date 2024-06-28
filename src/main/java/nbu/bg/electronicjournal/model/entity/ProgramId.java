package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ProgramId implements Serializable {
    private Semester semester;
    private Long grade;
    private LocalDate start;
    private LocalDate end;
}
