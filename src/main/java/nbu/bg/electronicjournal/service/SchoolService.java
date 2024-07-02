package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.School;

import java.util.List;

public interface SchoolService {
    School getSchool(Long id);

    List<School> getSchools();
}
