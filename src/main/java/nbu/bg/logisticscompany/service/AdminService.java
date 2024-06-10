package nbu.bg.logisticscompany.service;

import nbu.bg.logisticscompany.model.dto.UserRegisterDto;

public interface AdminService {

    boolean registerUser(UserRegisterDto userRegisterDto);

}
