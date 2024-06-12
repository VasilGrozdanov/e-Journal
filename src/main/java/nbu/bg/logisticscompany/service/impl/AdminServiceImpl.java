package nbu.bg.logisticscompany.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.logisticscompany.exceptions.InvalidRegistration;
import nbu.bg.logisticscompany.model.dto.SchoolRegisterDto;
import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.entity.*;
import nbu.bg.logisticscompany.repository.*;
import nbu.bg.logisticscompany.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private DirectorRepository directorRepository;
    private ParentRepository parentRepository;
    private AdminRepository adminRepository;
    private SchoolRepository schoolRepository;
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

    private void saveUserAccordingToRole(User user, UserRegisterDto fullDetails) {
        switch (user.getRoles().iterator().next().getName()) {
            case ADMIN:
                Admin admin = Admin.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword())
                                   .roles(user.getRoles()).name(user.getName()).lastName(user.getLastName())
                                   .age(user.getAge()).build();
                adminRepository.save(admin);
                break;
            case DIRECTOR:
                SchoolRegisterDto directorSchool = fullDetails.getDirectorSchool();
                if (directorSchool == null) {
                    throw new EntityExistsException("School doesn't exist");
                }
                Director director = Director.builder().id(user.getId()).username(user.getUsername())
                                            .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                            .lastName(user.getLastName()).age(user.getAge()).school(schoolRepository
                                .findFirstByNameAndAddress(directorSchool.getName(), directorSchool.getAddress())
                                .orElseThrow()).build();
                System.err.println(director);
                directorRepository.save(director);
                break;
            case STUDENT:
                Student student = Student.builder().id(user.getId()).username(user.getUsername())
                                         .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                         .lastName(user.getLastName()).age(user.getAge())
                                         .number(fullDetails.getStudentNumber()).build();
                studentRepository.save(student);
                System.err.println(student);
                break;
            case PARENT:
                Parent parent = Parent.builder().id(user.getId()).username(user.getUsername())
                                      .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                      .lastName(user.getLastName()).age(user.getAge()).kids(new HashSet<>()).build();
                System.err.println(parent);
                parentRepository.save(parent);
                break;
            case TEACHER:
                Teacher teacher = Teacher.builder().id(user.getId()).username(user.getUsername())
                                         .password(user.getPassword()).roles(user.getRoles()).name(user.getName())
                                         .lastName(user.getLastName()).age(user.getAge())
                                         .qualifications(new ArrayList<>()).build();
                System.err.println(teacher);
                teacherRepository.save(teacher);
                break;
        }
    }

}
