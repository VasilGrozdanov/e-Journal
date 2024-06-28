package nbu.bg.electronicjournal.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AvgMarkDto<Entity> {
    private Entity entity;
    private double mark;
}
