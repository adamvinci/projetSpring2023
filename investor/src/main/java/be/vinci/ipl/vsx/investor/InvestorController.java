package be.vinci.ipl.vsx.investor;

import be.vinci.ipl.vsx.investor.models.Investor;
import be.vinci.ipl.vsx.investor.models.InvestorWithPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class  InvestorController {

    private InvestorService investorService;

    /**
     * Constructor for InvestorController.
     *
     * @param investorService The service handling investor-related business logic.
     */
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    /**
     * Endpoint to create a new investor in the system.
     *
     * @param investor The investor data to be created.
     * @return ResponseEntity containing the result of the creation operation.
     */
    @PostMapping("/investor/{username}")
    public ResponseEntity<?> createInvestor(@PathVariable String username ,@RequestBody InvestorWithPassword investor) {


            if(!investor.getInvestor().getUsername().equals(username)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("les donnees de l'investisseur sont invalides");
            }

            Optional<Investor> investorExists = Optional.ofNullable(investorService.getInvestorByUsername(investor.getInvestor().getUsername()));
            if(investorExists.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("L'investisseur existe deja");

            }
            Boolean createdInvestor = investorService.createInvestor(investor);


            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestor );

    }


    /**
     * Endpoint to retrieve information about a specific investor.
     *
     * @param username The username of the investor.
     * @return Investor object containing information about the investor.
     */
    @GetMapping("/investor/{username}")
    public  ResponseEntity<Investor> readOne(@PathVariable String username) {
        Investor investor = investorService.getInvestorByUsername(username);
        if (investor == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        };
        return ResponseEntity.ok(investor);
    }

    @GetMapping("/investor/readAll")
    public ResponseEntity<List<Investor>> readAll() {
        List<Investor> investors = investorService.readAll();
        return new ResponseEntity<>(investors, HttpStatus.OK);
    }



    /**
     * Endpoint to update information about a specific investor.
     *
     * @param username The username of the investor.
     * @param investor The updated investor data.
     */
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


    /**
     * Endpoint to delete a specific investor.
     *
     * @param username The username of the investor to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
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