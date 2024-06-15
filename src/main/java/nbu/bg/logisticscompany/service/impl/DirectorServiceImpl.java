package nbu.bg.logisticscompany.service.impl;

import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.repository.GradeRepository;
import nbu.bg.logisticscompany.repository.StudentRepository;
import nbu.bg.logisticscompany.service.DirectorService;

import javax.persistence.EntityExistsException;

public class DirectorServiceImpl implements DirectorService {

    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;

    @Override
    public boolean enrollStudent(StudentEnrollDto studentEnrollDto) {
        Student student = studentRepository.findById(studentEnrollDto.getStudentId()).orElseThrow(
                () -> new EntityExistsException("Student with this id doesn't exist"));
        Grade grade = gradeRepository.findById(studentEnrollDto.getStudentId())
                                     .orElseThrow(() -> new EntityExistsException("Grade with this id doesn't exist"));
        student.setGrade(grade);
        grade.getStudents().add(student);
        studentRepository.save(student);
        gradeRepository.save(grade);
        return true;
    }

}
