package ng.com.dpros.customermanager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.domain.*; // for static metamodels
import ng.com.dpros.customermanager.repository.ServicesRepository;
import ng.com.dpros.customermanager.repository.search.ServicesSearchRepository;
import ng.com.dpros.customermanager.service.dto.ServicesCriteria;
import ng.com.dpros.customermanager.service.dto.ServicesDTO;
import ng.com.dpros.customermanager.service.mapper.ServicesMapper;

/**
 * Service for executing complex queries for {@link Services} entities in the database.
 * The main input is a {@link ServicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServicesDTO} or a {@link Page} of {@link ServicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicesQueryService extends QueryService<Services> {

    private final Logger log = LoggerFactory.getLogger(ServicesQueryService.class);

    private final ServicesRepository servicesRepository;

    private final ServicesMapper servicesMapper;

    private final ServicesSearchRepository servicesSearchRepository;

    public ServicesQueryService(ServicesRepository servicesRepository, ServicesMapper servicesMapper, ServicesSearchRepository servicesSearchRepository) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
        this.servicesSearchRepository = servicesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServicesDTO> findByCriteria(ServicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesMapper.toDto(servicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServicesDTO> findByCriteria(ServicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.findAll(specification, page)
            .map(servicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Services> specification = createSpecification(criteria);
        return servicesRepository.count(specification);
    }

    /**
     * Function to convert {@link ServicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Services> createSpecification(ServicesCriteria criteria) {
        Specification<Services> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Services_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Services_.description));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Services_.startDate));
            }
            if (criteria.getAgree() != null) {
                specification = specification.and(buildSpecification(criteria.getAgree(), Services_.agree));
            }
            if (criteria.getSignDocUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignDocUrl(), Services_.signDocUrl));
            }
            if (criteria.getHardwareId() != null) {
                specification = specification.and(buildSpecification(criteria.getHardwareId(),
                    root -> root.join(Services_.hardware, JoinType.LEFT).get(Hardware_.id)));
            }
            if (criteria.getTrainingId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingId(),
                    root -> root.join(Services_.training, JoinType.LEFT).get(Training_.id)));
            }
            if (criteria.getSoftwareId() != null) {
                specification = specification.and(buildSpecification(criteria.getSoftwareId(),
                    root -> root.join(Services_.software, JoinType.LEFT).get(Software_.id)));
            }
        }
        return specification;
    }
}
