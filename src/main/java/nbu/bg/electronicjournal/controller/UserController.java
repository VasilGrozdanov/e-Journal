package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.electronicjournal.model.dto.UserUpdateDto;
import nbu.bg.electronicjournal.model.entity.User;
import nbu.bg.electronicjournal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/user/{id}")
    public String updateOrder(@PathVariable("id") String id,
                              @Valid @ModelAttribute UserUpdateDto user,
                              BindingResult result,
                              Model model,
                              @RequestParam String oldPassword,
                              @RequestParam String newPassword,
                              @RequestParam String confirmPassword) {
        if (result.hasErrors()) {
            log.error("Validation errors occurred");
            model.addAttribute("error", "Validation errors occurred");
            populateModelForProfilePage(model, id);
            return "profile";
        }

        // Fetch the current user
        Optional<User> currentUserOpt = userService.getUserById(Long.valueOf(id));
        if (!currentUserOpt.isPresent()) {
            log.error("User not found!");
            model.addAttribute("error", "User not found");
            populateModelForProfilePage(model, id);
            return "profile";
        }

        User currentUser = currentUserOpt.get();

        // Check if the old password is correct
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            log.error("Old password is incorrect");
            model.addAttribute("error", "Old password is incorrect");
            populateModelForProfilePage(model, id);
            return "profile";
        }

        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            log.error("New passwords do not match");
            model.addAttribute("error", "New passwords do not match");
            populateModelForProfilePage(model, id);
            return "profile";
        }

        // Check if the new username is already in use
        if (userService.usernameExists(user.getUsername())) {
            log.error("Username is already taken");
            model.addAttribute("error", "Username is already taken");
            populateModelForProfilePage(model, id);
            return "profile";
        }

        // Update the user's password and username
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        currentUser.setUsername(user.getUsername()); // Update the username if needed
        userService.updateUser(id, user);

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        String currentUsername = getCurrentAuthenticatedUsername();
        Long userId = userService.getUserIdByUsername(currentUsername);
        Optional<User> currentUser = userService.getUserById(userId);

        if (currentUser.isPresent()) {
            model.addAttribute("currUser", currentUser.get());
        } else {
            log.error("User not found!");
            model.addAttribute("error", "User not found");
        }

        return "profile";
    }

    private String getCurrentAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private void populateModelForProfilePage(Model model, String userId) {
        Optional<User> currentUser = userService.getUserById(Long.valueOf(userId));
        currentUser.ifPresent(user -> model.addAttribute("currUser", user));
    }
}
