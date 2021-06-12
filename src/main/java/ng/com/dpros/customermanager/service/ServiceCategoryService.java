package ng.com.dpros.customermanager.service;

import ng.com.dpros.customermanager.domain.ServiceCategory;
import ng.com.dpros.customermanager.repository.ServiceCategoryRepository;
import ng.com.dpros.customermanager.repository.search.ServiceCategorySearchRepository;
import ng.com.dpros.customermanager.service.dto.ServiceCategoryDTO;
import ng.com.dpros.customermanager.service.mapper.ServiceCategoryMapper;
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
 * Service Implementation for managing {@link ServiceCategory}.
 */
@Service
@Transactional
public class ServiceCategoryService {

    private final Logger log = LoggerFactory.getLogger(ServiceCategoryService.class);

    private final ServiceCategoryRepository serviceCategoryRepository;

    private final ServiceCategoryMapper serviceCategoryMapper;

    private final ServiceCategorySearchRepository serviceCategorySearchRepository;

    public ServiceCategoryService(ServiceCategoryRepository serviceCategoryRepository, ServiceCategoryMapper serviceCategoryMapper, ServiceCategorySearchRepository serviceCategorySearchRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceCategoryMapper = serviceCategoryMapper;
        this.serviceCategorySearchRepository = serviceCategorySearchRepository;
    }

    /**
     * Save a serviceCategory.
     *
     * @param serviceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceCategoryDTO save(ServiceCategoryDTO serviceCategoryDTO) {
        log.debug("Request to save ServiceCategory : {}", serviceCategoryDTO);
        ServiceCategory serviceCategory = serviceCategoryMapper.toEntity(serviceCategoryDTO);
        serviceCategory = serviceCategoryRepository.save(serviceCategory);
        ServiceCategoryDTO result = serviceCategoryMapper.toDto(serviceCategory);
        serviceCategorySearchRepository.save(serviceCategory);
        return result;
    }

    /**
     * Get all the serviceCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceCategoryDTO> findAll() {
        log.debug("Request to get all ServiceCategories");
        return serviceCategoryRepository.findAll().stream()
            .map(serviceCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serviceCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceCategoryDTO> findOne(Long id) {
        log.debug("Request to get ServiceCategory : {}", id);
        return serviceCategoryRepository.findById(id)
            .map(serviceCategoryMapper::toDto);
    }

    /**
     * Delete the serviceCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceCategory : {}", id);
        serviceCategoryRepository.deleteById(id);
        serviceCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceCategory corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceCategoryDTO> search(String query) {
        log.debug("Request to search ServiceCategories for query {}", query);
        return StreamSupport
            .stream(serviceCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(serviceCategoryMapper::toDto)
        .collect(Collectors.toList());
    }
}
