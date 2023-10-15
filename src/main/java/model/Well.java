package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Well {
    private long id;
    private  String equipment;
    private double productivity;
    private WellCluster wellCluster;
}
