package be.vinci.ipl.vsx.repositories;

import be.vinci.ipl.vsx.models.FilledQuantity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Proxy interface for communicating with the "order" microservice using OpenFeign.
 */
@Repository
@FeignClient(name = "order")
public interface OrderProxy {

  /**
   * Order service endpoint to update the filled quantity of an order based on its GUID.
   * @param guid The GUID of the order to update.
   * @param filled The quantity of shares that have already been traded for this order.
   * @return NOT_FOUND if the guid does not exist or OK if the order is successfully updated
   */
  @PutMapping("/order/{guid}")
  ResponseEntity<Void> updateOne(@PathVariable String guid,@RequestBody FilledQuantity filled);
}
