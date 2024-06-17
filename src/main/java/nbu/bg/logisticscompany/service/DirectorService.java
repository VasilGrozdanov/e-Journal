package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;

import java.util.List;

public interface DirectorService {

    boolean enrollStudent(StudentEnrollDto studentEnrollDto);

    boolean createSubject(SubjectDto newSubjectDto);

    boolean updateSubject(SubjectDto updatedSubject);

    boolean removeSubject(String subjectSignature);

    List<Grade> getGrades();

    List<Student> getStudents();

}
