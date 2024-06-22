package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Director;
import nbu.bg.electronicjournal.model.entity.Qualification;
import nbu.bg.electronicjournal.model.entity.Teacher;

public interface QualificationService {
    Qualification getQualifications(Teacher teacher, Director director);
}
