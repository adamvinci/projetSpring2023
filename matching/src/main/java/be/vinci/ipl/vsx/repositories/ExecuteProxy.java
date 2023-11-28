package be.vinci.ipl.vsx.repositories;


import be.vinci.ipl.vsx.models.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "execute")
public interface ExecuteProxy {

  /**
   * Endpoint to execute a financial transaction between a seller and a buyer for a specific stock.
   * @param ticker The Stock alphanumeric code
   * @param seller The username of the investor selling the stocks
   * @param buyer The username of the investor buying the stocks
   * @param transaction The details of the financial transaction to be executed.
   * @return BAD_REQUEST if the provided transaction details are invalid or OK if the transaction is successfully executed.
   */
  @PostMapping("/execute/{ticker}/{seller}/{buyer}")
  ResponseEntity<Void> executeTransaction(@PathVariable String ticker,
      @PathVariable String seller, @PathVariable String buyer,@RequestBody Transaction transaction);

}
