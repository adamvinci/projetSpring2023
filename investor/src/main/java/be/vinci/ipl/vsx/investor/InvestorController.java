package be.vinci.ipl.vsx.investor;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class InvestorController {

    private InvestorService investorService;


    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }


    @PostMapping("/investor")
    public ResponseEntity<?> createInvestor(@RequestBody Investor investor) {
        try {
            Optional<Investor> investorExists = Optional.ofNullable(investorService.getInvestorByUsername(investor.getUsername()));
            if(investorExists.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("L'investisseur existe deja");

            }
            Investor createdInvestor = investorService.createInvestor(investor);


            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestor );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("les donnees de l'investisseur sont invalides");
        }
    }



    @GetMapping("/investor/{username}")
    public  Investor readOne(@PathVariable String username) {
        Investor user = investorService.getInvestorByUsername(username);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        };
        return user;
    }

    @GetMapping("/investor/readAll")
    public ResponseEntity<List<Investor>> readAll() {
        List<Investor> investors = investorService.readAll();
        return new ResponseEntity<>(investors, HttpStatus.OK);
    }




    @PutMapping("/investor/{username}")
    public void updateOne(@PathVariable String username, @RequestBody Investor investor) {

        if(!investor.getUsername().equals(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else if (investor.getEmail() == null  ||
                investor.getLastname() == null || investor.getFirstname() == null || investor.getBirthdate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        boolean found = investorService.updateOne(investor);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("investor/{username}")
    public ResponseEntity<?> deleteInvestor(@PathVariable String username) {

        Optional<Investor> deletedInvestor = Optional.ofNullable(investorService.getInvestorByUsername(username));

        if(!deletedInvestor.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("l'investisseur n'as pas pu etre trouve");
        }else{
            investorService.deleteInvestor(username);

        }

        return ResponseEntity.ok("Investor deleted successfully");
    }

}