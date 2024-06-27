package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EvaluatesRepository extends JpaRepository<Evaluates, Long> {
    List<Evaluates> findAllByStudent(Student student);

    @Query("SELECT e.teacher, AVG(e.mark) FROM Evaluates e GROUP BY e.teacher ORDER BY e.teacher.username")
    List<Object[]> findAverageMarkForEachTeacher();
}



