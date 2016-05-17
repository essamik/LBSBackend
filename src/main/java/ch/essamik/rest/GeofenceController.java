package ch.essamik.rest;

import ch.essamik.model.Geofence;
import ch.essamik.repositories.GeofenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geofences")
public class GeofenceController {

    private static final Logger log = LoggerFactory.getLogger(GeofenceController.class);

    @Autowired
    GeofenceRepository geofenceRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Geofence> getAll() throws Exception {
        return geofenceRepository.findAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Geofence getByName(@PathVariable String name) throws Exception{
        Geofence foundGeofence = geofenceRepository.findByName(name);

        if(foundGeofence != null){
            log.info(foundGeofence.getName().toString());
            return foundGeofence;
        }else{
            log.info("No object found");
            throw  new Exception("No object found");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Geofence create(@RequestBody Geofence geofence) throws Exception{
        boolean exist = geofence.getId() != null && geofenceRepository.exists(geofence.getId());

        if(exist) throw new Exception("Object already exist");
        else return geofenceRepository.save(geofence);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Geofence update(@RequestBody Geofence geofence) throws Exception{
        boolean exist = geofence.getId() != null && geofenceRepository.exists(geofence.getId());

        if(exist)return geofenceRepository.save(geofence);
        else throw new Exception("Object not existing");
    }
}
