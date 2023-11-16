package be.vinci.ipl.vsx;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecuteController {
    private final ExecuteService executeService;

  public ExecuteController(ExecuteService executeService) {
    this.executeService = executeService;
  }

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
