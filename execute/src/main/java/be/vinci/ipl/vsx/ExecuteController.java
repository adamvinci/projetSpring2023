package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling execute-related HTTP requests.
 */
@RestController
public class ExecuteController {
    private final ExecuteService executeService;

  public ExecuteController(ExecuteService executeService) {
    this.executeService = executeService;
  }

  /**
   * Endpoint to execute a financial transaction between a seller and a buyer for a specific stock.
   * @param ticker The Stock alphanumeric code
   * @param seller The username of the investor selling the stocks
   * @param buyer The username of the investor buying the stocks
   * @param transaction The details of the financial transaction to be executed.
   * @return BAD_REQUEST if the provided transaction details are invalid or OK if the transaction is successfully executed.
   */
  @PostMapping("/execute/{ticker}/{seller}/{buyer}")
  public ResponseEntity<Void> executeTransaction(@PathVariable String ticker,
      @PathVariable String seller, @PathVariable String buyer,@RequestBody Transaction transaction){
    if(!buyer.equals(transaction.getBuyer())|| !seller.equals(transaction.getSeller()) || !ticker.equals(transaction.getTicker())
    || buyer.equals(seller))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if(transaction.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    executeService.executeTransaction(transaction);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
