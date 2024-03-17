package project.first.spring.processData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.processData.model.entities.RuleParameter;

public interface RuleParameterDAO extends JpaRepository<RuleParameter, Long> {
}
