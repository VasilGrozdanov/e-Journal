package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher getTeacher(Long id);

    Teacher getTeacherWithQualifications(Long id);

    List<Teacher> getAll();
}
