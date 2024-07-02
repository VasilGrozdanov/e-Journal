package nbu.bg.electronicjournal.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
    private String grade;
    private Long studentId;
    private int studentNumber;
    private String name;
    private String parentName;

}
