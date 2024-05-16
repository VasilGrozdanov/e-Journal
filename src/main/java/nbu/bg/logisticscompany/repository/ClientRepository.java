package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
