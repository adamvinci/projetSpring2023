
package be.vinci.ipl.vsx.wallet.wallet;


import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController {


    private final WalletService walletService;

    /**
     * Constructor for WalletController.
     *
     * @param walletService The service responsible for handling wallet-related business logic.
     */
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    /**
     * Endpoint to get the net worth of a user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return ResponseEntity containing the net worth of the user's wallet.
     */
    @GetMapping("/wallet/{username}/net-worth")
    public ResponseEntity<Double> getNetWorth(@PathVariable String username) {
        double netWorth = walletService.calculateNetWorth(username);


        return ResponseEntity.ok(netWorth);
    }

    /**
     * Endpoint to add new positions to the user's wallet.
     *
     * @param username     The username associated with the wallet.
     * @param newPositions List of positions to be added to the wallet.
     * @return ResponseEntity containing the updated list of positions in DTO format.
     */
    @PostMapping("/wallet/{username}")
    public  ResponseEntity<List<PositionDTO>> addPositions(@PathVariable String username, @RequestBody List<Position> newPositions) {



        List<PositionDTO> positions = walletService.addPositions(username, newPositions);



        return ResponseEntity.ok(positions);
    }

    /**
     * Endpoint to get all open positions in the user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return ResponseEntity containing the list of open positions in DTO format.
     */
    @GetMapping("/{username}/positions")
    public ResponseEntity<List<PositionDTO>> getOpenPositions(@PathVariable String username) {
        List<PositionDTO> positions = walletService.getOpenPositions(username);

        return ResponseEntity.ok(positions);
    }


    /**
     * Endpoint to get all positions (including closed) in the user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return ResponseEntity containing the list of all positions in DTO format.
     */
    @GetMapping("/{username}/all-positions")
    public ResponseEntity<List<PositionDTO>> getPositions(@PathVariable String username) {
        List<PositionDTO> positions = walletService.getAllPositions(username);

        return ResponseEntity.ok(positions);
    }
}
