package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator {
    private long id;
    private String surname;
    private String name;
}
