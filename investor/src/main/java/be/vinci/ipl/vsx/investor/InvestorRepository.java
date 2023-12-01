package be.vinci.ipl.vsx.investor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends CrudRepository<Investor,String> {

}
