package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.service.SoftwareService;
import ng.com.dpros.customermanager.web.rest.errors.BadRequestAlertException;
import ng.com.dpros.customermanager.service.dto.SoftwareDTO;
import ng.com.dpros.customermanager.service.dto.SoftwareCriteria;
import ng.com.dpros.customermanager.service.SoftwareQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ng.com.dpros.customermanager.domain.Software}.
 */
@RestController
@RequestMapping("/api")
public class SoftwareResource {

    private final Logger log = LoggerFactory.getLogger(SoftwareResource.class);

    private static final String ENTITY_NAME = "software";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoftwareService softwareService;

    private final SoftwareQueryService softwareQueryService;

    public SoftwareResource(SoftwareService softwareService, SoftwareQueryService softwareQueryService) {
        this.softwareService = softwareService;
        this.softwareQueryService = softwareQueryService;
    }

    /**
     * {@code POST  /software} : Create a new software.
     *
     * @param softwareDTO the softwareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new softwareDTO, or with status {@code 400 (Bad Request)} if the software has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/software")
    public ResponseEntity<SoftwareDTO> createSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to save Software : {}", softwareDTO);
        if (softwareDTO.getId() != null) {
            throw new BadRequestAlertException("A new software cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoftwareDTO result = softwareService.save(softwareDTO);
        return ResponseEntity.created(new URI("/api/software/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /software} : Updates an existing software.
     *
     * @param softwareDTO the softwareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated softwareDTO,
     * or with status {@code 400 (Bad Request)} if the softwareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the softwareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/software")
    public ResponseEntity<SoftwareDTO> updateSoftware(@Valid @RequestBody SoftwareDTO softwareDTO) throws URISyntaxException {
        log.debug("REST request to update Software : {}", softwareDTO);
        if (softwareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoftwareDTO result = softwareService.save(softwareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, softwareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /software} : get all the software.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of software in body.
     */
    @GetMapping("/software")
    public ResponseEntity<List<SoftwareDTO>> getAllSoftware(SoftwareCriteria criteria) {
        log.debug("REST request to get Software by criteria: {}", criteria);
        List<SoftwareDTO> entityList = softwareQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /software/count} : count all the software.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/software/count")
    public ResponseEntity<Long> countSoftware(SoftwareCriteria criteria) {
        log.debug("REST request to count Software by criteria: {}", criteria);
        return ResponseEntity.ok().body(softwareQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /software/:id} : get the "id" software.
     *
     * @param id the id of the softwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the softwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/software/{id}")
    public ResponseEntity<SoftwareDTO> getSoftware(@PathVariable Long id) {
        log.debug("REST request to get Software : {}", id);
        Optional<SoftwareDTO> softwareDTO = softwareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(softwareDTO);
    }

    /**
     * {@code DELETE  /software/:id} : delete the "id" software.
     *
     * @param id the id of the softwareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/software/{id}")
    public ResponseEntity<Void> deleteSoftware(@PathVariable Long id) {
        log.debug("REST request to delete Software : {}", id);
        softwareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/software?query=:query} : search for the software corresponding
     * to the query.
     *
     * @param query the query of the software search.
     * @return the result of the search.
     */
    @GetMapping("/_search/software")
    public List<SoftwareDTO> searchSoftware(@RequestParam String query) {
        log.debug("REST request to search Software for query {}", query);
        return softwareService.search(query);
    }
}
