package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Director;
import nbu.bg.electronicjournal.model.entity.Qualification;
import nbu.bg.electronicjournal.model.entity.Teacher;
import nbu.bg.electronicjournal.repository.QualificationRepository;
import nbu.bg.electronicjournal.repository.SchoolRepository;
import nbu.bg.electronicjournal.service.QualificationService;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class QualificationServiceImpl implements QualificationService {
    private QualificationRepository qualificationRepository;
    private SchoolRepository schoolRepository;

    @Override
    public Qualification getQualifications(Teacher teacher, Director director) {
        return qualificationRepository.findByTeacherAndSchool(teacher, director.getSchool()).orElse(null);
    }
}
