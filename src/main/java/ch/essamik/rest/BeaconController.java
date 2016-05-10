package ch.essamik.rest;

import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoint for Beacon
 */

@RestController
@RequestMapping("/beacons")
public class BeaconController {

    @Autowired
    private BeaconRepository beaconRepository;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Beacon getByName(@PathVariable String name) {
        Beacon foundBeacon = beaconRepository.findByName(name);

        return foundBeacon;
    }
}
