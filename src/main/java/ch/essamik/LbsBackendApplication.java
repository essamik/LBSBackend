package ch.essamik;

import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LbsBackendApplication {

	private static final Logger log = LoggerFactory.getLogger(LbsBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LbsBackendApplication.class, args);
	}

	/**
	 * Populating the database with examples objects
     */
	@Bean
	public CommandLineRunner populate(BeaconRepository repository) {
		return (args) -> {
			// save a couple of beacons
			repository.save(new Beacon("MyBkn", "213f5d8d-75c1-44fb-b375-ae101d5ce2a8", 100 ,1));
			repository.save(new Beacon("KBeacon", "47f35b95-35df-40bb-ae7f-4ca1e4778d6c", 100 ,5));
			repository.save(new Beacon("MBeacon", "e89dbcf7-0d5d-4ed8-a378-e42435230b57", 100 ,3));
			repository.save(new Beacon("Sensor1", "4dd96110-ae33-41c6-b29c-800848129036", 345 ,2));
			repository.save(new Beacon("ProximitySensor", "86690f51-4b60-4fa2-a4d4-5520745acc4a", 1 ,2));
		};
	}
}
