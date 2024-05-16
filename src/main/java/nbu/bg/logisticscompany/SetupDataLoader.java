package nbu.bg.logisticscompany;

import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.entity.Role;
import nbu.bg.logisticscompany.model.entity.User;
import nbu.bg.logisticscompany.repository.ClientRepository;
import nbu.bg.logisticscompany.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;


@Component
@AllArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    static boolean alreadySetup = false;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        String test = "Test";

        User admin = User.builder().roles(new HashSet<>(List.of(new Role("Admin")))).username("admin")
                         .password(passwordEncoder.encode("admin")).name(test).lastName(test).age(30).build();


        User teacher = User.builder().roles(new HashSet<>(List.of(new Role("Teacher")))).username("teacher")
                           .password(passwordEncoder.encode("teacher")).name(test).lastName(test).age(60).build();

        User student = User.builder().roles(new HashSet<>(List.of(new Role("Student")))).username("student")
                           .password(passwordEncoder.encode("student")).name(test).lastName(test).age(14).build();

        User parent = User.builder().roles(new HashSet<>(List.of(new Role("Parent")))).username("parent")
                          .password(passwordEncoder.encode("parent")).name(test).lastName(test).age(35).build();
        User director = User.builder().roles(new HashSet<>(List.of(new Role("Director")))).username("director")
                            .password(passwordEncoder.encode("director")).name(test).lastName(test).age(55).build();


        // persist users
        userRepository.save(teacher);
        userRepository.save(student);
        userRepository.save(parent);
        userRepository.save(director);

        userRepository.save(admin);

        alreadySetup = true;
    }
}
