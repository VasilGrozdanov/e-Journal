package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.entity.Subject;

import java.util.List;

public interface SubjectService {

    SubjectDto getSubjectDto(String signature);

    Subject getSubject(String signature);

    boolean deleteSubject(String signature);

    List<Subject> getAll();

}
