package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.service.ServiceCategoryService;
import ng.com.dpros.customermanager.web.rest.errors.BadRequestAlertException;
import ng.com.dpros.customermanager.service.dto.ServiceCategoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ng.com.dpros.customermanager.domain.ServiceCategory}.
 */
@RestController
@RequestMapping("/api")
public class ServiceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ServiceCategoryResource.class);

    private static final String ENTITY_NAME = "serviceCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceCategoryService serviceCategoryService;

    public ServiceCategoryResource(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }

    /**
     * {@code POST  /service-categories} : Create a new serviceCategory.
     *
     * @param serviceCategoryDTO the serviceCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceCategoryDTO, or with status {@code 400 (Bad Request)} if the serviceCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-categories")
    public ResponseEntity<ServiceCategoryDTO> createServiceCategory(@RequestBody ServiceCategoryDTO serviceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceCategory : {}", serviceCategoryDTO);
        if (serviceCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceCategoryDTO result = serviceCategoryService.save(serviceCategoryDTO);
        return ResponseEntity.created(new URI("/api/service-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-categories} : Updates an existing serviceCategory.
     *
     * @param serviceCategoryDTO the serviceCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the serviceCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-categories")
    public ResponseEntity<ServiceCategoryDTO> updateServiceCategory(@RequestBody ServiceCategoryDTO serviceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceCategory : {}", serviceCategoryDTO);
        if (serviceCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceCategoryDTO result = serviceCategoryService.save(serviceCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-categories} : get all the serviceCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceCategories in body.
     */
    @GetMapping("/service-categories")
    public List<ServiceCategoryDTO> getAllServiceCategories() {
        log.debug("REST request to get all ServiceCategories");
        return serviceCategoryService.findAll();
    }

    /**
     * {@code GET  /service-categories/:id} : get the "id" serviceCategory.
     *
     * @param id the id of the serviceCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-categories/{id}")
    public ResponseEntity<ServiceCategoryDTO> getServiceCategory(@PathVariable Long id) {
        log.debug("REST request to get ServiceCategory : {}", id);
        Optional<ServiceCategoryDTO> serviceCategoryDTO = serviceCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceCategoryDTO);
    }

    /**
     * {@code DELETE  /service-categories/:id} : delete the "id" serviceCategory.
     *
     * @param id the id of the serviceCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-categories/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        log.debug("REST request to delete ServiceCategory : {}", id);
        serviceCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-categories?query=:query} : search for the serviceCategory corresponding
     * to the query.
     *
     * @param query the query of the serviceCategory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-categories")
    public List<ServiceCategoryDTO> searchServiceCategories(@RequestParam String query) {
        log.debug("REST request to search ServiceCategories for query {}", query);
        return serviceCategoryService.search(query);
    }
}
