package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Trial;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Trial}.
 */
public interface TrialService {

    /**
     * Save a trial.
     *
     * @param trial the entity to save.
     * @return the persisted entity.
     */
    Trial save(Trial trial);

    /**
     * Get all the trials.
     *
     * @return the list of entities.
     */
    List<Trial> findAll();


    /**
     * Get the "id" trial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trial> findOne(Long id);

    /**
     * Delete the "id" trial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the trial corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Trial> search(String query);
}
