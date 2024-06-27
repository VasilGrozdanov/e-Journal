package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Program;
import nbu.bg.electronicjournal.model.entity.Semester;
import nbu.bg.electronicjournal.repository.ProgramRepository;
import nbu.bg.electronicjournal.service.ProgramService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class ProgramServiceImpl implements ProgramService {
    private ProgramRepository programRepository;

    @Override
    public Program getProgram(Semester semester, Long gradeId, LocalDate start, LocalDate end) {
        return programRepository.findBySemesterAndGradeIdAndStartBetweenAndEndBetween(semester, gradeId,
                                        start.atStartOfDay().toLocalDate(),
                                        start.plusDays(1).atStartOfDay().toLocalDate(),
                                        end.atStartOfDay().toLocalDate(), end.plusDays(1).atStartOfDay().toLocalDate())
                                .orElseThrow(() -> new EntityNotFoundException("Program doesn't exist"));
    }

}
