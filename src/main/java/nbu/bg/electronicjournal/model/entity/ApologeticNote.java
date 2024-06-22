package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ApologeticNoteId.class)
@SuperBuilder
public class ApologeticNote {

    @Id
    @ManyToOne
    private Student student;

    @Id
    private LocalDateTime start;

    @Id
    private LocalDateTime end;

}
