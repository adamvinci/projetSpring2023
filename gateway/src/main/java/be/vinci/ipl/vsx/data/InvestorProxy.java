package be.vinci.ipl.vsx.data;

import be.vinci.ipl.vsx.models.Investor.Investor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "investor")
public interface InvestorProxy {

  @PostMapping("/investor/{username}")
  String createInvestor(@PathVariable String username, @RequestBody Investor investor);

  @GetMapping("/investor/{username}")
  Investor readOne(@PathVariable String username);

  @GetMapping("/investor/readAll")
  Iterable<Investor> readAll();

  @PutMapping("/investor/{username}")
  void updateOne(@PathVariable String username, @RequestBody Investor investor);

  @DeleteMapping("/investor/{username}")
  void deleteInvestor(@PathVariable String username);
}
