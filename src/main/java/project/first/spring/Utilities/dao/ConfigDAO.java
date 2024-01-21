package project.first.spring.Utilities.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.Utilities.entities.Config;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;

public interface ConfigDAO extends JpaRepository<Config, Integer> {
    Config findByConfigTypeAndConfigCategory(ConfigType configType, ConfigCategory configCategory);
}
