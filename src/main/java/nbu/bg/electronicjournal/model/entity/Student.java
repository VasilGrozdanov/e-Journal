package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nbu.bg.electronicjournal.utilities.DirectorGroupingEntity;

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
public class Student extends User implements DirectorGroupingEntity {

    @Positive
    private int number;

    @ManyToOne
    private Grade grade;

    @Override
    public String toString() {
        return String.format("%s - %s %s", getUsername(), getName(), getLastName());
    }

}
