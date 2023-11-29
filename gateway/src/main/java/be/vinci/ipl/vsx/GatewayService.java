package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.data.AuthenticationProxy;
import be.vinci.ipl.vsx.data.InvestorProxy;
import be.vinci.ipl.vsx.exceptions.BadRequestException;
import be.vinci.ipl.vsx.exceptions.ConflictException;
import be.vinci.ipl.vsx.exceptions.UnauthorizedException;
import be.vinci.ipl.vsx.models.*;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

  private final AuthenticationProxy authenticationProxy;
  private final InvestorProxy investorProxy;


  public GatewayService(AuthenticationProxy authenticationProxy, InvestorProxy investorProxy) {
    this.authenticationProxy = authenticationProxy;
    this.investorProxy = investorProxy;
  }

  /**
   * Get connection token from credentials
   *
   * @param credentials Credentials of the user
   * @return Connection token
   * @throws BadRequestException when the credentials are invalid
   * @throws UnauthorizedException when the credentials are incorrect
   */
  public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
    try {
      return authenticationProxy.connect(credentials);
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 401) throw new UnauthorizedException();
      else throw e;
    }
  }

  public void createInvestor(InvestorWithCredentials investor) throws BadRequestException, ConflictException {
    try {
      investorProxy.createInvestor(investor.toInvestor());
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 409) throw new ConflictException();
      else throw e;
    }

    try {
      authenticationProxy.createCredentials(investor.getUsername(), investor.toCredentials());
    } catch (FeignException e) {
      try {
        investorProxy.deleteInvestor(investor.getUsername());
      } catch (FeignException ignored) {
      }

      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 409) throw new ConflictException();
      else throw e;
    }
  }

}
