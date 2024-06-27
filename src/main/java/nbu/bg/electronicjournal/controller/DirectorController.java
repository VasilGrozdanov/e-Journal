package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.annotation.security.isAdmin;
import nbu.bg.electronicjournal.annotation.security.isDirector;
import nbu.bg.electronicjournal.model.dto.*;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class DirectorController {

    private DirectorService directorService;
    private TeacherService teacherService;
    private QualificationService qualificationService;
    private SubjectService subjectService;
    private UserService userService;

    @ModelAttribute("grades")
    public List<Grade> getGrades() {
        return directorService.getGrades();
    }

    @ModelAttribute("students")
    public List<Student> getStudents() {
        return directorService.getStudents();
    }

    @ModelAttribute("semesters")
    public Semester[] getSemetsers() {
        return Semester.values();
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
        List<Subject> subjects = subjectService.getAll();
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
    public String updateSubjectPage(@PathVariable("signature") String signature,
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
    public String deleteSubject(@PathVariable("signature") String signature) {
        subjectService.deleteSubject(signature);
        return "redirect:/subjects";
    }

    @ModelAttribute("teachers")
    public List<Teacher> getTeachers(Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("DIRECTOR"))) {
            return List.of();
        }
        Long directorId = getDirectorId(authentication);
        Director director = directorService.getDirector(directorId);
        List<Teacher> allTeachers = teacherService.getAll();
        allTeachers.sort(getTeacherComparator(director));
        return allTeachers;
    }

    @ModelAttribute("subjects")
    public List<Subject> getSubjects() {
        return subjectService.getAll();
    }

    @isDirector
    @GetMapping("/qualifications")
    public String showQualificationsPage(@RequestParam(name = "teacherId", required = false) Long teacherId,
            Model model, Authentication authentication) {
        Long directorId = getDirectorId(authentication);
        if (teacherId == null) {
            return "qualifications";
        }
        Teacher teacher = teacherService.getTeacher(teacherId);
        Qualification qualification = qualificationService.getQualifications(teacher,
                directorService.getDirector(directorId));
        model.addAttribute("qualifications", qualification);
        model.addAttribute("selectedTeacher", teacher);
        return "qualifications";
    }


    @isDirector
    @GetMapping("/qualifications/add")
    public String showAddQualificationPage() {
        return "add-qualification";
    }

    @isDirector
    @PostMapping("/qualifications/add")
    public String addQualification(Authentication authentication,
            @ModelAttribute("qualification") @Valid QualificationDto newQualificationDto, BindingResult result,
            Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "add-qualification";
        }
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            directorService.addQualification(newQualificationDto,
                    directorService.getDirector(directorId).getSchool().getId());
        }
        catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "add-qualification";
        }
        return "redirect:/qualifications";
    }

    @isDirector
    @PutMapping("/qualification/update/teacher={teacherId}&oldSignature={oldSignature}")
    public String updateQualification(Authentication authentication,
            @PathVariable(value = "teacherId", required = false) String teacherId,
            @PathVariable(value = "oldSignature", required = false) String oldSignature,
            @ModelAttribute("newSignature") String newSignature, Model model, HttpServletRequest request) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            Director director = directorService.getDirector(directorId);
            directorService.updateQualification(Long.valueOf(teacherId), oldSignature, newSignature,
                    director.getSchool().getId());
        }
        catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "update-qualification";
        }
        return "redirect:/qualifications";
    }

    @DeleteMapping("/qualification/delete/teacher={teacherId}&subject={signature}")
    @isDirector
    public String deleteQualification(@PathVariable("teacherId") String teacherId,
            @PathVariable("signature") String signature, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            Director director = directorService.getDirector(directorId);
            directorService.removeQualification(Long.valueOf(teacherId), signature, director.getSchool().getId());
        }
        catch (Exception ex) {
            return "redirect:/qualifications";
        }
        return "redirect:/qualifications";
    }


    @isDirector
    @GetMapping("/programs")
    public String showPrograms(Authentication authentication, Model model, HttpServletRequest request) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            model.addAttribute("programs",
                    directorService.getPrograms(directorService.getDirector(directorId).getSchool()));
        }
        catch (Exception ex) {
            return "redirect:/404";
        }
        return "programs";
    }


    @isDirector
    @GetMapping("/program/add")
    public String showAddProgramPage() {
        return "add-program";
    }

    @isDirector
    @PostMapping("/program/add")
    public String addProgram(Authentication authentication, @ModelAttribute("program") @Valid ProgramDto programDto,
            BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", result.getAllErrors());
            return "add-program";
        }
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            directorService.addProgram(programDto, directorService.getDirector(directorId).getSchool().getId());
        }
        catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "add-program";
        }
        return "redirect:/programs";
    }

    @isDirector
    @PutMapping("/program/update/semester={semester}&grade={gradeId}&start={start}&end={end}")
    public String updateProgram(Authentication authentication,
            @PathVariable(value = "semester", required = false) Semester semester,
            @PathVariable(value = "gradeId", required = false) String gradeId,
            @PathVariable(value = "start", required = false) LocalDate start,
            @PathVariable(value = "end", required = false) LocalDate end,
            @ModelAttribute("subjectsTeached") SubjectsTeachedDtoList subjectsTeached, Model model,
            HttpServletRequest request) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {
            Long directorId = userService.getUserIdByUsername(authentication.getName());
            Director director = directorService.getDirector(directorId);
            directorService.updateProgram(
                    new ProgramDto(semester, Long.valueOf(gradeId), start, end, subjectsTeached.getSubjectsTeached()),
                    director.getSchool().getId());
        }
        catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "update-program";
        }
        return "redirect:/programs";
    }

    @DeleteMapping("/program/delete/semester={semester}&grade={gradeId}&start={start}&end={end}")
    public String deleteProgram(@PathVariable(value = "semester", required = false) Semester semester,
            @PathVariable(value = "gradeId", required = false) String gradeId,
            @PathVariable(value = "start", required = false) LocalDate start,
            @PathVariable(value = "end", required = false) LocalDate end) {
        try {
            directorService.removeProgram(semester, Long.valueOf(gradeId), start.atStartOfDay().toLocalDate(),
                    end.atStartOfDay().toLocalDate());
        }
        catch (Exception ex) {
            return "redirect:/404";
        }
        return "redirect:/programs";
    }

    private Comparator<Teacher> getTeacherComparator(Director director) {
        return (teacher1, teacher2) -> {
            boolean teacher1TeachesInSchool = teacherTeachesInSchool(director.getSchool(), teacher1);
            boolean teacher2TeachesInSchool = teacherTeachesInSchool(director.getSchool(), teacher2);
            if (teacher1TeachesInSchool && teacher2TeachesInSchool) {
                return teacher1.getUsername().compareTo(teacher2.getUsername());
            }
            if (teacher1TeachesInSchool) {
                return -1;
            }
            if (teacher2TeachesInSchool) {
                return 1;
            }
            return teacher1.getUsername().compareTo(teacher2.getUsername());
        };
    }

    private boolean teacherTeachesInSchool(School school, Teacher teacher1) {
        return teacher1.getQualifications().stream()
                       .anyMatch(qualification -> qualification.getSchool().equals(school));
    }

    private Long getDirectorId(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        return userService.getUserIdByUsername(authentication.getName());
    }

}
