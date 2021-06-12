package ng.com.dpros.customermanager.repository;

import ng.com.dpros.customermanager.domain.Hardware;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Hardware entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {
}
