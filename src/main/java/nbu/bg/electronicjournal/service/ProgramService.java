package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Program;
import nbu.bg.electronicjournal.model.entity.Semester;

import java.time.LocalDate;

public interface ProgramService {
    Program getProgram(Semester semester, Long gradeId, LocalDate start, LocalDate end);
}
