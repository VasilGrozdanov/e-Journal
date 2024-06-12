package nbu.bg.logisticscompany.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole name;

    public Role(UserRole name) {
        this.name = name;
    }

    public Role(String name) {
        switch (name.toLowerCase()) {
            case "admin":
                this.name = UserRole.ADMIN;
                break;
            case "teacher":
                this.name = UserRole.TEACHER;
                break;
            case "student":
                this.name = UserRole.STUDENT;
                break;
            case "parent":
                this.name = UserRole.PARENT;
                break;
            case "director":
                this.name = UserRole.DIRECTOR;
                break;
            default:
                throw new IllegalArgumentException("Invalid user role");
        }
    }
}
