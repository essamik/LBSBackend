package ch.essamik.repositories;

import ch.essamik.model.Discovery;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * Persistance layer for Discovery
 */
public interface DiscoveryRepository  extends SolrCrudRepository<Discovery, String> {

    List<Discovery> findByEmitterId(String emitterId);
}
