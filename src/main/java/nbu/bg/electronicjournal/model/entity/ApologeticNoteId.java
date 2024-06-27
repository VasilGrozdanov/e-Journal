package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class ApologeticNoteId implements Serializable {
    private Long student;
    private LocalDateTime start;
    private LocalDateTime end;
}
