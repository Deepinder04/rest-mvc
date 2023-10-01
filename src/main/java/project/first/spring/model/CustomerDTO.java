package project.first.spring.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;

    @NotEmpty
    private String customerName;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;
}
