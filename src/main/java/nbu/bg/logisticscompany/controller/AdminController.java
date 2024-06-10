package nbu.bg.logisticscompany.controller;

import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.annotation.security.isAdmin;
import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.entity.Role;
import nbu.bg.logisticscompany.model.entity.UserRole;
import nbu.bg.logisticscompany.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return Arrays.asList(new Role(UserRole.ADMIN.name()), new Role(UserRole.TEACHER.name()),
                new Role(UserRole.STUDENT.name()), new Role(UserRole.PARENT.name()),
                new Role(UserRole.DIRECTOR.name()));
    }

    @isAdmin
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
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

}
