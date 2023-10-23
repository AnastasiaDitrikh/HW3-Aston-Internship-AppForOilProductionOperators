package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellCluster {
    private Long id;

    private Long number;
    private Long locationId;
    private Long operatorId;

    public WellCluster(Long number, Long locationId, Long operatorId) {
        this.number = number;
        this.locationId = locationId;
        this.operatorId = operatorId;
    }
}