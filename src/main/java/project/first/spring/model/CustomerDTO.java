package project.first.spring.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;

    @NotEmpty
    @Size(max = 50)
    @Column(length = 50)
    private String customerName;
    private String email;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;
}
