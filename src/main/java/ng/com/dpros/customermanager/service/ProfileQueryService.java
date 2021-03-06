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

import ng.com.dpros.customermanager.domain.Profile;
import ng.com.dpros.customermanager.domain.*; // for static metamodels
import ng.com.dpros.customermanager.repository.ProfileRepository;
import ng.com.dpros.customermanager.repository.search.ProfileSearchRepository;
import ng.com.dpros.customermanager.service.dto.ProfileCriteria;
import ng.com.dpros.customermanager.service.dto.ProfileDTO;
import ng.com.dpros.customermanager.service.mapper.ProfileMapper;

/**
 * Service for executing complex queries for {@link Profile} entities in the database.
 * The main input is a {@link ProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProfileDTO} or a {@link Page} of {@link ProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfileQueryService extends QueryService<Profile> {

    private final Logger log = LoggerFactory.getLogger(ProfileQueryService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final ProfileSearchRepository profileSearchRepository;

    public ProfileQueryService(ProfileRepository profileRepository, ProfileMapper profileMapper, ProfileSearchRepository profileSearchRepository) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.profileSearchRepository = profileSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findByCriteria(ProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileMapper.toDto(profileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findByCriteria(ProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification, page)
            .map(profileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Profile> createSpecification(ProfileCriteria criteria) {
        Specification<Profile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Profile_.id));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Profile_.phoneNumber));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Profile_.dateOfBirth));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfileId(), Profile_.profileId));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Profile_.gender));
            }
            if (criteria.getValidId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidId(), Profile_.validId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Profile_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(Profile_.address, JoinType.LEFT).get(Address_.id)));
            }
            if (criteria.getHardwareId() != null) {
                specification = specification.and(buildSpecification(criteria.getHardwareId(),
                    root -> root.join(Profile_.hardware, JoinType.LEFT).get(Hardware_.id)));
            }
            if (criteria.getSoftwareId() != null) {
                specification = specification.and(buildSpecification(criteria.getSoftwareId(),
                    root -> root.join(Profile_.software, JoinType.LEFT).get(Software_.id)));
            }
            if (criteria.getTrainingId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingId(),
                    root -> root.join(Profile_.trainings, JoinType.LEFT).get(Training_.id)));
            }
        }
        return specification;
    }
}
