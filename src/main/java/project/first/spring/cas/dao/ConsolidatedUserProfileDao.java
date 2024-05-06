package project.first.spring.cas.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.first.spring.cas.entities.ConsolidatedUserProfile;

import java.util.Optional;

public interface ConsolidatedUserProfileDao extends MongoRepository<ConsolidatedUserProfile, String> {
    Optional<ConsolidatedUserProfile> findByMemberUid(String memberUid);
}
