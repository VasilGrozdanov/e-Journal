package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.model.entity.Subject;

import java.util.List;

public interface SubjectService {

    SubjectDto getSubject(String signature);

    boolean deleteSubject(String signature);

    List<Subject> getSubjects();

}
