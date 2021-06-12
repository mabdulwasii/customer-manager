package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.service.HardwareService;
import ng.com.dpros.customermanager.web.rest.errors.BadRequestAlertException;
import ng.com.dpros.customermanager.service.dto.HardwareDTO;
import ng.com.dpros.customermanager.service.dto.HardwareCriteria;
import ng.com.dpros.customermanager.service.HardwareQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link ng.com.dpros.customermanager.domain.Hardware}.
 */
@RestController
@RequestMapping("/api")
public class HardwareResource {

    private final Logger log = LoggerFactory.getLogger(HardwareResource.class);

    private static final String ENTITY_NAME = "hardware";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HardwareService hardwareService;

    private final HardwareQueryService hardwareQueryService;

    public HardwareResource(HardwareService hardwareService, HardwareQueryService hardwareQueryService) {
        this.hardwareService = hardwareService;
        this.hardwareQueryService = hardwareQueryService;
    }

    /**
     * {@code POST  /hardware} : Create a new hardware.
     *
     * @param hardwareDTO the hardwareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hardwareDTO, or with status {@code 400 (Bad Request)} if the hardware has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hardware")
    public ResponseEntity<HardwareDTO> createHardware(@Valid @RequestBody HardwareDTO hardwareDTO) throws URISyntaxException {
        log.debug("REST request to save Hardware : {}", hardwareDTO);
        if (hardwareDTO.getId() != null) {
            throw new BadRequestAlertException("A new hardware cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HardwareDTO result = hardwareService.save(hardwareDTO);
        return ResponseEntity.created(new URI("/api/hardware/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hardware} : Updates an existing hardware.
     *
     * @param hardwareDTO the hardwareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hardwareDTO,
     * or with status {@code 400 (Bad Request)} if the hardwareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hardwareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hardware")
    public ResponseEntity<HardwareDTO> updateHardware(@Valid @RequestBody HardwareDTO hardwareDTO) throws URISyntaxException {
        log.debug("REST request to update Hardware : {}", hardwareDTO);
        if (hardwareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HardwareDTO result = hardwareService.save(hardwareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hardwareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hardware} : get all the hardware.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hardware in body.
     */
    @GetMapping("/hardware")
    public ResponseEntity<List<HardwareDTO>> getAllHardware(HardwareCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hardware by criteria: {}", criteria);
        Page<HardwareDTO> page = hardwareQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hardware/count} : count all the hardware.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hardware/count")
    public ResponseEntity<Long> countHardware(HardwareCriteria criteria) {
        log.debug("REST request to count Hardware by criteria: {}", criteria);
        return ResponseEntity.ok().body(hardwareQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hardware/:id} : get the "id" hardware.
     *
     * @param id the id of the hardwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hardwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hardware/{id}")
    public ResponseEntity<HardwareDTO> getHardware(@PathVariable Long id) {
        log.debug("REST request to get Hardware : {}", id);
        Optional<HardwareDTO> hardwareDTO = hardwareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hardwareDTO);
    }

    /**
     * {@code DELETE  /hardware/:id} : delete the "id" hardware.
     *
     * @param id the id of the hardwareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hardware/{id}")
    public ResponseEntity<Void> deleteHardware(@PathVariable Long id) {
        log.debug("REST request to delete Hardware : {}", id);
        hardwareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/hardware?query=:query} : search for the hardware corresponding
     * to the query.
     *
     * @param query the query of the hardware search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/hardware")
    public ResponseEntity<List<HardwareDTO>> searchHardware(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Hardware for query {}", query);
        Page<HardwareDTO> page = hardwareService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
