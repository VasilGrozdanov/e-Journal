package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.model.entity.SubjectTeachedBy;
import nbu.bg.electronicjournal.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectTeachedByRepository extends JpaRepository<SubjectTeachedBy, Long> {
    Optional<SubjectTeachedBy> findByTeacher(Teacher teacher);

    Optional<SubjectTeachedBy> findBySubject(Subject subject);

    Optional<SubjectTeachedBy> findByTeacherAndSubject(Teacher teacher, Subject subject);

    Optional<SubjectTeachedBy> findByTeacherIdAndSubjectSignature(Long teacher, String subject);

    boolean existsByTeacherAndSubject(Teacher teacher, Subject subject);

}
