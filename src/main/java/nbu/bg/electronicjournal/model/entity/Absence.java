package nbu.bg.electronicjournal.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(AbsenceId.class)
@SuperBuilder
@ToString
public class Absence {

    @Id
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Id
    @ManyToOne
    //    @JoinColumn(name = "subject_id", referencedColumnName = "id", foreignKey = @ForeignKey(name =
    //    "fk_absence_subject"))
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    private LocalDateTime systemDate = LocalDateTime.now();

    @NotNull
    private LocalDateTime entryDate;

    @Enumerated(EnumType.STRING)
    private AbsenceType type;

}
