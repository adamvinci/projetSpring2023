package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.exceptions.*;
import be.vinci.ipl.vsx.models.*;
import be.vinci.ipl.vsx.models.Order.Order;
import feign.FeignException;
import java.util.Objects;
import java.util.Optional;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

  private final GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }



  @PostMapping("/authentication/connect")
  public ResponseEntity<String> connect(@RequestBody Credentials credentials) {

    try {
      String token = service.connect(credentials);
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/investor/{username}")
  public ResponseEntity<Void> createInvestor(@PathVariable String username, @RequestBody InvestorWithCredentials investor) {
    if (!Objects.equals(investor.getUsername(), username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    try {
      service.createInvestor(investor);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ConflictException e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/investor/{username}")
  public ResponseEntity<Investor> readInvestor(@PathVariable String username, @RequestHeader("Authorization") String token) {
    String validToken = service.verify(token);
    if(validToken == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if(!validToken.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    Investor investor = service.readInvestor(username);

    if (investor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(investor, HttpStatus.OK);
  }

  /**
   * Delete an investor
   * @param username Investor's username
   * @return Credentials
   */
  @DeleteMapping("investor/{username}")
  public ResponseEntity<?> deleteInvestor(@PathVariable String username, @RequestHeader("Authorization") String token) {
    String validToken = service.verify(token);
    if(validToken == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if(!validToken.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    boolean deleted = service.deleteInvestor(username);
    if(!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Update Investor's password (Credentiols)
   * @param username Investor's username
   * @param credentials Investor's credentials containing new password
   * @param token Investor's token
   * @return Investor updated
   */
  @PutMapping("/investor/{username}")
  public ResponseEntity<?> updateInvestor(@PathVariable String username,
      @RequestBody Credentials credentials, @RequestHeader("Authorization") String token){

    String validToken = service.verify(token);
    if(validToken == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if(!validToken.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);



    boolean updated = service.updateInvestor(credentials);
    if(!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Create new Order
   * @param order new Order to be created
   * @param token token
   * @return New order
   */
  @PostMapping("order")
  public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestHeader("Authorization") String token)
      {
        String validToken = service.verify(token);
        if(validToken == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(!validToken.equals(order.getOwner())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    try {
      Order newOrder = service.createOrder(order);
      return new ResponseEntity<>(newOrder,HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/order/by-user/{username}")
  public ResponseEntity<Iterable<Order>> readAllOrdersByInvestor(@PathVariable String username, @RequestHeader("Authorization") String token){
    String validToken = service.verify(token);
    if(validToken == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    if(!validToken.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    try {
      Iterable<Order> orders = service.readAllOrdersByInvestor(username);
      return new ResponseEntity<>(orders, HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
