package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.electronicjournal.model.dto.UserUpdateDto;
import nbu.bg.electronicjournal.model.entity.User;
import nbu.bg.electronicjournal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.util.Optional; // Import for Optional

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PutMapping("/user/{id}")
    public String updateOrder(@PathVariable("id") String id, @Valid @ModelAttribute UserUpdateDto user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("has errors");
            return "profile";
        }
        userService.updateUser(id, user);
        return "redirect:/logout";
    }

    /*@GetMapping("/profile")
    public String profilePage(Model model) {
        return "profile";
    }
    */
    @GetMapping("/profile")
    public String profilePage(Model model) {
        // Assuming you have a method to get the current authenticated user's username
        String currentUsername = getCurrentAuthenticatedUsername();
        Long userId = userService.getUserIdByUsername(currentUsername);
        Optional<User> currentUser = userService.getUserById(userId);

        if (currentUser.isPresent()) {
            model.addAttribute("currUser", currentUser.get());
        } else {
            // handle the case where user is not found
            log.error("User not found!");
        }

        return "profile";
    }

    // Method to get the currently authenticated user's username
    private String getCurrentAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


}
