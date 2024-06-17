package nbu.bg.logisticscompany.controller;

import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.service.SubjectService;
import nbu.bg.logisticscompany.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class PageController {
    private final UserService userService;
    private final SubjectService subjectService;

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
            SubjectDto subject = subjectService.getSubject(signature);
            model.addAttribute("subject", subject);
        }
        catch (NumberFormatException e) {
            return "subjects";
        }
        return "update-subject";
    }
}
