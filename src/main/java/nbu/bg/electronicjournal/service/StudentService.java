package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.Evaluates;

import java.util.List;

public interface StudentService {

    List<Absence> getAbsences(Long studentId);

    List<Evaluates> getGrades(Long studentId);
}
