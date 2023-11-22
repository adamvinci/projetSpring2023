package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.model.Instrument;
import be.vinci.ipl.vsx.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
public class PriceController {


  private PriceService priceService;

  /**
   * Constructeur du contrôleur des prix.
   * @param priceService Le service des prix.
   */
  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  /**
   * Récupère le dernier prix en fonction du ticker.
   * @param ticker Le ticker de l'instrument.
   * @return Le dernier prix de l'instrument correspondant au ticker.
   */
  @GetMapping("/{ticker}")
  public ResponseEntity<Double> getLastPrice(@PathVariable String ticker) {
    Double lastPrice = priceService.getLastPriceByTicker(ticker);

    if (lastPrice != null) {
      return ResponseEntity.ok(lastPrice);
    } else {
      // Initialisation du prix selon les spécifications
      if ("CASH".equals(ticker)) {
        // Le prix du CASH reste fixé à 1
        priceService.updatePriceByTicker(ticker, 1.0); // Met à jour le prix à 1
        return ResponseEntity.ok(1.0); // Retourne le prix initial du CASH
      } else {
        // Initialisation du prix pour un nouveau ticker à 1 euro par action
        priceService.updatePriceByTicker(ticker, 1.0);
        return ResponseEntity.ok(1.0); // Retourne le prix initial pour le nouveau ticker
      }
    }
  }


  /**
   * Met à jour le prix en fonction du ticker.
   * @param ticker Le ticker de l'instrument.
   * @param newPrice Le nouveau prix à mettre à jour.
   * @return Réponse indiquant si le prix a été mis à jour ou s'il a été ajouté comme un nouvel instrument.
   */
  @PatchMapping("/{ticker}")
  public ResponseEntity<String> updatePrice(@PathVariable String ticker, @RequestBody Double newPrice) {
    if ("CASH".equals(ticker)) {
      return ResponseEntity.badRequest().body("Impossible de mettre à jour le prix du CASH");
    }
    // Vérifie si le ticker existe déjà dans la base de données
    Double existingPrice = priceService.getLastPriceByTicker(ticker);

    if (existingPrice != null) {
      // Le ticker existe, met à jour le prix
      if (newPrice < 0) {
        return ResponseEntity.badRequest().body("Le prix n'était pas valide (négatif)");
      }
      priceService.updatePriceByTicker(ticker, newPrice);
      return ResponseEntity.ok("Le prix a été mis à jour");
    } else {
      // Le ticker n'existe pas, crée un nouvel instrument avec le prix spécifié
      Instrument newInstrument = new Instrument();
      newInstrument.setTicker(ticker);
      newInstrument.setPrice(newPrice);
      priceService.addInstrument(newInstrument);
      return ResponseEntity.ok("Le nouvel instrument avec le prix a été ajouté à la base de données");
    }
  }





}
