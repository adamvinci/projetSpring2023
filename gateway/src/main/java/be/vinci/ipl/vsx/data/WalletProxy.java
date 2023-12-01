package be.vinci.ipl.vsx.data;

import be.vinci.ipl.vsx.models.Wallet.Position;
import be.vinci.ipl.vsx.models.Wallet.PositionDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "wallet")
public interface WalletProxy {

  @GetMapping("/wallet/{username}/net-worth")
  Double getNetWorth(@PathVariable String username);

  @PostMapping("/wallet/{username}")
  List<PositionDTO> addPositions(@PathVariable String username, @RequestBody List<Position> newPositions);

  @GetMapping("/{username}/positions")
  List<PositionDTO> getOpenPositions(@PathVariable String username);

  @GetMapping("/{username}/all-positions")
  List<PositionDTO> getPositions(@PathVariable String username);
}
