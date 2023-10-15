package model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellCluster {
    private long idCluster;
    private Location locality;
    private Operator operator;
}
