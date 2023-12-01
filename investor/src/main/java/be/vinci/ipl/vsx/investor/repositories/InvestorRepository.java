package be.vinci.ipl.vsx.investor.repositories;

import be.vinci.ipl.vsx.investor.models.Investor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends CrudRepository<Investor,String> {

}
