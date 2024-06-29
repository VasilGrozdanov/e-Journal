package nbu.bg.electronicjournal.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nbu.bg.electronicjournal.utilities.DirectorGroupingEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Qualification> qualifications = new ArrayList<>();

    @Override
    public String toString() {
        return getFullName();
    }
}
