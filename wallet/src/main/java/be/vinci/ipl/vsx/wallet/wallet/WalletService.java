package be.vinci.ipl.vsx.wallet.wallet;

import be.vinci.ipl.vsx.wallet.wallet.data.PriceProxy;
import be.vinci.ipl.vsx.wallet.wallet.data.WalletRepository;
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

    //private List<Position> walletPositions;

    public WalletService(WalletRepository walletRepository , PriceProxy priceProxy) {
        this.walletRepository = walletRepository;
        this.priceProxy = priceProxy;
        //walletPositions = new ArrayList<>();
    }

    public List<PositionDTO> getAllPositions(String username) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null || walletPositions.isEmpty()) {
            return Collections.emptyList();
        }



        return walletPositions.stream()
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }

    public List<PositionDTO> getOpenPositions(String username) {
        List<Position> walletPositions = walletRepository.findByUsername(username);

        if (walletPositions == null || walletPositions.isEmpty()) {
            return Collections.emptyList();
        }


        return walletPositions.stream()
                .filter(p -> p.getQuantity()>0)
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }
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

            if (positionExists) {
                existingPosition.setQuantity(existingPosition.getQuantity() + newPosition.getQuantity());
            } else {
                newPosition.setUsername(username);
                walletPositions.add(newPosition);


            }
        }
        // Ajoute une position "CASH" s'il n'existe pas encore

        walletRepository.saveAll(walletPositions);

        return walletPositions.stream()
                .map(this::toPositionDTO)
                .collect(Collectors.toList());
    }

    private PositionDTO toPositionDTO(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setTicker(position.getTicker());
        positionDTO.setQuantity(position.getQuantity());
        positionDTO.setUnitValue(priceProxy.getLastPriceByTicker(position.getTicker()));
        return positionDTO;
    }



}


