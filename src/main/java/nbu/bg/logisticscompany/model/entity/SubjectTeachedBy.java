package nbu.bg.logisticscompany.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(SubjectTeachedById.class)
@SuperBuilder
@Table(indexes = { @Index(name = "idx_teacher_id", columnList = "teacher_id"),
                   @Index(name = "idx_subject_id", columnList = "subject_id") })
public class SubjectTeachedBy {

    @Id
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    //    @org.hibernate.annotations.Index(name = "idx_teacher_id")
    private Teacher teacher;

    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    //    @org.hibernate.annotations.Index(name = "idx_subject_id")
    private Subject subject;
}
