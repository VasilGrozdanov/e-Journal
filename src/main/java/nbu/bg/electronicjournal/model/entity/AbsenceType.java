package nbu.bg.electronicjournal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AbsenceType {
    PARTIAL(1), WHOLE(3);
    private final int value;
}
