package ch.essamik.rest;

import ch.essamik.LbsBackendApplication;
import ch.essamik.model.Beacon;
import ch.essamik.repositories.BeaconRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LbsBackendApplication.class)
@WebAppConfiguration
public class BeaconResporitoyTest {

    @Autowired
    private BeaconRepository beaconRepository;

    private Beacon beacon101 = new Beacon("Beacon101", "f7826da6-4fa2-4e98-8024-bc5b71e0893e", 100 ,1);
    private Beacon beacon102 = new Beacon("Sensor1", "4dd96110-ae33-41c6-b29c-800848129036", 345 ,2);
    private Beacon beacon103 = new Beacon("ProximitySensor", "86690f51-4b60-4fa2-a4d4-5520745acc4a", 1 ,2);

    @Before
    public void setup() throws Exception {
        this.beaconRepository.save(beacon101);
        this.beaconRepository.save(beacon102);
    }

    @After
    public void tearDown() {
        this.beaconRepository.deleteAll();
    }

    @Test
    public void createBeacon() throws Exception {

        Beacon beaconToCreate = new Beacon("BeaconToCreate", "42826da6-4fa2-4e98-8024-bc5b71e1893e", 1 ,1);
        Beacon createdBeacon = this.beaconRepository.save(beaconToCreate);

        assertNotNull(createdBeacon.getId());
        assertEquals("BeaconToCreate", createdBeacon.getName());
        assertNull(createdBeacon.getVendorId());
        assertEquals("42826da6-4fa2-4e98-8024-bc5b71e1893e", createdBeacon.getUuid());
        assertEquals(1, createdBeacon.getMajor());
        assertEquals(1, createdBeacon.getMinor());
    }

    @Test
    public void getBeaconByName() {
        Beacon beacon = this.beaconRepository.findByName(this.beacon101.getName());
        compareBeaconsEquals(beacon, this.beacon101);

        beacon = this.beaconRepository.findByName("unknown name");
        assertNull(beacon);
    }

    @Test
    public void getAllBeacons() {
        List<Beacon> beacons = this.beaconRepository.findAll();

        assertEquals(2, beacons.size());

        this.beaconRepository.save(this.beacon103);
        beacons = this.beaconRepository.findAll();
        assertEquals(3, beacons.size());

        this.beaconRepository.deleteAll();
        beacons = this.beaconRepository.findAll();
        assertEquals(0, beacons.size());
    }

    @Test
    public void updateBeacon() {
        Beacon beaconToUpdate = this.beaconRepository.findOne(this.beacon102.getId());

        beaconToUpdate.setName("updated name");
        beaconToUpdate.setVendorId("UPDT");
        beaconToUpdate.setUuid("UPDT-UPDT-UPDT");
        beaconToUpdate.setMajor(111);
        beaconToUpdate.setMinor(22);

        Beacon updatedBeacon = this.beaconRepository.save(beaconToUpdate);

        assertEquals(beaconToUpdate.getId(), updatedBeacon.getId());
        assertEquals("updated name", updatedBeacon.getName());
        assertEquals("UPDT", updatedBeacon.getVendorId());
        assertEquals("UPDT-UPDT-UPDT", updatedBeacon.getUuid());
        assertEquals(111, updatedBeacon.getMajor());
        assertEquals(22, updatedBeacon.getMinor());
    }

    @Test
    public void deleteBeacon() {
        Beacon beaconToDelete = this.beaconRepository.findOne(this.beacon101.getId());

        this.beaconRepository.delete(beaconToDelete);

        assertNull(this.beaconRepository.findOne(beaconToDelete.getId()));
    }


    public void compareBeaconsEquals(Beacon b1, Beacon b2) {
        assertEquals(b1.getName(), b2.getName());
        assertEquals(b1.getVendorId(), b2.getVendorId());
        assertEquals(b1.getUuid(), b2.getUuid());
        assertEquals(b1.getMajor(), b2.getMajor());
        assertEquals(b1.getMinor(), b2.getMinor());
    }
}
