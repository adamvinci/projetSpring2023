package be.vinci.ipl.vsx.wallet.wallet.data;

import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for interacting with the database to manage Position entities.
 */
@Repository
public interface WalletRepository extends CrudRepository<Position, String> {


    /**
     * Retrieves a list of positions based on the provided username.
     *
     * @param username The username associated with the positions.
     * @return List of positions associated with the provided username.
     */
    List<Position> findByUsername(String username);


}