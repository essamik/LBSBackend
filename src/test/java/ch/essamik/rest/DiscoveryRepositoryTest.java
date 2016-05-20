package ch.essamik.rest;

import ch.essamik.LbsBackendApplication;
import ch.essamik.model.Discovery;
import ch.essamik.repositories.DiscoveryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LbsBackendApplication.class, loader=SpringApplicationContextLoader.class)
public class DiscoveryRepositoryTest {

    @Autowired
    private DiscoveryRepository discoveryRepository;

    private Discovery discovery301 = new Discovery("001", "MyBkon", Discovery.DiscoverEvent.ENTER);
    private Discovery discovery302 = new Discovery("002", "Kontakt.io Beacon 2", Discovery.DiscoverEvent.EXIT);
    private Discovery discovery303 = new Discovery("999", "Estimote Beacon 12", Discovery.DiscoverEvent.DWELL);
    private Discovery discovery304 = new Discovery("011", "ZH", Discovery.DiscoverEvent.ENTER);
    private Discovery discovery305 = new Discovery("011", "Area B", Discovery.DiscoverEvent.ENTER);

    @Before
    public void setup() throws Exception {
        this.discoveryRepository.save(discovery301);
        this.discoveryRepository.save(discovery302);
        this.discoveryRepository.save(discovery303);

        assertEquals(3, this.discoveryRepository.count());
    }

    @After
    public void tearDown() throws Exception {
        this.discoveryRepository.deleteAll();

        assertEquals(0, this.discoveryRepository.count());
    }

    @Test
    public void getByEmitterId() throws Exception {
        this.discoveryRepository.save(discovery304);
        this.discoveryRepository.save(discovery305);

        List<Discovery> listDiscovery = this.discoveryRepository.findByEmitterId(discovery305.getEmitterId());
        assertEquals(2, listDiscovery.size());
        listDiscovery = this.discoveryRepository.findByEmitterId(discovery304.getEmitterId());
        assertEquals(2, listDiscovery.size());
    }

    @Test
    public void createDiscovery() throws Exception {

        Discovery discoveryToCreate = new Discovery("beaconId", "EntrySensor", Discovery.DiscoverEvent.ENTER);
        discoveryToCreate.setTags(new String[]{"First floor", "Place 12", "Bob"});
        this.discoveryRepository.save(discoveryToCreate);

        Discovery createdDiscovery = this.discoveryRepository.findOne(discoveryToCreate.getId());
        assertNotNull(createdDiscovery.getId());
        assertEquals(Discovery.DiscoverEvent.ENTER, createdDiscovery.getEvent());
        assertEquals(discoveryToCreate.getCreated().getTime(), createdDiscovery.getCreated().getTime());
        assertEquals("beaconId", createdDiscovery.getEmitterId());
        assertEquals("EntrySensor", createdDiscovery.getEmitterName());
        assertArrayEquals(createdDiscovery.getTags(), new String[]{"First floor", "Place 12", "Bob"} );
    }
}
