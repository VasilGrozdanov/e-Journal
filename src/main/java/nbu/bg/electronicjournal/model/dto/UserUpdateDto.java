    package nbu.bg.electronicjournal.model.dto;

    import lombok.*;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UserUpdateDto {
        private String username;
        private String newPassword;
    }
