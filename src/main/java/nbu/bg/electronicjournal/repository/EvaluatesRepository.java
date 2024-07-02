package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Student;
import nbu.bg.electronicjournal.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluatesRepository extends JpaRepository<Evaluates, Long> {
    List<Evaluates> findAllByStudent(Student student);

    @Query("SELECT e.subject, AVG(e.mark) FROM Evaluates e GROUP BY e.subject ORDER BY e.subject.signature")
    List<Object[]> findAverageMarkForEachSubject();

    @Query("SELECT e.student.grade.school, AVG(e.mark) FROM Evaluates e GROUP BY e.student.grade.school ORDER BY e" +
           ".student.grade.school.name")
    List<Object[]> findAverageMarkForEachSchool();

    @Query("SELECT e.teacher, AVG(e.mark) FROM Evaluates e WHERE e.student.grade.school = :school GROUP BY e.teacher " +
           "ORDER BY e.teacher.username ")
    List<Object[]> findAverageMarkForEachTeacherInSchool(@Param("school") School school);

    @Query("SELECT e.subject, AVG(e.mark) FROM Evaluates e WHERE e.student.grade.school = :school GROUP BY e.subject " +
           "ORDER BY e.subject.signature")
    List<Object[]> findAverageMarkForEachSubjectInSchool(@Param("school") School school);

    @Query("SELECT e.student, AVG(e.mark) FROM Evaluates e WHERE e.student.grade.school = :school GROUP BY e.student " +
           "ORDER BY e.student.username")
    List<Object[]> findAverageMarkForEachStudentInSchool(@Param("school") School school);

    @Query("SELECT e.student.grade.school, AVG(e.mark) FROM Evaluates e WHERE e.student.grade.school = :school")
    List<Object[]> findAverageMarkForSchool(@Param("school") School school);

    @Query("SELECT e.subject, AVG(e.mark) FROM Evaluates e WHERE e.subject = :subject")
    List<Object[]> findAverageMarkForSubject(@Param("subject") Subject subject);

}



