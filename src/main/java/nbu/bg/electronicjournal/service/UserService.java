package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.dto.UserUpdateDto;

public interface UserService {

    void updateUser(String id, UserUpdateDto user);

    Long getUserIdByUsername(String username);

}
