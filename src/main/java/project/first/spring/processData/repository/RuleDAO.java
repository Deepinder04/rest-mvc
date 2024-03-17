package project.first.spring.processData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.first.spring.processData.model.entities.Rule;
import project.first.spring.processData.model.enums.RuleType;
import project.first.spring.processData.model.enums.Rules;

import java.util.List;

public interface RuleDAO extends JpaRepository<Rule, Long> {
    @Query("SELECT r.name FROM Rule r WHERE r.ruleType = :ruleType AND r.enabled = true")
    List<String> findNameByRuleTypeAndEnabledTrue(RuleType ruleType);

}
