package nbu.bg.logisticscompany.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class AbsenceId implements Serializable {

    private Long teacher;
    private String subject;
    private Long student;
}
