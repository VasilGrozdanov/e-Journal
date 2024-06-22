package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String username);

    Optional<Subject> findBySignature(String signature);

    boolean existsBySignature(String signature);
}
