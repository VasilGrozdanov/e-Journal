package nbu.bg.logisticscompany.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("STUDENT")
@SuperBuilder
@ToString
public class Student extends User {

    @Positive
    private int number;

    @ManyToOne
    private Grade grade;
}
