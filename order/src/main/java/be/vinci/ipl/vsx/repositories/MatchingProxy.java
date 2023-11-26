package be.vinci.ipl.vsx.repositories;

import be.vinci.ipl.vsx.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "matching")
public interface MatchingProxy {

  /**
   * Try to find a match between order.
   *
   * @param ticker The name of the order to match.
   * @param order  The order to match
   * @return OK if the operation went well
   */
  @PostMapping("/trigger/{ticker}")
  ResponseEntity<Void> triggerMatching(@PathVariable String ticker, @RequestBody Order order);
}
