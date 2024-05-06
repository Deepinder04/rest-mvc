package project.first.spring.cas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class BaseDocumentEntity implements Serializable {
    @Id
    private String id;
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedAt;
}
