package nbu.bg.electronicjournal.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TotalAbsencesDto<Entity> {
    private Entity entity;
    private AbscencesDto absences;
}
