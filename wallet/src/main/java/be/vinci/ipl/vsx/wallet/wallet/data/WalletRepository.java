package be.vinci.ipl.vsx.wallet.wallet.data;

import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends CrudRepository<Position, String> {

    List<Position> findByUsername(String username);


}