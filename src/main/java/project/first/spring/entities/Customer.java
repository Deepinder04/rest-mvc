package project.first.spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ValueGenerationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar",updatable = false,nullable = false)
    private UUID id;

    @NotEmpty
    @Size(max = 50)
    @Column(length = 50)
    private String customerName;

    @Version
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;
}