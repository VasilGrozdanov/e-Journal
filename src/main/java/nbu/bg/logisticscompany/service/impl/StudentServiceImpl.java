package nbu.bg.logisticscompany.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.entity.Absence;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.repository.AbsenceRepository;
import nbu.bg.logisticscompany.repository.StudentRepository;
import nbu.bg.logisticscompany.service.StudentService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private AbsenceRepository absenceRepository;

    @Override
    public List<Absence> getAbsences(Long studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException("Student with this id doesn't exist"));
        return absenceRepository.findAllByStudent(existingStudent);
    }

}
