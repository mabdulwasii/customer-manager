package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Trial;
import ng.com.dpros.customermanager.repository.TrialRepository;
import ng.com.dpros.customermanager.repository.search.TrialSearchRepository;
import ng.com.dpros.customermanager.service.TrialService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrialResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrialResourceIT {

    private static final Float DEFAULT_NAME = 1F;
    private static final Float UPDATED_NAME = 2F;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_EXAMPLE = 1D;
    private static final Double UPDATED_EXAMPLE = 2D;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialService trialService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.TrialSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrialSearchRepository mockTrialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrialMockMvc;

    private Trial trial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trial createEntity(EntityManager em) {
        Trial trial = new Trial()
            .name(DEFAULT_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .example(DEFAULT_EXAMPLE);
        return trial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trial createUpdatedEntity(EntityManager em) {
        Trial trial = new Trial()
            .name(UPDATED_NAME)
            .fullName(UPDATED_FULL_NAME)
            .example(UPDATED_EXAMPLE);
        return trial;
    }

    @BeforeEach
    public void initTest() {
        trial = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrial() throws Exception {
        int databaseSizeBeforeCreate = trialRepository.findAll().size();
        // Create the Trial
        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isCreated());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeCreate + 1);
        Trial testTrial = trialList.get(trialList.size() - 1);
        assertThat(testTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrial.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testTrial.getExample()).isEqualTo(DEFAULT_EXAMPLE);

        // Validate the Trial in Elasticsearch
        verify(mockTrialSearchRepository, times(1)).save(testTrial);
    }

    @Test
    @Transactional
    public void createTrialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trialRepository.findAll().size();

        // Create the Trial with an existing ID
        trial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeCreate);

        // Validate the Trial in Elasticsearch
        verify(mockTrialSearchRepository, times(0)).save(trial);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trialRepository.findAll().size();
        // set the field null
        trial.setName(null);

        // Create the Trial, which fails.


        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trialRepository.findAll().size();
        // set the field null
        trial.setFullName(null);

        // Create the Trial, which fails.


        restTrialMockMvc.perform(post("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrials() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        // Get all the trialList
        restTrialMockMvc.perform(get("/api/trials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.doubleValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].example").value(hasItem(DEFAULT_EXAMPLE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTrial() throws Exception {
        // Initialize the database
        trialRepository.saveAndFlush(trial);

        // Get the trial
        restTrialMockMvc.perform(get("/api/trials/{id}", trial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.doubleValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.example").value(DEFAULT_EXAMPLE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTrial() throws Exception {
        // Get the trial
        restTrialMockMvc.perform(get("/api/trials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrial() throws Exception {
        // Initialize the database
        trialService.save(trial);

        int databaseSizeBeforeUpdate = trialRepository.findAll().size();

        // Update the trial
        Trial updatedTrial = trialRepository.findById(trial.getId()).get();
        // Disconnect from session so that the updates on updatedTrial are not directly saved in db
        em.detach(updatedTrial);
        updatedTrial
            .name(UPDATED_NAME)
            .fullName(UPDATED_FULL_NAME)
            .example(UPDATED_EXAMPLE);

        restTrialMockMvc.perform(put("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrial)))
            .andExpect(status().isOk());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeUpdate);
        Trial testTrial = trialList.get(trialList.size() - 1);
        assertThat(testTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrial.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testTrial.getExample()).isEqualTo(UPDATED_EXAMPLE);

        // Validate the Trial in Elasticsearch
        verify(mockTrialSearchRepository, times(2)).save(testTrial);
    }

    @Test
    @Transactional
    public void updateNonExistingTrial() throws Exception {
        int databaseSizeBeforeUpdate = trialRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrialMockMvc.perform(put("/api/trials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trial)))
            .andExpect(status().isBadRequest());

        // Validate the Trial in the database
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Trial in Elasticsearch
        verify(mockTrialSearchRepository, times(0)).save(trial);
    }

    @Test
    @Transactional
    public void deleteTrial() throws Exception {
        // Initialize the database
        trialService.save(trial);

        int databaseSizeBeforeDelete = trialRepository.findAll().size();

        // Delete the trial
        restTrialMockMvc.perform(delete("/api/trials/{id}", trial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trial> trialList = trialRepository.findAll();
        assertThat(trialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Trial in Elasticsearch
        verify(mockTrialSearchRepository, times(1)).deleteById(trial.getId());
    }

    @Test
    @Transactional
    public void searchTrial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        trialService.save(trial);
        when(mockTrialSearchRepository.search(queryStringQuery("id:" + trial.getId())))
            .thenReturn(Collections.singletonList(trial));

        // Search the trial
        restTrialMockMvc.perform(get("/api/_search/trials?query=id:" + trial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.doubleValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].example").value(hasItem(DEFAULT_EXAMPLE.doubleValue())));
    }
}
