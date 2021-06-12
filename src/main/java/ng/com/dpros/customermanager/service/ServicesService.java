package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.repository.ServicesRepository;
import ng.com.dpros.customermanager.repository.search.ServicesSearchRepository;
import ng.com.dpros.customermanager.service.dto.ServicesDTO;
import ng.com.dpros.customermanager.service.mapper.ServicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Services}.
 */
@Service
@Transactional
public class ServicesService {

    private final Logger log = LoggerFactory.getLogger(ServicesService.class);

    private final ServicesRepository servicesRepository;

    private final ServicesMapper servicesMapper;

    private final ServicesSearchRepository servicesSearchRepository;

    public ServicesService(ServicesRepository servicesRepository, ServicesMapper servicesMapper, ServicesSearchRepository servicesSearchRepository) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
        this.servicesSearchRepository = servicesSearchRepository;
    }

    /**
     * Save a services.
     *
     * @param servicesDTO the entity to save.
     * @return the persisted entity.
     */
    public ServicesDTO save(ServicesDTO servicesDTO) {
        log.debug("Request to save Services : {}", servicesDTO);
        Services services = servicesMapper.toEntity(servicesDTO);
        services = servicesRepository.save(services);
        ServicesDTO result = servicesMapper.toDto(services);
        servicesSearchRepository.save(services);
        return result;
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return servicesRepository.findAll(pageable)
            .map(servicesMapper::toDto);
    }



    /**
     *  Get all the services where Hardware is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ServicesDTO> findAllWhereHardwareIsNull() {
        log.debug("Request to get all services where Hardware is null");
        return StreamSupport
            .stream(servicesRepository.findAll().spliterator(), false)
            .filter(services -> services.getHardware() == null)
            .map(servicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the services where Training is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ServicesDTO> findAllWhereTrainingIsNull() {
        log.debug("Request to get all services where Training is null");
        return StreamSupport
            .stream(servicesRepository.findAll().spliterator(), false)
            .filter(services -> services.getTraining() == null)
            .map(servicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the services where Software is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ServicesDTO> findAllWhereSoftwareIsNull() {
        log.debug("Request to get all services where Software is null");
        return StreamSupport
            .stream(servicesRepository.findAll().spliterator(), false)
            .filter(services -> services.getSoftware() == null)
            .map(servicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one services by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicesDTO> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findById(id)
            .map(servicesMapper::toDto);
    }

    /**
     * Delete the services by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.deleteById(id);
        servicesSearchRepository.deleteById(id);
    }

    /**
     * Search for the services corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Services for query {}", query);
        return servicesSearchRepository.search(queryStringQuery(query), pageable)
            .map(servicesMapper::toDto);
    }
}
