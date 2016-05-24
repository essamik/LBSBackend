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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
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
    private Discovery discovery304 = new Discovery("011", "Area B", Discovery.DiscoverEvent.ENTER);
    private Discovery discovery305 = new Discovery("011", "Area B", Discovery.DiscoverEvent.ENTER);
    private Discovery discovery306 = new Discovery("011", "Area B", Discovery.DiscoverEvent.ENTER);
    private Discovery discovery307 = new Discovery("011", "Area B", Discovery.DiscoverEvent.EXIT);

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
    public void getByEmitterNameAndFacetOnEvent() {
        this.discoveryRepository.save(discovery304);
        this.discoveryRepository.save(discovery305);
        this.discoveryRepository.save(discovery306);
        this.discoveryRepository.save(discovery307);

        FacetPage<Discovery> facetPage = this.discoveryRepository.findByEmitterNameAndFacetOnEvent(discovery304.getEmitterName(), new PageRequest(0, 20));
        List<Discovery> listDiscoveries = facetPage.getContent();

        assertEquals(4, listDiscoveries.size());

        for (Page<? extends FacetEntry> page : facetPage.getAllFacets()) {
            for (FacetEntry facetEntry : page.getContent()) {
                String eventName = facetEntry.getValue();
                // convert the event name back to an enum
                Discovery.DiscoverEvent eventEnum = Discovery.DiscoverEvent.valueOf(eventName.toUpperCase());
                long count = facetEntry.getValueCount();      // number of discovery in this event type

                if (eventEnum.equals(Discovery.DiscoverEvent.ENTER)) assertEquals(3, count);
                else if (eventEnum.equals(Discovery.DiscoverEvent.EXIT)) assertEquals(1, count);
            }
        }

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

    /**
     @Test
     public void searchOnTags() {
     discovery304.setTags(new String[]{"username", "bob", "customerType", "loyal", "age", "25", "office", "zurich", });
     discovery305.setTags(new String[]{"username", "jean", "age", "21", "office", "zurich", });
     discovery306.setTags(new String[]{"", ""});
     discovery307.setTags(new String[]{"username", "bob", "customertype", "loyal", "age", "25", "office", "zurich", });
     this.discoveryRepository.save(discovery304);
     this.discoveryRepository.save(discovery305);
     this.discoveryRepository.save(discovery306);
     this.discoveryRepository.save(discovery307);



     List<Discovery> bobDiscoveries = this.discoveryRepository.findByTags(new String[]{"bob"});
     assertEquals(2, bobDiscoveries.size());

     List<Discovery> zurichDiscoveries = this.discoveryRepository.findByTags(new String[]{"zurich"});
     assertEquals(3, zurichDiscoveries.size());

     List<Discovery> jeanDiscoveries = this.discoveryRepository.findByTags(new String[]{"jean", "21"});
     assertEquals(1, jeanDiscoveries.size());

     List<Discovery> emptyDiscoveries = this.discoveryRepository.findByTags(new String[]{""});
     assertEquals(0, emptyDiscoveries.size());

     }
     */

}
