package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Well {
    private Long id;
    private  String equipment;
    private double productivity;
    private WellCluster wellCluster;

    public Well(String equipment, double productivity, WellCluster wellCluster) {
        this.equipment = equipment;
        this.productivity = productivity;
        this.wellCluster = wellCluster;
    }
}