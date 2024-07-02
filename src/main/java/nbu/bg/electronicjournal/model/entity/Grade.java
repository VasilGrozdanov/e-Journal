package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("GRADE")
@SuperBuilder
public class Grade extends BaseEntity {

    @NotNull
    private LocalDate graduationYear;

    @NotBlank
    @Length(min = 1, max = 1)
    private String letter;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "grade")
    private Set<Student> students;

    @OneToOne
    private School school;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Teacher headTeacher;

    @OneToOne
    private Program program;

    @Override
    public String toString() {
        return String.format("%s, %s - %s", school, graduationYear, letter);
    }
}
