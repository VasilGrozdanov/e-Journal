package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Role;
import nbu.bg.logisticscompany.model.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(UserRole role);
}
