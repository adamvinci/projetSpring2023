package be.vinci.ipl.vsx.investor;

import be.vinci.ipl.vsx.investor.models.Credentials;
import be.vinci.ipl.vsx.investor.models.Investor;
import be.vinci.ipl.vsx.investor.models.InvestorWithPassword;
import be.vinci.ipl.vsx.investor.repositories.AuthenticationProxy;
import be.vinci.ipl.vsx.investor.repositories.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;
    private final AuthenticationProxy authenticationProxy;


    /**
     * Constructor for InvestorService.
     *
     * @param investorRepo        The repository for handling investor data.
     * @param authenticationProxy
     */
    @Autowired
    public InvestorService(InvestorRepository investorRepo, AuthenticationProxy authenticationProxy){
        this.investorRepository = investorRepo;
        this.authenticationProxy = authenticationProxy;
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
    public boolean createInvestor(InvestorWithPassword  investor) {
        if(investorRepository.existsById(investor.getInvestor().getUsername())){
            return false;
        }

        Credentials cerdential = new Credentials();
        cerdential.setUsername(investor.getInvestor().getUsername());
        cerdential.setPassword(investor.getPassword());
        authenticationProxy.createCredentials(cerdential.getUsername(),cerdential);

        investorRepository.save(investor.getInvestor());
        return true;
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
    public boolean deleteInvestor(String username) {
        if (!investorRepository.existsById(username)){
            return false;
        }

        authenticationProxy.deleteCredentials(username);

        investorRepository.deleteById(username);
        return true;
    }
}
