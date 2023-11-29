package be.vinci.ipl.vsx.service;

import be.vinci.ipl.vsx.model.Instrument;

public interface PriceService {
  Number getLastPriceByTicker(String ticker);
  void updatePriceByTicker(String ticker, Number newPrice);
  void addInstrument(Instrument instrument);
}
