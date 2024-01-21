package project.first.spring.Utilities.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;

import java.time.LocalDateTime;

@Table(name = "config")
@Entity(name = "Config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Config {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ConfigType configType;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ConfigCategory configCategory;

    @Column(name = "data")
    private String data;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

