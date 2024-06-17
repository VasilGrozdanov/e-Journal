package nbu.bg.logisticscompany.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.model.entity.Subject;
import nbu.bg.logisticscompany.repository.GradeRepository;
import nbu.bg.logisticscompany.repository.StudentRepository;
import nbu.bg.logisticscompany.repository.SubjectRepository;
import nbu.bg.logisticscompany.service.DirectorService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;
    private SubjectRepository subjectRepository;

    @Override
    public boolean enrollStudent(StudentEnrollDto studentEnrollDto) {
        Student student = studentRepository.findById(studentEnrollDto.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException("Student with this id doesn't exist"));
        Grade newGrade = gradeRepository.findById(studentEnrollDto.getGradeId()).orElseThrow(
                () -> new EntityNotFoundException("Grade with this id doesn't exist"));
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
    public boolean createSubject(SubjectDto newSubjectDto) {
        if (subjectRepository.existsBySignature(newSubjectDto.getSignature())) {
            throw new EntityExistsException("Subject with this signature already exists");
        }
        subjectRepository.save(
                Subject.builder().signature(newSubjectDto.getSignature()).name(newSubjectDto.getName()).build());
        return true;
    }

    @Override
    public boolean updateSubject(SubjectDto updatedSubject) {
        Subject existingSubject = subjectRepository.findBySignature(updatedSubject.getSignature()).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
        existingSubject.setName(updatedSubject.getName());
        subjectRepository.save(existingSubject);
        return true;
    }

    @Override
    public boolean removeSubject(String subjectSignature) {
        Subject existingSubject = subjectRepository.findBySignature(subjectSignature).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
        subjectRepository.delete(existingSubject);
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
