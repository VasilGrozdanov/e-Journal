package nbu.bg.electronicjournal;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.repository.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


@Component
@AllArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private DirectorRepository directorRepository;
    private ParentRepository parentRepository;
    private AdminRepository adminRepository;
    private SchoolRepository schoolRepository;
    private GradeRepository gradeRepository;
    private SubjectRepository subjectRepository;
    private AbsenceRepository absenceRepository;
    private EvaluatesRepository evaluatesRepository;
    private QualificationRepository qualificationRepository;
    private final PasswordEncoder passwordEncoder;
    static boolean alreadySetup = false;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup || adminRepository.existsByUsername("admin")) {
            return;
        }

        String system = "System";

        Admin admin = Admin.builder().roles(new HashSet<>(List.of(new Role("Admin")))).username("admin")
                           .password(passwordEncoder.encode("admin")).name(system).lastName(system).age(30).build();


        Teacher teacher = Teacher.builder().roles(new HashSet<>(List.of(new Role("Teacher")))).username("teacher")
                                 .password(passwordEncoder.encode("teacher")).name(system).lastName(system).age(60)
                                 .build();

        Student student = Student.builder().roles(new HashSet<>(List.of(new Role("Student")))).username("student")
                                 .password(passwordEncoder.encode("student")).name(system).lastName(system).age(14)
                                 .number(1).build();

        Parent parent = Parent.builder().roles(new HashSet<>(List.of(new Role("Parent")))).username("parent")
                              .password(passwordEncoder.encode("parent")).name(system).lastName(system).age(35).build();

        School school = School.builder().name(system).address(system).build();
        Grade gradeA = Grade.builder().graduationYear(LocalDate.now()).school(school).letter("A").build();
        Grade gradeB = Grade.builder().graduationYear(LocalDate.now()).school(school).letter("B").build();
        Subject subject = Subject.builder().signature(system).name(system).build();
        Absence absence = Absence.builder().teacher(teacher).subject(subject).student(student)
                                 .systemDate(LocalDateTime.now()).entryDate(LocalDateTime.now()).type(AbsenceType.WHOLE)
                                 .build();
        //        Qualification qualification = Qualification.builder().teacher(teacher).subjects(Set.of(subject))
        //        .school(school)
        //                                                   .build();
        Evaluates evaluates = Evaluates.builder().teacher(teacher).subject(subject).student(student)
                                       .systemDate(LocalDateTime.now()).entryDate(LocalDateTime.now()).mark(6).build();

        Director director = Director.builder().roles(new HashSet<>(List.of(new Role("Director")))).username("director")
                                    .password(passwordEncoder.encode("director")).name(system).lastName(system).age(55)
                                    .school(school).build();


        // persist users
        adminRepository.save(admin);
        teacherRepository.save(teacher);
        studentRepository.save(student);
        parentRepository.save(parent);
        schoolRepository.save(school);
        gradeRepository.save(gradeA);
        gradeRepository.save(gradeB);
        subjectRepository.save(subject);
        absenceRepository.save(absence);
        evaluatesRepository.save(evaluates);
        //        qualificationRepository.save(qualification);
        directorRepository.save(director);

        alreadySetup = true;
    }
}
