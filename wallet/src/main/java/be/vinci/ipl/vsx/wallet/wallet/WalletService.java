package be.vinci.ipl.vsx.wallet.wallet;

import be.vinci.ipl.vsx.wallet.wallet.data.PriceProxy;
import be.vinci.ipl.vsx.wallet.wallet.data.WalletRepository;
import be.vinci.ipl.vsx.wallet.wallet.models.Investor;
import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.PositionDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final PriceProxy priceProxy;

    /**
     * Constructor for WalletService.
     *
     * @param walletRepository The repository for interacting with the database.
     * @param priceProxy       The proxy for retrieving asset prices.
     */
    public WalletService(WalletRepository walletRepository, PriceProxy priceProxy) {
        this.walletRepository = walletRepository;
        this.priceProxy = priceProxy;
    }


    /**
     * Retrieves all positions in the user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return List of PositionDTO representing all positions in the user's wallet.
     */
    public List<PositionDTO> getAllPositions(String username) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null || walletPositions.isEmpty()) {
            return Collections.emptyList();
        }


        return walletPositions.stream()
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves open positions in the user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return List of PositionDTO representing open positions in the user's wallet.
     */
    public List<PositionDTO> getOpenPositions(String username) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null || walletPositions.isEmpty()) {
            return Collections.emptyList();
        }


        return walletPositions.stream()
                .filter(p -> p.getQuantity() > 0)
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }


    /**
     * Calculates the net worth of the user's wallet.
     *
     * @param username The username associated with the wallet.
     * @return The net worth of the user's wallet.
     */
    public double calculateNetWorth(String username) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null || walletPositions.isEmpty()) {
            return 0.0;
        }

        double netWorth = walletPositions.stream()
                .mapToDouble(position -> {
                    Number lastPrice = priceProxy.getLastPriceByTicker(position.getTicker());
                    return position.getQuantity() * lastPrice.doubleValue();
                })
                .sum();

        return netWorth;
    }


    /**
     * Adds new positions to the user's wallet.
     *
     * @param username     The username associated with the wallet.
     * @param newPositions List of positions to be added to the wallet.
     * @return List of PositionDTO representing the updated list of positions in the user's wallet.
     */
    @Transactional
    public List<PositionDTO> addPositions(String username, List<Position> newPositions) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null) {
            walletPositions = new ArrayList<>();
        }

        Position cashPosition = null;
        for (Position position : walletPositions) {
            if (position.getTicker().equalsIgnoreCase("CASH")) {
                cashPosition = position;
                break;
            }
        }

        //add position CASH
        if (cashPosition == null) {
            cashPosition = new Position();
            cashPosition.setUsername(username);
            cashPosition.setTicker("CASH");
            walletPositions.add(cashPosition);
        }
        for (Position newPosition : newPositions) {
            boolean positionExists = false;
            Position existingPosition = null;

            for (Position position : walletPositions) {
                if (newPosition.getTicker().equals(position.getTicker())) {
                    existingPosition = position;
                    positionExists = true;
                    break;
                }
            }

            //if position already exists
            if (positionExists) {
                existingPosition.setQuantity(existingPosition.getQuantity() + newPosition.getQuantity());
            } else {
                newPosition.setUsername(username);
                walletPositions.add(newPosition);

            }
        }
        walletRepository.saveAll(walletPositions);

        return walletPositions.stream()
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }


    /**
     * Converts a Position entity to a PositionDTO.
     *
     * @param position The Position entity to be converted.
     * @return PositionDTO representing the converted position.
     */
    private PositionDTO toPositionDTO(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setTicker(position.getTicker());
        positionDTO.setQuantity(position.getQuantity());
        positionDTO.setUnitValue(priceProxy.getLastPriceByTicker(position.getTicker()));
        return positionDTO;
    }


}


