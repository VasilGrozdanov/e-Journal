package nbu.bg.logisticscompany.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbu.bg.logisticscompany.exceptions.InvalidRegistration;
import nbu.bg.logisticscompany.model.dto.UserRegisterDto;
import nbu.bg.logisticscompany.model.entity.Role;
import nbu.bg.logisticscompany.model.entity.User;
import nbu.bg.logisticscompany.repository.UserRepository;
import nbu.bg.logisticscompany.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public boolean registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            throw new InvalidRegistration("Username is already taken!");
        }
        User newClient = User.builder().username(userRegisterDto.getUsername())
                             .password(encoder.encode(userRegisterDto.getPassword()))
                             .roles(Set.of(new Role(userRegisterDto.getRole()))).name(userRegisterDto.getFirstName())
                             .lastName(userRegisterDto.getLastName()).age(userRegisterDto.getAge()).build();
        userRepository.save(newClient);
        return true;
    }

}
