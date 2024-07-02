package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nbu.bg.electronicjournal.utilities.AdminGroupingEntity;
import nbu.bg.electronicjournal.utilities.DirectorGroupingEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(indexes = { @Index(name = "idx_signature", columnList = "signature") })
public class Subject implements DirectorGroupingEntity, AdminGroupingEntity {

    @Id
    @NotBlank
    private String signature;

    @NotBlank
    private String name;

    @Override
    public String toString() {
        return String.format("%s %s", signature, name);
    }
}
