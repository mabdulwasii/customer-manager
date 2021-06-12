package ng.com.dpros.customermanager.repository;

import ng.com.dpros.customermanager.domain.Software;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Software entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long>, JpaSpecificationExecutor<Software> {
}
