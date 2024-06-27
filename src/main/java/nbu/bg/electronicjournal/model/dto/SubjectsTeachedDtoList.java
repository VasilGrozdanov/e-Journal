package nbu.bg.electronicjournal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsTeachedDtoList {
    private List<QualificationDto> subjectsTeached;
}
