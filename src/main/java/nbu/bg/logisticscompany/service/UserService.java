package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.dto.UserUpdateDto;

public interface UserService {

    void updateUser(String id, UserUpdateDto user);

}
