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

import ng.com.dpros.customermanager.domain.Hardware;
import ng.com.dpros.customermanager.domain.*; // for static metamodels
import ng.com.dpros.customermanager.repository.HardwareRepository;
import ng.com.dpros.customermanager.repository.search.HardwareSearchRepository;
import ng.com.dpros.customermanager.service.dto.HardwareCriteria;
import ng.com.dpros.customermanager.service.dto.HardwareDTO;
import ng.com.dpros.customermanager.service.mapper.HardwareMapper;

/**
 * Service for executing complex queries for {@link Hardware} entities in the database.
 * The main input is a {@link HardwareCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HardwareDTO} or a {@link Page} of {@link HardwareDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HardwareQueryService extends QueryService<Hardware> {

    private final Logger log = LoggerFactory.getLogger(HardwareQueryService.class);

    private final HardwareRepository hardwareRepository;

    private final HardwareMapper hardwareMapper;

    private final HardwareSearchRepository hardwareSearchRepository;

    public HardwareQueryService(HardwareRepository hardwareRepository, HardwareMapper hardwareMapper, HardwareSearchRepository hardwareSearchRepository) {
        this.hardwareRepository = hardwareRepository;
        this.hardwareMapper = hardwareMapper;
        this.hardwareSearchRepository = hardwareSearchRepository;
    }

    /**
     * Return a {@link List} of {@link HardwareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HardwareDTO> findByCriteria(HardwareCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hardware> specification = createSpecification(criteria);
        return hardwareMapper.toDto(hardwareRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HardwareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HardwareDTO> findByCriteria(HardwareCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hardware> specification = createSpecification(criteria);
        return hardwareRepository.findAll(specification, page)
            .map(hardwareMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HardwareCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hardware> specification = createSpecification(criteria);
        return hardwareRepository.count(specification);
    }

    /**
     * Function to convert {@link HardwareCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hardware> createSpecification(HardwareCriteria criteria) {
        Specification<Hardware> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hardware_.id));
            }
            if (criteria.getGadget() != null) {
                specification = specification.and(buildSpecification(criteria.getGadget(), Hardware_.gadget));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Hardware_.model));
            }
            if (criteria.getBrandName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrandName(), Hardware_.brandName));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), Hardware_.serialNumber));
            }
            if (criteria.getImeiNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImeiNumber(), Hardware_.imeiNumber));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Hardware_.state));
            }
            if (criteria.getServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getServicesId(),
                    root -> root.join(Hardware_.services, JoinType.LEFT).get(Services_.id)));
            }
            if (criteria.getReviewId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewId(),
                    root -> root.join(Hardware_.reviews, JoinType.LEFT).get(Review_.id)));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(Hardware_.payments, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getServiceCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceCategoryId(),
                    root -> root.join(Hardware_.serviceCategory, JoinType.LEFT).get(ServiceCategory_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                    root -> root.join(Hardware_.profile, JoinType.LEFT).get(Profile_.id)));
            }
        }
        return specification;
    }
}
