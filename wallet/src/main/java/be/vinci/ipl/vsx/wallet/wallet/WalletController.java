
package be.vinci.ipl.vsx.wallet.wallet;


import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/wallet/{username}")
    public  ResponseEntity<List<PositionDTO>> addPositions(@PathVariable String username, @RequestBody List<Position> newPositions) {
        List<PositionDTO> positions = walletService.addPositions(username, newPositions);

        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{username}/positions")
    public ResponseEntity<List<PositionDTO>> getOpenPositions(@PathVariable String username) {
        List<PositionDTO> positions = walletService.getOpenPositions(username);

        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{username}/all-positions")
    public ResponseEntity<List<PositionDTO>> getPositions(@PathVariable String username) {
        List<PositionDTO> positions = walletService.getAllPositions(username);

        return ResponseEntity.ok(positions);
    }
}
