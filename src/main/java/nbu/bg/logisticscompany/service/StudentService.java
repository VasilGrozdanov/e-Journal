package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.entity.Absence;

import java.util.List;

public interface StudentService {

    List<Absence> getAbsences(Long studentId);

}
