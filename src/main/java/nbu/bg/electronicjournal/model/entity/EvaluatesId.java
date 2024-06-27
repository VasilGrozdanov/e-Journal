package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class EvaluatesId implements Serializable {

    private Long teacher;
    private String subject;
    private Long student;
    private LocalDateTime systemDate;

}
