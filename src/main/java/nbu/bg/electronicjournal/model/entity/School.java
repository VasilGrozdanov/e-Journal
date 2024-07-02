package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nbu.bg.electronicjournal.utilities.AdminGroupingEntity;
import nbu.bg.electronicjournal.utilities.DirectorGroupingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class School extends BaseEntity implements DirectorGroupingEntity, AdminGroupingEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;

    @Override
    public String toString() {
        return String.format("%s, %s", name, address);
    }
}
