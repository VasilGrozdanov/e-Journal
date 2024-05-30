package nbu.bg.logisticscompany.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Parent extends User{

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "parent_kid", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns =
    @JoinColumn(name = "kid_id"))
    private Set<Student> kids;

}
