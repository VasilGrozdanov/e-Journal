package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.UserUpdateDto;
import nbu.bg.electronicjournal.model.entity.User;

import java.util.Optional;

public interface UserService {

    void updateUser(String id, UserUpdateDto user);

    Long getUserIdByUsername(String username);

    Optional<User> getUserById(Long id); // Add this method

}
