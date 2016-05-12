package ch.essamik.rest;

import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoint for Beacon
 */

@RestController
@RequestMapping("/beacons")
public class BeaconController {

    private static final Logger log = LoggerFactory.getLogger(BeaconController.class);


    @Autowired
    private BeaconRepository beaconRepository;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Beacon getByName(@PathVariable String name) throws Exception {
        Beacon foundBeacon = beaconRepository.findByName(name);

        if (foundBeacon != null) {
            log.info(foundBeacon.getName().toString());
            return foundBeacon;
        }
        else {
            log.info("No object found");
            throw new Exception("No object found");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beacon> getAll() throws Exception {
        return beaconRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Beacon create(@RequestBody Beacon beacon) throws Exception {
        boolean exist = beacon.getId() != null && beaconRepository.exists(beacon.getId());

        if (exist) throw new Exception("Object already exist");
        else return beaconRepository.save(beacon);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Beacon update(@RequestBody Beacon beacon) throws Exception {
        boolean exist = beacon.getId() != null && beaconRepository.exists(beacon.getId());

        if (exist) return beaconRepository.save(beacon);
        else throw new Exception("Object not existing");
    }
}
