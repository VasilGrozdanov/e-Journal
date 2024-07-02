package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);


    @Query("SELECT s FROM Student s WHERE s.grade.headTeacher.id = :teacherId")
    List<Student> findHeadTeacherStudents(@Param("teacherId") Long teacherId);

}
