package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Qualification;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    Optional<Qualification> findByTeacherAndSchool(Teacher teacher, School school);
}
