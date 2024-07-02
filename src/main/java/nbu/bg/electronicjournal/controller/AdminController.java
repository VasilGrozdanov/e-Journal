package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.annotation.security.isAdmin;
import nbu.bg.electronicjournal.model.dto.AvgMarkDto;
import nbu.bg.electronicjournal.model.dto.SchoolRegisterDto;
import nbu.bg.electronicjournal.model.dto.TotalAbsencesDto;
import nbu.bg.electronicjournal.model.dto.UserRegisterDto;
import nbu.bg.electronicjournal.model.entity.Role;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.model.entity.UserRole;
import nbu.bg.electronicjournal.service.AdminService;
import nbu.bg.electronicjournal.service.SchoolService;
import nbu.bg.electronicjournal.service.SubjectService;
import nbu.bg.electronicjournal.utilities.AdminGroupingEntity;
import nbu.bg.electronicjournal.utilities.StatisticsGroupingTypeAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final SubjectService subjectService;
    private final SchoolService schoolService;

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return Arrays.asList(new Role(UserRole.ADMIN.name()), new Role(UserRole.TEACHER.name()),
                new Role(UserRole.STUDENT.name()), new Role(UserRole.PARENT.name()),
                new Role(UserRole.DIRECTOR.name()));
    }

    @ModelAttribute("schools")
    public List<School> getSchools() {
        return adminService.getSchools();
    }

    @ModelAttribute("subjects")
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @isAdmin
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @isAdmin
    @GetMapping("/registerSchool")
    public String showRegisterSchoolPage() {
        return "register-school";
    }

    @isAdmin
    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserRegisterDto userDto,
            HttpServletRequest request) {

        try {
            adminService.registerUser(userDto);
        }
        catch (Exception ex) {
            ModelAndView mav = new ModelAndView("register", "user", userDto);
            mav.addObject("errorMessage", ex.getMessage());
            return mav;
        }
        return new ModelAndView("login", "user", userDto);
    }

    @isAdmin
    @PostMapping("/registerSchool")
    public ModelAndView registerSchool(@ModelAttribute("school") @Valid SchoolRegisterDto schoolDto,
            HttpServletRequest request) {

        try {
            adminService.registerSchool(schoolDto);
        }
        catch (Exception ex) {
            ModelAndView mav = new ModelAndView("registerSchool", "school", schoolDto);
            mav.addObject("errorMessage", ex.getMessage());
            return mav;
        }
        return new ModelAndView("index");
    }

    @isAdmin
    @GetMapping("/statistics/admin/marks")
    public String marksStatisticPage(
            @RequestParam(value = "selectedGroup", required = false) StatisticsGroupingTypeAdmin selectedGroup,
            @RequestParam(value = "specificSubject", required = false) String specificSubjectSignature,
            @RequestParam(value = "specificSchool", required = false) String specificSchoolId, Model model,
            HttpServletRequest request) {
        model.addAttribute("groups", StatisticsGroupingTypeAdmin.values());
        if (selectedGroup == null) {
            return "statistics-admin-marks";
        }
        model.addAttribute("selectedGroup", selectedGroup);
        AdminGroupingEntity entity = getAdminGroupingEntity(selectedGroup, specificSubjectSignature, specificSchoolId);
        try {
            List<AvgMarkDto<AdminGroupingEntity>> avgGrades = adminService.getAvgGrade(selectedGroup,
                    Optional.ofNullable(entity));
            model.addAttribute("gradesPair", avgGrades);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "statistics-admin-marks";
    }

    @isAdmin
    @GetMapping("/statistics/admin/absences")
    public String absencesStatisticPage(
            @RequestParam(value = "selectedGroup", required = false) StatisticsGroupingTypeAdmin selectedGroup,
            @RequestParam(value = "specificSubject", required = false) String specificSubjectSignature,
            @RequestParam(value = "specificSchool", required = false) String specificSchoolId, Model model,
            HttpServletRequest request) {
        model.addAttribute("groups", StatisticsGroupingTypeAdmin.values());
        if (selectedGroup == null) {
            return "statistics-admin-absences";
        }
        model.addAttribute("selectedGroup", selectedGroup);
        AdminGroupingEntity entity = getAdminGroupingEntity(selectedGroup, specificSubjectSignature, specificSchoolId);
        try {
            List<TotalAbsencesDto<AdminGroupingEntity>> totalAbsences = adminService.getTotalAbsences(selectedGroup,
                    Optional.ofNullable(entity));
            model.addAttribute("totalAbsences", totalAbsences);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "statistics-admin-absences";
    }

    private AdminGroupingEntity getAdminGroupingEntity(StatisticsGroupingTypeAdmin selectedGroup,
            String specificSubjectSignature, String specificSchoolId) {
        AdminGroupingEntity entity = null;
        switch (selectedGroup) {
            case SUBJECT:
                entity = adminService.getSubjectOptional(specificSubjectSignature).orElse(null);
                break;
            case SCHOOL:
                entity = adminService.getSchoolOptional(Long.valueOf(specificSchoolId)).orElse(null);
                break;
        }
        return entity;
    }
}
