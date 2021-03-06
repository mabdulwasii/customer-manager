package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.service.ServicesService;
import ng.com.dpros.customermanager.web.rest.errors.BadRequestAlertException;
import ng.com.dpros.customermanager.service.dto.ServicesDTO;
import ng.com.dpros.customermanager.service.dto.ServicesCriteria;
import ng.com.dpros.customermanager.service.ServicesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ng.com.dpros.customermanager.domain.Services}.
 */
@RestController
@RequestMapping("/api")
public class ServicesResource {

    private final Logger log = LoggerFactory.getLogger(ServicesResource.class);

    private static final String ENTITY_NAME = "services";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicesService servicesService;

    private final ServicesQueryService servicesQueryService;

    public ServicesResource(ServicesService servicesService, ServicesQueryService servicesQueryService) {
        this.servicesService = servicesService;
        this.servicesQueryService = servicesQueryService;
    }

    /**
     * {@code POST  /services} : Create a new services.
     *
     * @param servicesDTO the servicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicesDTO, or with status {@code 400 (Bad Request)} if the services has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/services")
    public ResponseEntity<ServicesDTO> createServices(@RequestBody ServicesDTO servicesDTO) throws URISyntaxException {
        log.debug("REST request to save Services : {}", servicesDTO);
        if (servicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new services cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicesDTO result = servicesService.save(servicesDTO);
        return ResponseEntity.created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /services} : Updates an existing services.
     *
     * @param servicesDTO the servicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicesDTO,
     * or with status {@code 400 (Bad Request)} if the servicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/services")
    public ResponseEntity<ServicesDTO> updateServices(@RequestBody ServicesDTO servicesDTO) throws URISyntaxException {
        log.debug("REST request to update Services : {}", servicesDTO);
        if (servicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServicesDTO result = servicesService.save(servicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /services} : get all the services.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @GetMapping("/services")
    public ResponseEntity<List<ServicesDTO>> getAllServices(ServicesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Services by criteria: {}", criteria);
        Page<ServicesDTO> page = servicesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /services/count} : count all the services.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/services/count")
    public ResponseEntity<Long> countServices(ServicesCriteria criteria) {
        log.debug("REST request to count Services by criteria: {}", criteria);
        return ResponseEntity.ok().body(servicesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /services/:id} : get the "id" services.
     *
     * @param id the id of the servicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/services/{id}")
    public ResponseEntity<ServicesDTO> getServices(@PathVariable Long id) {
        log.debug("REST request to get Services : {}", id);
        Optional<ServicesDTO> servicesDTO = servicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicesDTO);
    }

    /**
     * {@code DELETE  /services/:id} : delete the "id" services.
     *
     * @param id the id of the servicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        log.debug("REST request to delete Services : {}", id);
        servicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/services?query=:query} : search for the services corresponding
     * to the query.
     *
     * @param query the query of the services search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/services")
    public ResponseEntity<List<ServicesDTO>> searchServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Services for query {}", query);
        Page<ServicesDTO> page = servicesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
