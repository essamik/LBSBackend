package ch.essamik.repositories;

import ch.essamik.model.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistance layer for Beacon
 */
public interface BeaconRepository extends JpaRepository<Beacon, Long> {
    Beacon findByName(String name);
}
