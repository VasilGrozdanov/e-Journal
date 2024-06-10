package nbu.bg.logisticscompany.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.logisticscompany.exceptions.InvalidRegistration;
import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.dto.UserUpdateDto;
import nbu.bg.logisticscompany.model.entity.Admin;
import nbu.bg.logisticscompany.model.entity.Role;
import nbu.bg.logisticscompany.model.entity.User;
import nbu.bg.logisticscompany.model.entity.UserRole;
import nbu.bg.logisticscompany.repository.ClientRepository;
import nbu.bg.logisticscompany.repository.UserRepository;
import nbu.bg.logisticscompany.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public void updateUser(String id, UserUpdateDto user) {
        Optional<User> byId = userRepository.findById(Long.valueOf(id));
        if (byId.isEmpty()) {
            log.error("User with ID: " + id + " does not exist!");
            return;
        }
        User currUser = byId.get();
        if (user.getNewPassword() != null && !user.getNewPassword().isBlank()) {
            currUser.setPassword(encoder.encode(user.getNewPassword()));
        }

        if (user.getUsername() != null && !user.getUsername().isBlank()) {
            currUser.setUsername(user.getUsername());
        }
        userRepository.save(currUser);
    }

}
