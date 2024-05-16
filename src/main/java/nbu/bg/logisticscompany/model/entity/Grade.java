package nbu.bg.logisticscompany.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Grade extends BaseEntity {

    @NotBlank
    private LocalDate graduationYear;

    @NotBlank
    @Length(min = 1, max = 1)
    private String letter;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToOne
    private School school;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private School headTeacher;

    @OneToOne
    private Program program;

}
