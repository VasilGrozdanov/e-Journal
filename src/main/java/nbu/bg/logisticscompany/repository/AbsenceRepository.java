package nbu.bg.logisticscompany.repository;

import nbu.bg.logisticscompany.model.entity.Absence;
import nbu.bg.logisticscompany.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    List<Absence> findAllByStudent(Student student);

}
