package ng.com.dpros.customermanager.repository;

import ng.com.dpros.customermanager.domain.Trial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Trial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrialRepository extends JpaRepository<Trial, Long> {
}
