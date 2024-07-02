package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.AvgMarkDto;
import nbu.bg.electronicjournal.model.dto.SchoolRegisterDto;
import nbu.bg.electronicjournal.model.dto.TotalAbsencesDto;
import nbu.bg.electronicjournal.model.dto.UserRegisterDto;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.utilities.AdminGroupingEntity;
import nbu.bg.electronicjournal.utilities.StatisticsGroupingTypeAdmin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    boolean registerUser(UserRegisterDto userRegisterDto);

    List<School> getSchools();

    boolean registerSchool(SchoolRegisterDto schoolRegisterDto);

    public List<AvgMarkDto<AdminGroupingEntity>> getAvgGrade(StatisticsGroupingTypeAdmin avgMarkGroupingTypeAdmin,
            Optional<AdminGroupingEntity> specificEntity);

    public List<TotalAbsencesDto<AdminGroupingEntity>> getTotalAbsences(
            StatisticsGroupingTypeAdmin avgMarkGroupingTypeAdmin, Optional<AdminGroupingEntity> specificEntity);

    Optional<Subject> getSubjectOptional(String signature);

    Optional<School> getSchoolOptional(Long id);
}
