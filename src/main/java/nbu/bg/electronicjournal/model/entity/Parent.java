package nbu.bg.electronicjournal.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("PARENT")
@SuperBuilder
@ToString
public class Parent extends User {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "parent_kid", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns =
    @JoinColumn(name = "kid_id"))
    private Set<Student> kids = new HashSet<>();

}
