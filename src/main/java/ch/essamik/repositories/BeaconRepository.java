package ch.essamik.repositories;

import ch.essamik.model.Beacon;
import org.springframework.data.repository.CrudRepository;

/**
 * Persistance layer for Beacon
 */
public interface BeaconRepository extends CrudRepository<Beacon, Long> {
    Beacon findByName(String name);
}
