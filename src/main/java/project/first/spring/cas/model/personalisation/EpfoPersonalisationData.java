package project.first.spring.cas.model.personalisation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpfoPersonalisationData {
     @JsonProperty("uANMapped")
     private boolean uanMapped;
     @JsonProperty("fetchStatusPF")
     private String fetchStatus;
     @JsonProperty("employmentStatusPF")
     private String employmentStatus;
     @JsonProperty("dataRefreshedDatePF")
     private long dataRefreshedDate;
     @JsonProperty("firstDataFetchDatePF")
     private long firstDataFetchDate;
}
