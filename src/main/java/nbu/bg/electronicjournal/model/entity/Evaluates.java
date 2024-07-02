package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(EvaluatesId.class)
@SuperBuilder
public class Evaluates {

    @Id
    @ManyToOne
    private Teacher teacher;

    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Id
    @ManyToOne
    private Student student;

    @Id
    private LocalDateTime systemDate = LocalDateTime.now();

    @NotNull
    private LocalDateTime entryDate;

    @Min(2)
    @Max(6)
    private double mark;
}
