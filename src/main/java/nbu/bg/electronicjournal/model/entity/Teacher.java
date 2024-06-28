package nbu.bg.electronicjournal.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nbu.bg.electronicjournal.utilities.DirectorGroupingEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("TEACHER")
@SuperBuilder
public class Teacher extends User implements DirectorGroupingEntity {

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Qualification> qualifications = new HashSet<>();

    @Override
    public String toString() {
        return getFullName();
    }
}
