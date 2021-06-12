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

import ng.com.dpros.customermanager.domain.Software;
import ng.com.dpros.customermanager.domain.*; // for static metamodels
import ng.com.dpros.customermanager.repository.SoftwareRepository;
import ng.com.dpros.customermanager.repository.search.SoftwareSearchRepository;
import ng.com.dpros.customermanager.service.dto.SoftwareCriteria;
import ng.com.dpros.customermanager.service.dto.SoftwareDTO;
import ng.com.dpros.customermanager.service.mapper.SoftwareMapper;

/**
 * Service for executing complex queries for {@link Software} entities in the database.
 * The main input is a {@link SoftwareCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SoftwareDTO} or a {@link Page} of {@link SoftwareDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SoftwareQueryService extends QueryService<Software> {

    private final Logger log = LoggerFactory.getLogger(SoftwareQueryService.class);

    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    private final SoftwareSearchRepository softwareSearchRepository;

    public SoftwareQueryService(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper, SoftwareSearchRepository softwareSearchRepository) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
        this.softwareSearchRepository = softwareSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SoftwareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SoftwareDTO> findByCriteria(SoftwareCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Software> specification = createSpecification(criteria);
        return softwareMapper.toDto(softwareRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SoftwareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SoftwareDTO> findByCriteria(SoftwareCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Software> specification = createSpecification(criteria);
        return softwareRepository.findAll(specification, page)
            .map(softwareMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SoftwareCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Software> specification = createSpecification(criteria);
        return softwareRepository.count(specification);
    }

    /**
     * Function to convert {@link SoftwareCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Software> createSpecification(SoftwareCriteria criteria) {
        Specification<Software> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Software_.id));
            }
            if (criteria.getTechnology() != null) {
                specification = specification.and(buildSpecification(criteria.getTechnology(), Software_.technology));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Software_.amount));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Software_.details));
            }
            if (criteria.getServiceCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceCategoryId(),
                    root -> root.join(Software_.serviceCategory, JoinType.LEFT).get(ServiceCategory_.id)));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(Software_.payments, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getServicesId(),
                    root -> root.join(Software_.services, JoinType.LEFT).get(Services_.id)));
            }
            if (criteria.getReviewId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewId(),
                    root -> root.join(Software_.reviews, JoinType.LEFT).get(Review_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                    root -> root.join(Software_.profile, JoinType.LEFT).get(Profile_.id)));
            }
        }
        return specification;
    }
}
