package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.entity.Director;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.service.DirectorService;
import nbu.bg.electronicjournal.service.SubjectService;
import nbu.bg.electronicjournal.service.TeacherService;
import nbu.bg.electronicjournal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class PageController {
    private final UserService userService;
    private final DirectorService directorService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

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

        //        try {
        //        }
        //        catch (NumberFormatException e) {
        //            return "qualifications";
        //        }
        Set<Subject> alreadyQualifiedSubjects = new HashSet<>();
        Long userIdByUsername = userService.getUserIdByUsername(authentication.getName());
        Director director = directorService.getDirector(userIdByUsername);
        teacherService.getTeacherWithQualifications(Long.valueOf(teacherId)).getQualifications()
                      .forEach(qualification -> {
                          if (qualification.getSchool().getId().equals(director.getSchool().getId())) {
                              alreadyQualifiedSubjects.addAll(qualification.getSubjects());
                          }
                      });
        List<Subject> subjects = subjectService.getSubjects();
        subjects.removeAll(alreadyQualifiedSubjects);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjects", subjects);
        model.addAttribute("oldSubject", subjectService.getSubject(oldSignature));
        return "update-qualification";
    }
}
