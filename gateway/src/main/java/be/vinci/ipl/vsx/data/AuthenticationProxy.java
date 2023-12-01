package be.vinci.ipl.vsx.data;

import be.vinci.ipl.vsx.models.Credentials.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

  @PostMapping("/authentication/connect")
  String connect(@RequestBody Credentials credentials);

  @PostMapping("/authentication/verify")
  String verify(@RequestBody String token);

  @PostMapping("/authentication/{username}")
  void createCredentials(@PathVariable String username, @RequestBody Credentials credentials);

  @PutMapping("/authentication/{username}")
  void updateCredentials(@PathVariable String username, @RequestBody Credentials credentials);

  @DeleteMapping("/authentication/{username}")
  void deleteCredentials(@PathVariable String username);
}
