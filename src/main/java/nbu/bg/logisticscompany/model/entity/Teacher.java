package nbu.bg.logisticscompany.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Teacher extends User {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<Qualification> qualifications;

}
