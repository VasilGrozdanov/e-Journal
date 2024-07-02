package nbu.bg.electronicjournal.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.electronicjournal.exceptions.InvalidRegistration;
import nbu.bg.electronicjournal.model.dto.AvgMarkDto;
import nbu.bg.electronicjournal.model.dto.SchoolRegisterDto;
import nbu.bg.electronicjournal.model.dto.TotalAbsencesDto;
import nbu.bg.electronicjournal.model.dto.UserRegisterDto;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.repository.*;
import nbu.bg.electronicjournal.service.AbsenceService;
import nbu.bg.electronicjournal.service.AdminService;
import nbu.bg.electronicjournal.utilities.AdminGroupingEntity;
import nbu.bg.electronicjournal.utilities.StatisticsGroupingTypeAdmin;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private DirectorRepository directorRepository;
    private SubjectRepository subjectRepository;
    private ParentRepository parentRepository;
    private AdminRepository adminRepository;
    private SchoolRepository schoolRepository;
    private EvaluatesRepository evaluatesRepository;
    private AbsenceService absenceService;
    private final PasswordEncoder encoder;

    @Override
    public boolean registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            throw new InvalidRegistration("Username is already taken!");
        }
        User newUser = User.builder().username(userRegisterDto.getUsername())
                           .password(encoder.encode(userRegisterDto.getPassword()))
                           .roles(Set.of(roleRepository.findByName(userRegisterDto.getRole()).orElseThrow()))
                           .name(userRegisterDto.getFirstName()).lastName(userRegisterDto.getLastName())
                           .age(userRegisterDto.getAge()).build();
        saveUserAccordingToRole(newUser, userRegisterDto);
        return true;
    }

    @Override
    public List<School> getSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public boolean registerSchool(SchoolRegisterDto schoolRegisterDto) {
        if (schoolRepository.existsByName(schoolRegisterDto.getName()) &&
            schoolRepository.existsByAddress(schoolRegisterDto.getAddress())) {
            throw new InvalidRegistration("School with this name and address already exists!!");
        }
        School newSchool = School.builder().name(schoolRegisterDto.getName()).address(schoolRegisterDto.getAddress())
                                 .build();
        schoolRepository.save(newSchool);
        return true;
    }

    @Override
    public List<AvgMarkDto<AdminGroupingEntity>> getAvgGrade(StatisticsGroupingTypeAdmin avgMarkGroupingTypeAdmin,
            Optional<AdminGroupingEntity> specificEntity) {
        areCompatableTypes(avgMarkGroupingTypeAdmin, specificEntity);
        Object[] data = new Object[1];
        Class<? extends AdminGroupingEntity> adminGroupingEntityClass;
        switch (avgMarkGroupingTypeAdmin) {
            case SUBJECT:
                adminGroupingEntityClass = Subject.class;
                specificEntity.ifPresentOrElse(entity -> {
                    data[0] = evaluatesRepository.findAverageMarkForSubject(((Subject) entity));
                }, () -> data[0] = evaluatesRepository.findAverageMarkForEachSubject());
                break;
            case SCHOOL:
                specificEntity.ifPresentOrElse(
                        entity -> data[0] = evaluatesRepository.findAverageMarkForSchool(((School) entity)),
                        () -> data[0] = evaluatesRepository.findAverageMarkForEachSchool());
                adminGroupingEntityClass = School.class;
                break;
            default:
                throw new IllegalArgumentException("Invalid grouping entity type");
        }
        List<Object[]> dataAsList = ((List<Object[]>) data[0]);
        return dataAsList.stream()
                         .map(pair -> new AvgMarkDto<AdminGroupingEntity>(adminGroupingEntityClass.cast(pair[0]),
                                 ((double) pair[1]))).collect(Collectors.toList());
    }

    @Override
    public List<TotalAbsencesDto<AdminGroupingEntity>> getTotalAbsences(
            StatisticsGroupingTypeAdmin avgMarkGroupingTypeAdmin, Optional<AdminGroupingEntity> specificEntity) {
        areCompatableTypes(avgMarkGroupingTypeAdmin, specificEntity);
        Object[] data = new Object[1];
        List<Absence> absences;
        switch (avgMarkGroupingTypeAdmin) {
            case SUBJECT:
                specificEntity.ifPresentOrElse(entity -> data[0] = absenceService.findAllBySubject(((Subject) entity)),
                        () -> data[0] = absenceService.getAll());
                absences = ((List<Absence>) data[0]);
                return absenceService.getTotals(absences, Absence::getSubject);
            case SCHOOL:
                specificEntity.ifPresentOrElse(entity -> data[0] = absenceService.findAllBySchool(((School) entity)),
                        () -> data[0] = absenceService.getAll());
                absences = ((List<Absence>) data[0]);
                return absenceService.getTotals(absences,
                        (Absence absence) -> absence.getStudent().getGrade().getSchool());

            default:
                throw new IllegalArgumentException("Invalid grouping entity type");
        }
    }

    @Override
    public Optional<Subject> getSubjectOptional(String signature) {
        return subjectRepository.findBySignature(signature);
    }

    @Override
    public Optional<School> getSchoolOptional(Long id) {
        return schoolRepository.findById(id);
    }

    private void saveUserAccordingToRole(User user, UserRegisterDto fullDetails) {
        switch (user.getRoles().iterator().next().getName()) {
            case ADMIN:
                Admin admin = Admin.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword())
                                   .roles(user.getRoles()).name(user.getName()).lastName(user.getLastName())
                                   .age(user.getAge()).build();
                adminRepository.save(admin);
                break;
            case DIRECTOR:
                if (fullDetails.getDirectorSchool() == null ||
                    !schoolRepository.existsById(fullDetails.getDirectorSchool())) {
                    throw new EntityNotFoundException("School doesn't exist");
                }
                Director director = Director.builder().id(user.getId()).username(user.getUsername())
                                            .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                            .lastName(user.getLastName()).age(user.getAge())
                                            .school(schoolRepository.findById(fullDetails.getDirectorSchool()).get())
                                            .build();
                directorRepository.save(director);
                break;
            case STUDENT:
                Student student = Student.builder().id(user.getId()).username(user.getUsername())
                                         .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                         .lastName(user.getLastName()).age(user.getAge())
                                         .number(fullDetails.getStudentNumber()).build();
                studentRepository.save(student);
                break;
            case PARENT:
                Parent parent = Parent.builder().id(user.getId()).username(user.getUsername())
                                      .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                      .lastName(user.getLastName()).age(user.getAge()).kids(new HashSet<>()).build();
                parentRepository.save(parent);
                break;
            case TEACHER:
                Teacher teacher = Teacher.builder().id(user.getId()).username(user.getUsername())
                                         .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                         .lastName(user.getLastName()).age(user.getAge())
                                         .qualifications(new ArrayList<>()).build();
                teacherRepository.save(teacher);
                break;
        }
    }


    private void areCompatableTypes(StatisticsGroupingTypeAdmin avgMarkGroupingTypeAdmin,
            Optional<AdminGroupingEntity> specificEntity) {
        if (specificEntity.isPresent() &&
            avgMarkGroupingTypeAdmin.getEntityClass() != specificEntity.get().getClass()) {
            throw new IllegalArgumentException("Grouping type is not the same as the specific entity instance");
        }
    }
}
