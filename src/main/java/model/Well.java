package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Well {
    private Long id;
    private String equipment;
    private double productivity;
    private Long wellClusterId;

    public Well(String equipment, double productivity, Long wellClusterId) {
        this.equipment = equipment;
        this.productivity = productivity;
        this.wellClusterId = wellClusterId;
    }
}