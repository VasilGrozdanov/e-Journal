package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.Student;

import java.util.List;

public interface StudentService {
    Student getStudent(Long studentId);

    List<Absence> getAbsences(Long studentId);

    List<Evaluates> getGrades(Long studentId);
}
