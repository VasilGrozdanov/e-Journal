package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.entity.Absence;
import nbu.bg.logisticscompany.model.entity.Evaluates;

import java.util.List;

public interface StudentService {

    List<Absence> getAbsences(Long studentId);

    List<Evaluates> getGrades(Long studentId);
}
