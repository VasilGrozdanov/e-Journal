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

    public Role(String name) {
        switch (name) {
            case "Admin":
                this.name = UserRole.ADMIN;
                break;
            case "Teacher":
                this.name = UserRole.TEACHER;
                break;
            case "Student":
                this.name = UserRole.STUDENT;
                break;
            case "Parent":
                this.name = UserRole.PARENT;
                break;
            case "Director":
                this.name = UserRole.DIRECTOR;
                break;
            default:
                throw new IllegalArgumentException("Invalid user role");
        }
    }
}
