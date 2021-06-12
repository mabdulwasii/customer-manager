package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.Hardware;
import ng.com.dpros.customermanager.repository.HardwareRepository;
import ng.com.dpros.customermanager.repository.search.HardwareSearchRepository;
import ng.com.dpros.customermanager.service.dto.HardwareDTO;
import ng.com.dpros.customermanager.service.mapper.HardwareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Hardware}.
 */
@Service
@Transactional
public class HardwareService {

    private final Logger log = LoggerFactory.getLogger(HardwareService.class);

    private final HardwareRepository hardwareRepository;

    private final HardwareMapper hardwareMapper;

    private final HardwareSearchRepository hardwareSearchRepository;

    public HardwareService(HardwareRepository hardwareRepository, HardwareMapper hardwareMapper, HardwareSearchRepository hardwareSearchRepository) {
        this.hardwareRepository = hardwareRepository;
        this.hardwareMapper = hardwareMapper;
        this.hardwareSearchRepository = hardwareSearchRepository;
    }

    /**
     * Save a hardware.
     *
     * @param hardwareDTO the entity to save.
     * @return the persisted entity.
     */
    public HardwareDTO save(HardwareDTO hardwareDTO) {
        log.debug("Request to save Hardware : {}", hardwareDTO);
        Hardware hardware = hardwareMapper.toEntity(hardwareDTO);
        hardware = hardwareRepository.save(hardware);
        HardwareDTO result = hardwareMapper.toDto(hardware);
        hardwareSearchRepository.save(hardware);
        return result;
    }

    /**
     * Get all the hardware.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HardwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hardware");
        return hardwareRepository.findAll(pageable)
            .map(hardwareMapper::toDto);
    }


    /**
     * Get one hardware by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HardwareDTO> findOne(Long id) {
        log.debug("Request to get Hardware : {}", id);
        return hardwareRepository.findById(id)
            .map(hardwareMapper::toDto);
    }

    /**
     * Delete the hardware by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hardware : {}", id);
        hardwareRepository.deleteById(id);
        hardwareSearchRepository.deleteById(id);
    }

    /**
     * Search for the hardware corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HardwareDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hardware for query {}", query);
        return hardwareSearchRepository.search(queryStringQuery(query), pageable)
            .map(hardwareMapper::toDto);
    }
}
