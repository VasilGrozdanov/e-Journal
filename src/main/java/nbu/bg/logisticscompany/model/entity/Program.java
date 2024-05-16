package nbu.bg.logisticscompany.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ProgramId.class)
@SuperBuilder
public class Program {

    @Id
    @Enumerated(EnumType.ORDINAL)
    private Semester semester;

    @Id
    @OneToOne
    private Grade grade;

    @Id
    @FutureOrPresent
    private LocalDate start;

    @Id
    @Future
    private LocalDate end;

    @ManyToMany
    private List<SubjectTeachedBy> subjectsTeached;
}
