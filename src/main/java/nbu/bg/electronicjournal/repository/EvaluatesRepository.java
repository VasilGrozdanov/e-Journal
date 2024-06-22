package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluatesRepository extends JpaRepository<Evaluates, Long> {
    List<Evaluates> findAllByStudent(Student student);

}



