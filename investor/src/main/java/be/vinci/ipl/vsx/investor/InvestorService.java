package be.vinci.ipl.vsx.investor;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;


    /**
     * Constructor for InvestorService.
     *
     * @param investorRepo The repository for handling investor data.
     */
    @Autowired
    public InvestorService(InvestorRepository investorRepo){
        this.investorRepository = investorRepo;
    }

    public List<Investor> readAll(){
        return (List<Investor>) investorRepository.findAll();
    }

    /**
     * Retrieve information about a specific investor by username.
     *
     * @param username The username of the investor.
     * @return Investor object containing information about the investor.
     */
    public Investor getInvestorByUsername(String username) {
        return investorRepository.findById(username).orElse(null);
    }


    /**
     * Create a new investor in the system.
     *
     * @param investor The investor data to be created.
     * @return The created investor, or null if the investor already exists.
     */
    public Investor createInvestor(Investor investor) {
        if(investorRepository.existsById(investor.getUsername())){
            return null;
        }
        return investorRepository.save(investor);
    }


    /**
     * Update information about a specific investor.
     *
     * @param investor The updated investor data.
     * @return True if the investor is updated, false otherwise.
     */
    public boolean updateOne(Investor investor) {
        if (!investorRepository.existsById(investor.getUsername())) return false;
        investorRepository.save(investor);
        return true;
    }


    /**
     * Delete a specific investor by username.
     *
     * @param username The username of the investor to be deleted.
     */
    public void deleteInvestor(String username) {
        investorRepository.deleteById(username);
    }
}
