package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Student;
import nbu.bg.electronicjournal.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    List<Absence> findAllByStudent(Student student);

    List<Absence> findAllBySubject(Subject subject);

    List<Absence> findAllByStudentGradeSchool(School school);

    //    @Query("SELECT a FROM Absence a GROUP BY a.subject ORDER BY a.subject.signature")
    //    List<Absence> findAllGroupedBySubject();
    //
    //
    //    @Query("SELECT a FROM Absence a GROUP BY a.student.grade.school ORDER BY a.student.grade.school.name")
    //    List<Absence> findAllGroupedBySchool();
    //
    //    @Query("SELECT a.subject.signature FROM Absence a WHERE a.subject = :subject GROUP BY a.subject.signature")
    //    List<Object[]> getAllSubjects(@Param("subject") Subject subject);
    //
    //    @Query("SELECT a FROM Absence a WHERE a.student.grade.school = :school GROUP BY a.student.grade.school
    //    ORDER BY a" +
    //           ".student.grade.school.name")
    //    List<Absence> findAllBySchool(@Param("school") School school);


}
