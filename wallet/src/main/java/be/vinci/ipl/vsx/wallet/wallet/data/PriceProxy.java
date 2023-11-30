package be.vinci.ipl.vsx.wallet.wallet.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "price-service")
public interface PriceProxy {

    /**
     * Retrieves the last price for a given ticker symbol.
     *
     * @param ticker The ticker symbol of the asset.
     * @return The last price of the asset.
     */
    @GetMapping("/price/{ticker}")
    Number getLastPriceByTicker(@PathVariable String ticker);


}
