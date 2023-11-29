package be.vinci.ipl.vsx.wallet.wallet;

import be.vinci.ipl.vsx.wallet.wallet.data.PriceProxy;
import be.vinci.ipl.vsx.wallet.wallet.data.WalletRepository;
import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.Wallet;
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

    public WalletService(WalletRepository walletRepository , PriceProxy priceProxy) {
        this.walletRepository = walletRepository;
        this.priceProxy = priceProxy;

    }



    public List<Position> getOpenPositions(String username) {
        Wallet wallet = walletRepository.findByUsername(username);

        if (wallet == null) {
            return Collections.emptyList();
        }

        List<Position> openPositions = wallet.getPositions().stream()
                .filter(position -> position.getQuantity() > 0)
                .collect(Collectors.toList());

        wallet.setPositions(openPositions);

        return openPositions;
    }
    public double calculateNetWorth(String username) {

        Wallet wallet = walletRepository.findByUsername(username);

        if (wallet == null) {
            return 0.0;
        }

        List<Position> openPositions = getOpenPositions(username);

        wallet.setPositions(openPositions);

        double netWorth = openPositions.stream()
                .mapToDouble(position -> {
                    Number lastPrice = priceProxy.getLastPriceByTicker(position.getTicker());
                    return position.getQuantity() * lastPrice.doubleValue();
                })
                .sum();

        wallet.setNetWorth(netWorth);

        return netWorth;
    }

    @Transactional
    public List<Position> addPositions(String username, List<Position> newPositions) {

        Wallet existingWallet = walletRepository.findByUsername(username);

        if (existingWallet == null) {
            existingWallet = new Wallet();
            existingWallet.setUsername(username);
            existingWallet.setPositions(new ArrayList<>());

        }

        List<Position> existingPositions = existingWallet.getPositions();

        for (Position newPosition : newPositions) {
            boolean positionExists = false;

            for (Position existingPosition : existingPositions) {
                if (newPosition.getTicker().equals(existingPosition.getTicker())) {
                    existingPosition.setQuantity(existingPosition.getQuantity() + newPosition.getQuantity());

                    Number unitValue = priceProxy.getLastPriceByTicker(newPosition.getTicker());
                    existingPosition.setUnitValue(unitValue);



                    positionExists = true;
                    break;
                }
            }
            if (!positionExists) {
                Number unitValue = priceProxy.getLastPriceByTicker(newPosition.getTicker());
                newPosition.setUnitValue(unitValue);
                existingPositions.add(newPosition);
            }
        }

        existingWallet.setPositions(existingPositions);
         walletRepository.save(existingWallet);

        return existingWallet.getPositions();
    }

    @Transactional
    public Wallet getWallet(String username) {


        Wallet wallet = walletRepository.findByUsername(username);
        double netWorth = calculateNetWorth(username);
        if (wallet != null) {
            List<Position> positions = wallet.getPositions();
            wallet.setNetWorth(netWorth);
            wallet.setPositions(positions);
        }

        return wallet;
    }
}


