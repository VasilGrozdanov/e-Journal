package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {}
