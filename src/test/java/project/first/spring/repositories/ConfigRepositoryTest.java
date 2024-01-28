package project.first.spring.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.entities.Config;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;

import static org.assertj.core.api.Assertions.assertThat;
import static project.first.spring.Constants.TestConstants.MOCK_CONFIG_DATA;

@DataJpaTest
public class ConfigRepositoryTest {

    // By default, all tests decorated with the @DataJpaTest annotation become transactional + rollback at the end of each
    // Read source for this for, for using default db instead of an in-memory one and logging sql queries

    @Autowired
    ConfigDAO configDAO;

    @BeforeEach
    void addData(){
        Config config = Config.builder()
                .configType(ConfigType.CONSTANTS)
                .configCategory(ConfigCategory.HEADER)
                .data(MOCK_CONFIG_DATA)
                .build();
        configDAO.save(config);
    }

    @Test
    void testGetConfigByTypeAndCategory(){
        Config config = configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.HEADER);
        assertThat(config).isNotNull().isInstanceOfAny(Config.class);
        assertThat(config.getData()).isEqualTo(MOCK_CONFIG_DATA);
    }
}
