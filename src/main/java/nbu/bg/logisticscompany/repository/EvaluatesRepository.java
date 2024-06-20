package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Evaluates;
import nbu.bg.logisticscompany.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluatesRepository extends JpaRepository<Evaluates, Long> {
      List<Evaluates> findAllByStudent(Student student);

}



