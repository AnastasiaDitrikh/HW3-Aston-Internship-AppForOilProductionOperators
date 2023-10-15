package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location{
    private Long id;
    private Float lat;
    private Float lon;
}
