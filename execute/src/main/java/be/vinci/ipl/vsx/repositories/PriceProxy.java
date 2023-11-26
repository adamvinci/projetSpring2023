package be.vinci.ipl.vsx.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "price-service")
public interface PriceProxy {

  /**
   * Price service endpoint to update price of the ticker
   *
   * @param ticker   The GUID of the order to update.
   * @param newPrice The last price at which the stock has been sold.
   * @return OK if the price is updated or BAD_REQUEST if the price is not valid
   */
  @PutMapping("/price/{ticker}")
  ResponseEntity<Void> updatePrice(@PathVariable String ticker, @RequestBody Number newPrice);
}
