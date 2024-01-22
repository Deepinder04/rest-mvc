package project.first.spring.flows.home.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeScreenDto {
    private String header;
    private String footer;
    private String body;
    private String collections;
}
