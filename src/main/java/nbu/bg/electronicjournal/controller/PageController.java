package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.QualificationDto;
import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.dto.SubjectsTeachedDtoList;
import nbu.bg.electronicjournal.model.entity.Director;
import nbu.bg.electronicjournal.model.entity.Program;
import nbu.bg.electronicjournal.model.entity.Semester;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PageController {
    private final UserService userService;
    private final DirectorService directorService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final ProgramService programService;

    @RequestMapping({ "/index", "/", "/home", "*" })
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/subject/update/{signature}")
    public String showUpdateSubject(@PathVariable("signature") String signature, Model model) throws Exception {

        try {
            SubjectDto subject = subjectService.getSubjectDto(signature);
            model.addAttribute("subject", subject);
        }
        catch (NumberFormatException e) {
            return "subjects";
        }
        return "update-subject";
    }

    @GetMapping("/qualification/update/teacher={teacherId}&oldSignature={oldSignature}")
    public String showUpdateQualification(Authentication authentication,
            @PathVariable(value = "teacherId", required = false) String teacherId,
            @PathVariable(value = "oldSignature", required = false) String oldSignature, Model model) {
        Set<Subject> alreadyQualifiedSubjects = new HashSet<>();
        Long userIdByUsername = userService.getUserIdByUsername(authentication.getName());
        Director director = directorService.getDirector(userIdByUsername);
        teacherService.getTeacherWithQualifications(Long.valueOf(teacherId)).getQualifications()
                      .forEach(qualification -> {
                          if (qualification.getSchool().getId().equals(director.getSchool().getId())) {
                              alreadyQualifiedSubjects.addAll(qualification.getSubjects());
                          }
                      });
        List<Subject> subjects = subjectService.getAll();
        subjects.removeAll(alreadyQualifiedSubjects);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjects", subjects);
        model.addAttribute("oldSubject", subjectService.getSubject(oldSignature));
        return "update-qualification";
    }

    @GetMapping("/program/update/semester={semester}&grade={gradeId}&start={start}&end={end}")
    public String updateProgram(@PathVariable(value = "semester", required = false) Semester semester,
            @PathVariable(value = "gradeId", required = false) String gradeId,
            @PathVariable(value = "start", required = false) LocalDate start,
            @PathVariable(value = "end", required = false) LocalDate end, Model model, HttpServletRequest request) {
        Program program = programService.getProgram(semester, Long.valueOf(gradeId), start.atStartOfDay().toLocalDate(),
                end.atStartOfDay().toLocalDate());
        model.addAttribute("program", program);
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("subjectsTeached", new SubjectsTeachedDtoList(
                programService.getProgram(semester, Long.valueOf(gradeId), start, end).getSubjectsTeached().stream()
                              .map(subjectTeachedBy -> new QualificationDto(subjectTeachedBy.getTeacher().getId(),
                                      subjectTeachedBy.getSubject().getSignature())).collect(Collectors.toList())));
        return "update-program";
    }
}
