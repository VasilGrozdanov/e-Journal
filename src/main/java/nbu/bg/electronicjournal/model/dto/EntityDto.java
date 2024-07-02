package nbu.bg.electronicjournal.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntityDto<Entity> {
    private Entity object;
    private Object id;
}
