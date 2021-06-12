package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.service.TrainingService;
import ng.com.dpros.customermanager.web.rest.errors.BadRequestAlertException;
import ng.com.dpros.customermanager.service.dto.TrainingDTO;

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
 * REST controller for managing {@link ng.com.dpros.customermanager.domain.Training}.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingService trainingService;

    public TrainingResource(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    /**
     * {@code POST  /trainings} : Create a new training.
     *
     * @param trainingDTO the trainingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingDTO, or with status {@code 400 (Bad Request)} if the training has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainings")
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to save Training : {}", trainingDTO);
        if (trainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainings} : Updates an existing training.
     *
     * @param trainingDTO the trainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingDTO,
     * or with status {@code 400 (Bad Request)} if the trainingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainings")
    public ResponseEntity<TrainingDTO> updateTraining(@Valid @RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to update Training : {}", trainingDTO);
        if (trainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trainings} : get all the trainings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainings in body.
     */
    @GetMapping("/trainings")
    public List<TrainingDTO> getAllTrainings() {
        log.debug("REST request to get all Trainings");
        return trainingService.findAll();
    }

    /**
     * {@code GET  /trainings/:id} : get the "id" training.
     *
     * @param id the id of the trainingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainings/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<TrainingDTO> trainingDTO = trainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingDTO);
    }

    /**
     * {@code DELETE  /trainings/:id} : delete the "id" training.
     *
     * @param id the id of the trainingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trainings?query=:query} : search for the training corresponding
     * to the query.
     *
     * @param query the query of the training search.
     * @return the result of the search.
     */
    @GetMapping("/_search/trainings")
    public List<TrainingDTO> searchTrainings(@RequestParam String query) {
        log.debug("REST request to search Trainings for query {}", query);
        return trainingService.search(query);
    }
}
