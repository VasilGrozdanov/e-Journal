package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.QualificationDto;
import nbu.bg.electronicjournal.model.dto.StudentEnrollDto;
import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.entity.Director;
import nbu.bg.electronicjournal.model.entity.Grade;
import nbu.bg.electronicjournal.model.entity.Student;

import java.util.List;

public interface DirectorService {

    boolean enrollStudent(StudentEnrollDto studentEnrollDto);

    boolean createSubject(SubjectDto newSubjectDto);

    boolean updateSubject(SubjectDto updatedSubject);

    boolean removeSubject(String subjectSignature);

    boolean addQualification(QualificationDto newQualificationDto, Long schoolId);

    boolean updateQualification(Long teacherId, String oldSubjectSignature, String newSubjectSignature, Long schoolId);

    boolean removeQualification(Long teacherId, String subjectSignature, Long schoolId);


    List<Grade> getGrades();

    Director getDirector(Long id);

    List<Student> getStudents();

}
