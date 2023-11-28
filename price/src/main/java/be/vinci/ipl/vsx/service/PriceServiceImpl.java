package be.vinci.ipl.vsx.service;

import be.vinci.ipl.vsx.model.Instrument;
import be.vinci.ipl.vsx.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service gérant les prix des instruments.
 */
@Service
public class PriceServiceImpl implements PriceService {


  private InstrumentRepository instrumentRepository;


  /**
   * Constructeur du service de gestion des prix.
   * @param instrumentRepository Le repository des instruments.
   */
  public PriceServiceImpl(InstrumentRepository instrumentRepository) {
    this.instrumentRepository = instrumentRepository;
  }


  /**
   * Récupère le dernier prix en fonction du ticker.
   * @param ticker Le ticker de l'instrument.
   * @return Le dernier prix de l'instrument correspondant au ticker.
   */
  @Override
  public Number getLastPriceByTicker(String ticker) {
    Instrument instrument = instrumentRepository.findById(ticker).orElse(null);
    return instrument != null ? instrument.getPrice() : null;
  }

  /**
   * Met à jour le prix en fonction du ticker.
   * @param ticker Le ticker de l'instrument.
   * @param newPrice Le nouveau prix à mettre à jour.
   */
  @Override
  public void updatePriceByTicker(String ticker, Number newPrice) {
    Instrument instrument = instrumentRepository.findById(ticker).orElse(null);
    if (instrument != null) {
      instrument.setPrice(newPrice);
      instrumentRepository.save(instrument);
    }
  }

  /**
   * Ajoute un nouvel instrument.
   * @param instrument Le nouvel instrument à ajouter.
   */
  public void addInstrument(Instrument instrument) {
    instrumentRepository.save(instrument);
  }
}
