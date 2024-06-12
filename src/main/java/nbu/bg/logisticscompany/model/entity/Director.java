package nbu.bg.logisticscompany.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("DIRECTOR")
@SuperBuilder
@ToString
public class Director extends User {

    @OneToOne
    private School school;
}
