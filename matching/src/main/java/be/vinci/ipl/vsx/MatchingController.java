package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MatchingController {

  private final MatchingService matchingService;

  public MatchingController(MatchingService matchingService) {
    this.matchingService = matchingService;
  }

  /**
   * Traite la réception d'un ordre pour un ticker spécifié, vérifie la validité de l'ordre
   * et déclenche son exécution via le service de correspondance (MatchingService).
   *
   * @param ticker Le ticker spécifié dans l'URL pour lequel l'ordre est destiné
   * @param order  L'ordre reçu dans le corps de la requête
   * @return       Une réponse HTTP indiquant le statut de la transaction :
   *               - OK (200) si l'ordre est exécuté avec succès
   *               - BAD_REQUEST (400) si l'ordre est invalide ou n'a pas pu être exécuté
   */
  @PostMapping("/trigger/{ticker}")
  public ResponseEntity<String> triggerMatching(@PathVariable String ticker, @RequestBody Order order) {
    // Vérification si l'ordre reçu est valide
    if (order==null || !order.getTicker().equals(ticker)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // Vérification si l'ordre reçu existe et execution de l'ordre
    if(!matchingService.executeOrder(order)){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
