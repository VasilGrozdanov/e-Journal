package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.AbsenceType;
import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.Student;
import nbu.bg.electronicjournal.repository.AbsenceRepository;
import nbu.bg.electronicjournal.repository.EvaluatesRepository;
import nbu.bg.electronicjournal.repository.StudentRepository;
import nbu.bg.electronicjournal.service.StudentService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private AbsenceRepository absenceRepository;
    private EvaluatesRepository evaluatesRepository;

    @Override
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
    }

    @Override
    public List<Absence> getAbsences(Long studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException("Student with this id doesn't exist"));
        return absenceRepository.findAllByStudent(existingStudent);
    }

    @Override
    public List<Evaluates> getGrades(Long studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException("Student with this id doesn't exist"));
        return evaluatesRepository.findAllByStudent(existingStudent);
    }

    @Override
    public double calculateGPA(List<Evaluates> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Evaluates grade : grades) {
            total += grade.getMark();
        }

        return total / grades.size();
    }

    @Override
    public double calculateTotalAbsences(List<Absence> absences) {
        if (absences.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Absence absence : absences) {
            if (absence.getType() == AbsenceType.WHOLE) {
                total += 1;
            } else if (absence.getType() == AbsenceType.PARTIAL) {
                total += 0.5;
            }
        }

        return total;
    }


}
