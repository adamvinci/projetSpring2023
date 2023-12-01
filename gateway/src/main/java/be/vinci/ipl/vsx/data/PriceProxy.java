package be.vinci.ipl.vsx.data;

;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "price-service")
public interface PriceProxy {

  @GetMapping("/price/{ticker}")
  Double getLastPrice(@PathVariable String ticker);

  @PatchMapping("/price/{ticker}")
  void updatePrice(@PathVariable String ticker, @RequestBody Number newPrice);
}
