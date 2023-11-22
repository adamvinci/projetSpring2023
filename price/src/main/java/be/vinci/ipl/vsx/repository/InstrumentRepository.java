package be.vinci.ipl.vsx.repository;

import be.vinci.ipl.vsx.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, String> {

}
