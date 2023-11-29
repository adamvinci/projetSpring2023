package be.vinci.ipl.vsx.investor;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository investorRepo){
        this.investorRepository = investorRepo;
    }

    public List<Investor> readAll(){
        return (List<Investor>) investorRepository.findAll();
    }
    public Investor getInvestorByUsername(String username) {
        return investorRepository.findById(username).orElse(null);
    }

    public Investor createInvestor(Investor investor) {
        return investorRepository.save(investor);
    }

    public Optional<Investor> updateInvestor(String username, Investor updatedInvestor) {
        Optional<Investor> existingInvestor = investorRepository.findById(username);
        if (existingInvestor.isPresent()) {
            Investor investorToUpdate = existingInvestor.get();
            investorToUpdate.setEmail(updatedInvestor.getEmail());
            investorToUpdate.setFirstname(updatedInvestor.getFirstname());
            investorToUpdate.setLastname(updatedInvestor.getLastname());
            investorToUpdate.setBirthdate(updatedInvestor.getBirthdate());
            return Optional.of(investorRepository.save(investorToUpdate));
        } else {
            return Optional.empty(); // Investor not found
        }
    }

    public boolean updateOne(Investor investor) {
        if (!investorRepository.existsById(investor.getUsername())) return false;
        investorRepository.save(investor);
        return true;
    }

    public void deleteInvestor(String username) {
        investorRepository.deleteById(username);
    }
}
