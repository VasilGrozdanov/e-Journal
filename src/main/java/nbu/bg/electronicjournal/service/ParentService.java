package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.Student;

import java.util.Set;

public interface ParentService {

    Set<Student> getKids(Long parentId);

}
