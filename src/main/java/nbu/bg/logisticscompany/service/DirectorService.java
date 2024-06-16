package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;

import java.util.List;

public interface DirectorService {

    boolean enrollStudent(StudentEnrollDto studentEnrollDto);

    List<Grade> getGrades();

    List<Student> getStudents();

}
