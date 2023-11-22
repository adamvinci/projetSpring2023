package be.vinci.ipl.vsx.service;

import be.vinci.ipl.vsx.model.Instrument;

public interface PriceService {
  Double getLastPriceByTicker(String ticker);
  void updatePriceByTicker(String ticker, Double newPrice);
  void addInstrument(Instrument instrument);
}
