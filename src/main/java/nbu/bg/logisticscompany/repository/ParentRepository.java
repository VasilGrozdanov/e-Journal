package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByUsername(String username);


    @Query("SELECT p FROM Parent p LEFT JOIN FETCH p.kids WHERE p.id = :parentId")
    Parent findByIdWithKids(@Param("parentId") Long parentId);

}
