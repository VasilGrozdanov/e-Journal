package nbu.bg.logisticscompany.controller;

import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.annotation.security.isDirector;
import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class DirectorController {

    private DirectorService directorService;

    @ModelAttribute("grades")
    public List<Grade> getGrades() {
        return directorService.getGrades();
    }

    @ModelAttribute("students")
    public List<Student> getStudents() {
        return directorService.getStudents();
    }

    @isDirector
    @GetMapping("/enrollStudent")
    public String showEnrollStudentPage() {
        return "enroll-student";
    }

    @isDirector
    @PostMapping("/enrollStudent")
    public ModelAndView enrollStudent(@ModelAttribute("student") @Valid StudentEnrollDto studentEnrollDto,
            HttpServletRequest request) {

        try {
            directorService.enrollStudent(studentEnrollDto);
        }
        catch (Exception ex) {
            ModelAndView mav = new ModelAndView("enroll-student", "student", studentEnrollDto);
            mav.addObject("errorMessage", ex.getMessage());
            return mav;
        }
        return new ModelAndView("index");
    }
}
