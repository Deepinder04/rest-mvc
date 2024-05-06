package project.first.spring.cas.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import project.first.spring.cas.entities.ConsolidatePortfolio;

public interface ConsolidatePortfolioDao extends MongoRepository<ConsolidatePortfolio, Long> {

        @Query(value = "{" +
                "'memberUid': { $exists: true }, " +
                "$expr: { $and: [ " +
                "{ $gte: [{ $toLong: '$memberUid' }, ?0] }, " +
                "{ $lte: [{ $toLong: '$memberUid' }, ?1] } " +
                "] } " +
                "}", sort = "{ 'memberUid': 1 }")
        Page<ConsolidatePortfolio> findByMemberUidGreaterThanEqualAndMemberUidLessThanEqualOrderByMemberUid(long startingMemberUid, long endMemberUid, Pageable pageable);


        @Query(value = "{" +
                "'memberUid': { $exists: true }, " +
                "$expr: { $and: [ " +
                "{ $gte: [{ $toLong: '$memberUid' }, ?0] }] } " +
                "}", sort = "{ 'memberUid': 1 }")
        Page<ConsolidatePortfolio> findByMemberUidGreaterThanEqualOrderByMemberUid(long startingMemberUid, Pageable pageable);
}
