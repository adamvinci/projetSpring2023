
package be.vinci.ipl.vsx.wallet.wallet;


import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WalletController {


    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallet/{username}/net-worth")
    public ResponseEntity<Double> getNetWorth(@PathVariable String username) {
        double netWorth = walletService.calculateNetWorth(username);


        return ResponseEntity.ok(netWorth);
    }

    @GetMapping("/{username}")
    public Wallet getWallet(@PathVariable String username) {

        Wallet wallet = walletService.getWallet(username);



        return wallet;
    }

    @PostMapping("/wallet/{username}")
    public List<Position> addPositions(@PathVariable String username, @RequestBody List<Position> newPositions) {
        return walletService.addPositions(username, newPositions);
    }

    @GetMapping("/{username}/positions")
    public List<Position> getPositions(@PathVariable String username) {
        return walletService.getOpenPositions(username);
    }
}
