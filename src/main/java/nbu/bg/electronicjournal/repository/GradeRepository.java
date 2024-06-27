package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Grade;
import nbu.bg.electronicjournal.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    @Query("SELECT g FROM Grade g LEFT JOIN FETCH g.students WHERE g.headTeacher = :teacher")
    List<Grade> findAllByHeadTeacher(Teacher teacher);
}
