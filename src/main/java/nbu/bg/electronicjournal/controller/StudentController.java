package nbu.bg.electronicjournal.controller;


import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.service.StudentService;
import nbu.bg.electronicjournal.service.UserService;
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
            // Calculate total number of absences
            double totalAbsences = studentService.calculateTotalAbsences(absences);
            model.addAttribute("totalAbsences", totalAbsences);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "student-absences";
    }

    @GetMapping("/grades")
    public String showGradesPage(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long studentId = userService.getUserIdByUsername(authentication.getName());
            List<Evaluates> grades = studentService.getGrades(studentId);
            model.addAttribute("grades", grades);
            // Calculate GPA
            double gpa = studentService.calculateGPA(grades);
            model.addAttribute("gpa", gpa);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "student-grades";
    }
}
