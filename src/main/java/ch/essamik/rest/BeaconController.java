package ch.essamik.rest;

import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            log.info("No beacon found");
            throw new Exception("No beacon found");
        }
    }

    /**
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAppException(Exception ex) {
        return ex.getMessage();
    }
     */

    /**
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
    public class ObjectNotFoundException extends RuntimeException
    {}

*/
}
