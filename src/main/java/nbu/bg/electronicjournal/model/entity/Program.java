package nbu.bg.electronicjournal.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
    @Column(name = "start", columnDefinition = "DATE")
    private LocalDate start;

    @Id
    @Column(name = "end", columnDefinition = "DATE")
    private LocalDate end;

    @ManyToMany
    private Set<SubjectTeachedBy> subjectsTeached;
}
