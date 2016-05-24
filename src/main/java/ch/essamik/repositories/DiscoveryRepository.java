package ch.essamik.repositories;

import ch.essamik.model.Discovery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Persistance layer for Discovery
 */
public interface DiscoveryRepository  extends SolrCrudRepository<Discovery, String> {

    List<Discovery> findByEmitterId(String emitterId);

    //@Query("tags_ss:?0")
    //List<Discovery> findByTags(String[] tags);

    /**
     * Query the Discovery based on the Emiter name, with a categorization done on the Event type.
     * @param emitterName The identifier of the emitter to query
     * @param page Pageable defining how many objects to return for each page
     * @return A FacetPage containing the categorized Discoveries
     */
    @Query("emitterName_s:?0")
    @Facet(fields = {"event_s"})
    FacetPage<Discovery> findByEmitterNameAndFacetOnEvent(String emitterName, Pageable page);


}
