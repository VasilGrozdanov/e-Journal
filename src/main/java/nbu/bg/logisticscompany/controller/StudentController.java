package nbu.bg.logisticscompany.controller;


import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.entity.Absence;
import nbu.bg.logisticscompany.service.StudentService;
import nbu.bg.logisticscompany.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;
    private UserService userService;


    @GetMapping("/absences")
    public String showRegisterSchoolPage(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long studentId = userService.getUserIdByUsername(authentication.getName());
            List<Absence> absences = studentService.getAbsences(studentId);
            model.addAttribute("absences", absences);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "student-absences";
    }

}
