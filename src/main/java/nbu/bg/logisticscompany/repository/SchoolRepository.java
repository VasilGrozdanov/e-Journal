package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);

    Optional<School> findByAddress(String address);

    Optional<School> findFirstByNameAndAddress(String name, String address);

    boolean existsByName(String username);

    boolean existsByAddress(String username);
}
