package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.repository.ServiceRepository;
import ng.com.dpros.customermanager.repository.search.ServiceSearchRepository;
import ng.com.dpros.customermanager.service.dto.ServiceDTO;
import ng.com.dpros.customermanager.service.mapper.ServiceMapper;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Services}.
 */
@Service
@Transactional
public class ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final ServiceSearchRepository serviceSearchRepository;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper, ServiceSearchRepository serviceSearchRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.serviceSearchRepository = serviceSearchRepository;
    }

    /**
     * Save a service.
     *
     * @param serviceDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceDTO save(ServiceDTO serviceDTO) {
        log.debug("Request to save Service : {}", serviceDTO);
        Services service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        ServiceDTO result = serviceMapper.toDto(service);
        serviceSearchRepository.save(service);
        return result;
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable)
            .map(serviceMapper::toDto);
    }



    /**
     *  Get all the services where Hardware is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAllWhereHardwareIsNull() {
        log.debug("Request to get all services where Hardware is null");
        return StreamSupport
            .stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getHardware() == null)
            .map(serviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the services where Training is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAllWhereTrainingIsNull() {
        log.debug("Request to get all services where Training is null");
        return StreamSupport
            .stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getTraining() == null)
            .map(serviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the services where Software is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAllWhereSoftwareIsNull() {
        log.debug("Request to get all services where Software is null");
        return StreamSupport
            .stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getSoftware() == null)
            .map(serviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one service by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id)
            .map(serviceMapper::toDto);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
        serviceSearchRepository.deleteById(id);
    }

    /**
     * Search for the service corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Services for query {}", query);
        return serviceSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceMapper::toDto);
    }
}
