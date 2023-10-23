package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator {
    private Long id;
    private String surname;
    private String name;

    public Operator(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }
}