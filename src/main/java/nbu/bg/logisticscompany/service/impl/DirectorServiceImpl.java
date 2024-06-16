package nbu.bg.logisticscompany.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.repository.GradeRepository;
import nbu.bg.logisticscompany.repository.StudentRepository;
import nbu.bg.logisticscompany.service.DirectorService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;

    @Override
    public boolean enrollStudent(StudentEnrollDto studentEnrollDto) {
        Student student = studentRepository.findById(studentEnrollDto.getStudentId()).orElseThrow(
                () -> new EntityExistsException("Student with this id doesn't exist"));
        Grade newGrade = gradeRepository.findById(studentEnrollDto.getGradeId()).orElseThrow(
                () -> new EntityExistsException("Grade with this id doesn't exist"));
        Grade currentGrade = student.getGrade();

        if (currentGrade != null) {
            currentGrade.getStudents().remove(student);
            gradeRepository.save(currentGrade);
        }
        student.setGrade(newGrade);
        newGrade.getStudents().add(student);

        studentRepository.save(student);
        gradeRepository.save(newGrade);
        return true;
    }

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

}
