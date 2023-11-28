package be.vinci.ipl.vsx.wallet.wallet.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "price-service")
public interface PriceProxy {


    @GetMapping("/price/{ticker}")
    Double getLastPrice(@PathVariable String ticker);


}
