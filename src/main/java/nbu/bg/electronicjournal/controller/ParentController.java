package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Absence;
import nbu.bg.electronicjournal.model.entity.Evaluates;
import nbu.bg.electronicjournal.model.entity.Student;
import nbu.bg.electronicjournal.service.ParentService;
import nbu.bg.electronicjournal.service.StudentService;
import nbu.bg.electronicjournal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor

public class ParentController {
    private StudentService studentService;
    private UserService userService;
    private ParentService parentService;

    @GetMapping("/gradesOfKids")
    public String showGradesPages(@RequestParam(name = "studentId", required = false) Long studentId,
            Authentication authentication, Model model) {

        if (authentication == null) {
            throw new RuntimeException();
        }

        try {
            Long parentId = userService.getUserIdByUsername(authentication.getName());
            Set<Student> students = parentService.getKids(parentId);
            model.addAttribute("kids", students);
            if (studentId == null) {
                return "kids-grades";
            }
            Student student = studentService.getStudent(studentId);

            if (!students.contains(student)) {
                throw new RuntimeException("Kid not found");
            }

            List<Evaluates> grades = studentService.getGrades(studentId);
            model.addAttribute("selectedStudent", student);
            model.addAttribute("grades", grades);
            // Calculate GPA
            double gpa = studentService.calculateGPA(grades);
            model.addAttribute("gpa", gpa);


        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "kids-grades";
    }

    @GetMapping("/absenceOfKids")
    public String showAbsencePages(@RequestParam(name = "studentId", required = false) Long studentId,
                                  Authentication authentication, Model model) {

        if (authentication == null) {
            throw new RuntimeException();
        }

        try {
            Long parentId = userService.getUserIdByUsername(authentication.getName());
            Set<Student> students = parentService.getKids(parentId);
            model.addAttribute("kids", students);
            if (studentId == null) {
                return "kids-absence";
            }
            Student student = studentService.getStudent(studentId);

            if (!students.contains(student)) {
                throw new RuntimeException("Kid not found");
            }

            List<Absence> absence = studentService.getAbsences(studentId);
            model.addAttribute("selectedStudent", student);
            model.addAttribute("absences", absence);
            // Calculate total number of absences
            double totalAbsences = studentService.calculateTotalAbsences(absence);
            model.addAttribute("totalAbsences", totalAbsences);


        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "kids-absence";
    }
}