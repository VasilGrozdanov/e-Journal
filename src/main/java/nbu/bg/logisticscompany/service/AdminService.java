package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.SchoolRegisterDto;
import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.entity.School;

import java.util.List;

public interface AdminService {

    boolean registerUser(UserRegisterDto userRegisterDto);

    List<School> getSchools();

    boolean registerSchool(SchoolRegisterDto schoolRegisterDto);

}
