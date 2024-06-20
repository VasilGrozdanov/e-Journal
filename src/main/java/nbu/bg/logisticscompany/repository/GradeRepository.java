package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Evaluates;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {}
