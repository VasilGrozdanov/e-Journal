package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.TotalAbsencesDto;
import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Subject;

import java.util.List;
import java.util.function.Function;

public interface AbsenceService {

    <Entity> List<TotalAbsencesDto<Entity>> getTotals(List<Absence> absences, Function<Absence, Entity> entityFunction);

    List<Absence> findAllBySubject(Subject subject);

    List<Absence> findAllBySchool(School school);

    List<Absence> getAll();

}
