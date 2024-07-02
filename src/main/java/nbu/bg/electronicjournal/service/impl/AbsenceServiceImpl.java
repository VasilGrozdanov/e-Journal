package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.AbscencesDto;
import nbu.bg.electronicjournal.model.dto.TotalAbsencesDto;
import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.repository.AbsenceRepository;
import nbu.bg.electronicjournal.service.AbsenceService;
import nbu.bg.electronicjournal.service.StudentService;
import nbu.bg.electronicjournal.service.SubjectService;
import nbu.bg.electronicjournal.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class AbsenceServiceImpl implements AbsenceService {
    private AbsenceRepository absenceRepository;
    private TeacherService teacherService;
    private SubjectService subjectService;
    private StudentService studentService;

    @Override
    public <Entity> List<TotalAbsencesDto<Entity>> getTotals(List<Absence> absences,
            Function<Absence, Entity> entityFunction) {
        Map<Entity, Integer> absencesMap = new LinkedHashMap<>();
        absences.forEach(element -> absencesMap.put(entityFunction.apply(element),
                absencesMap.getOrDefault(entityFunction.apply(element), 0) + element.getType().getValue()));
        List<TotalAbsencesDto<Entity>> result = new ArrayList<>();
        absencesMap.forEach((key, value) -> result.add(new TotalAbsencesDto<>(key, new AbscencesDto(value))));
        return result;
    }

    @Override
    public List<Absence> findAllBySubject(Subject subject) {
        return absenceRepository.findAllBySubject(subject);
    }

    @Override
    public List<Absence> findAllBySchool(School school) {
        return absenceRepository.findAllByStudentGradeSchool(school);
    }

    @Override
    public List<Absence> getAll() {
        return absenceRepository.findAll();
    }
}