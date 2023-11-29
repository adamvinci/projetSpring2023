package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.SafeCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<SafeCredentials, String> {
}
