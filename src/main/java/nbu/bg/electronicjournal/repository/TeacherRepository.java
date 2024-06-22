package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);

    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.qualifications WHERE t.id = :teacherId")
    Optional<Teacher> findByIdWithQualifications(@Param("teacherId") Long teacherId);

}
