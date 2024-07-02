package nbu.bg.electronicjournal.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Subject;

@AllArgsConstructor
@Getter
public enum StatisticsGroupingTypeAdmin {
    SUBJECT(Subject.class), SCHOOL(School.class);
    private final Class<? extends AdminGroupingEntity> entityClass;

}
