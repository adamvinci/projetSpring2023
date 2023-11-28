package be.vinci.ipl.vsx.wallet.wallet.data;

import be.vinci.ipl.vsx.wallet.wallet.models.Position;
import be.vinci.ipl.vsx.wallet.wallet.models.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, String> {

    Wallet findByUsername(String username);


    /*@Query("SELECT p FROM Position p WHERE p.wallet.username = :username")
    List<Position> getAllPositionsByUsername(@Param("username") String username);

     */
}