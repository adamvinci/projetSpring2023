package be.vinci.ipl.vsx.repositories;

import be.vinci.ipl.vsx.models.Position;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "wallet")
public interface WalletProxy {

  /**
   * Wallet service endpoint to update the wallet of a buyer/seller
   *
   * @param username of the owner of the wallet to update
   * @param newPositions new list of positions
   * @return the content of the new wallet
   */
  @PostMapping("/wallet/{username}")
  List<Position> addPositions(@PathVariable String username,
      @RequestBody List<Position> newPositions);
}
