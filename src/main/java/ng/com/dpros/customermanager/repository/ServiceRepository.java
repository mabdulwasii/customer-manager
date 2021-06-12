package ng.com.dpros.customermanager.repository;

import ng.com.dpros.customermanager.domain.Services;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Service entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRepository extends JpaRepository<Services, Long>, JpaSpecificationExecutor<Services> {
}
