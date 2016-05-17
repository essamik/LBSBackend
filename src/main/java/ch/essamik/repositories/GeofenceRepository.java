package ch.essamik.repositories;

import ch.essamik.model.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofenceRepository extends JpaRepository<Geofence,Long> {
    Geofence findByName(String name);
}
