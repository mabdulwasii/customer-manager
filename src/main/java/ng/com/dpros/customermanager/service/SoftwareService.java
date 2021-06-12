package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Software;
import ng.com.dpros.customermanager.repository.SoftwareRepository;
import ng.com.dpros.customermanager.repository.search.SoftwareSearchRepository;
import ng.com.dpros.customermanager.service.dto.SoftwareDTO;
import ng.com.dpros.customermanager.service.mapper.SoftwareMapper;
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
 * Service Implementation for managing {@link Software}.
 */
@Service
@Transactional
public class SoftwareService {

    private final Logger log = LoggerFactory.getLogger(SoftwareService.class);

    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    private final SoftwareSearchRepository softwareSearchRepository;

    public SoftwareService(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper, SoftwareSearchRepository softwareSearchRepository) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
        this.softwareSearchRepository = softwareSearchRepository;
    }

    /**
     * Save a software.
     *
     * @param softwareDTO the entity to save.
     * @return the persisted entity.
     */
    public SoftwareDTO save(SoftwareDTO softwareDTO) {
        log.debug("Request to save Software : {}", softwareDTO);
        Software software = softwareMapper.toEntity(softwareDTO);
        software = softwareRepository.save(software);
        SoftwareDTO result = softwareMapper.toDto(software);
        softwareSearchRepository.save(software);
        return result;
    }

    /**
     * Get all the software.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoftwareDTO> findAll() {
        log.debug("Request to get all Software");
        return softwareRepository.findAll().stream()
            .map(softwareMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one software by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SoftwareDTO> findOne(Long id) {
        log.debug("Request to get Software : {}", id);
        return softwareRepository.findById(id)
            .map(softwareMapper::toDto);
    }

    /**
     * Delete the software by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Software : {}", id);
        softwareRepository.deleteById(id);
        softwareSearchRepository.deleteById(id);
    }

    /**
     * Search for the software corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoftwareDTO> search(String query) {
        log.debug("Request to search Software for query {}", query);
        return StreamSupport
            .stream(softwareSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(softwareMapper::toDto)
        .collect(Collectors.toList());
    }
}
