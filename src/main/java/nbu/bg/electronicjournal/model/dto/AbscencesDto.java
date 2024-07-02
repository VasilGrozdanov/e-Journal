package nbu.bg.electronicjournal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbscencesDto {
    private int whole;
    private int partial;

    public AbscencesDto(double total) {
        this.whole = (int) total / 3;
        this.partial = (int) total - whole;
    }

    @Override
    public String toString() {
        return "" + (whole > 0 ? String.format("%d", whole) : "") +
               (partial > 0 ? String.format(" %d/3", partial) : "");
    }
}
