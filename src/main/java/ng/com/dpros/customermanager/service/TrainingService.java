package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Training;
import ng.com.dpros.customermanager.repository.TrainingRepository;
import ng.com.dpros.customermanager.repository.search.TrainingSearchRepository;
import ng.com.dpros.customermanager.service.dto.TrainingDTO;
import ng.com.dpros.customermanager.service.mapper.TrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    private final TrainingSearchRepository trainingSearchRepository;

    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper, TrainingSearchRepository trainingSearchRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.trainingSearchRepository = trainingSearchRepository;
    }

    /**
     * Save a training.
     *
     * @param trainingDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingDTO save(TrainingDTO trainingDTO) {
        log.debug("Request to save Training : {}", trainingDTO);
        Training training = trainingMapper.toEntity(trainingDTO);
        training = trainingRepository.save(training);
        TrainingDTO result = trainingMapper.toDto(training);
        trainingSearchRepository.save(training);
        return result;
    }

    /**
     * Get all the trainings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingDTO> findAll() {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll().stream()
            .map(trainingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one training by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrainingDTO> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id)
            .map(trainingMapper::toDto);
    }

    /**
     * Delete the training by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
        trainingSearchRepository.deleteById(id);
    }

    /**
     * Search for the training corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingDTO> search(String query) {
        log.debug("Request to search Trainings for query {}", query);
        return StreamSupport
            .stream(trainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(trainingMapper::toDto)
        .collect(Collectors.toList());
    }
}
