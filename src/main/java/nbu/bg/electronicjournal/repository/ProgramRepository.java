package nbu.bg.electronicjournal.repository;

import nbu.bg.electronicjournal.model.entity.Program;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findAllByGradeSchoolOrderByGrade(School school);

    Optional<Program> findBySemesterAndGradeIdAndStartBetweenAndEndBetween(Semester semester, Long grade,
            LocalDate startNormalized, LocalDate startNormalizedPlusOne, LocalDate endNormalized,
            LocalDate endNormalizedPlusOne);
}
