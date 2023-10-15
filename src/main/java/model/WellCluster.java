package model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellCluster {
    private Long id;

    private int number;
    private Location locality;
    private Operator operator;

    public WellCluster(Location locality, Operator operator, int number) {
        this.locality = locality;
        this.operator = operator;
        this.number = number;
    }
}