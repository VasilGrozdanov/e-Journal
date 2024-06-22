package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.SchoolRegisterDto;
import nbu.bg.electronicjournal.model.dto.UserRegisterDto;
import nbu.bg.electronicjournal.model.entity.School;

import java.util.List;

public interface AdminService {

    boolean registerUser(UserRegisterDto userRegisterDto);

    List<School> getSchools();

    boolean registerSchool(SchoolRegisterDto schoolRegisterDto);

}
