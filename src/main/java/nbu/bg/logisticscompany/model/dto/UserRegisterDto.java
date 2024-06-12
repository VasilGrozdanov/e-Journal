package nbu.bg.logisticscompany.model.dto;

import lombok.*;
import nbu.bg.logisticscompany.annotation.PasswordMatches;
import nbu.bg.logisticscompany.model.entity.UserRole;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@PasswordMatches
public class UserRegisterDto {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    private String matchingPassword;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    private int age;
    @NotNull
    private UserRole role;

    @Nullable
    private Integer studentNumber;

    @Nullable
    private SchoolRegisterDto directorSchool;

}