package project.first.spring.cas.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.first.spring.cas.entities.InvestmentLedger;

public interface InvestmentLedgerDao extends MongoRepository<InvestmentLedger, String> {

    InvestmentLedger findByMemberUidAndYearAndModule(String memberUid, int year, String module);
}
