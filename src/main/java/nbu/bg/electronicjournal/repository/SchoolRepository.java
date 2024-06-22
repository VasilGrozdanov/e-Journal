package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);

    Optional<School> findByAddress(String address);

    Optional<School> findFirstByNameAndAddress(String name, String address);

    boolean existsByName(String username);

    boolean existsByAddress(String username);
}
