package nbu.bg.logisticscompany.controller;

import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.annotation.security.isAdmin;
import nbu.bg.logisticscompany.annotation.security.isDirector;
import nbu.bg.logisticscompany.model.dto.StudentEnrollDto;
import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.model.entity.Grade;
import nbu.bg.logisticscompany.model.entity.Student;
import nbu.bg.logisticscompany.model.entity.Subject;
import nbu.bg.logisticscompany.service.DirectorService;
import nbu.bg.logisticscompany.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class DirectorController {

    private DirectorService directorService;
    private SubjectService subjectService;

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

    @isDirector
    @GetMapping("/subject/create")
    public String showCreateSubjectPage() {
        return "create-subject";
    }

    @isDirector
    @GetMapping("/subjects")
    public String showSubjectsPage(Model model) {
        List<Subject> subjects = subjectService.getSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects";
    }

    @isDirector
    @PostMapping("/subject/create")
    public String createSubject(@ModelAttribute("subject") @Valid SubjectDto newSubjectDto, BindingResult result,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            return "redirect:/subjects";
        }
        try {
            directorService.createSubject(newSubjectDto);
        }
        catch (Exception ex) {
            return "redirect:/404";
        }
        return "redirect:/subjects";
    }

    @isDirector
    @PutMapping("/subject/update/{signature}")
    public String showCreateSubjectPage(@PathVariable("signature") String signature,
            @Valid @ModelAttribute SubjectDto updatedSubjectDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-subject";
        }
        try {
            directorService.updateSubject(updatedSubjectDto);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/subjects";
    }


    @DeleteMapping("/subject/delete/{signature}")
    @isAdmin
    public String deleteOffice(@PathVariable("signature") String signature) {
        subjectService.deleteSubject(signature);
        return "redirect:/subjects";
    }

}
