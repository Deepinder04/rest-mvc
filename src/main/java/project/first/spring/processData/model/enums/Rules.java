package project.first.spring.processData.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rules {
    ASCENDING("The input data is ordered in ascending order", "ASCENDING"),
    DESCENDING("The input data is ordered in descending order", "DESCENDING"),
    GROUP_COMMON_STRINGS("All the strings with exact same characters are grouped together in a list", "GROUP_COMMON_STRINGS");
    private String description;
    private String name;
}
