package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Training;
import ng.com.dpros.customermanager.domain.Profile;
import ng.com.dpros.customermanager.domain.ServiceCategory;
import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.repository.TrainingRepository;
import ng.com.dpros.customermanager.repository.search.TrainingSearchRepository;
import ng.com.dpros.customermanager.service.TrainingService;
import ng.com.dpros.customermanager.service.dto.TrainingDTO;
import ng.com.dpros.customermanager.service.mapper.TrainingMapper;

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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrainingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingMapper trainingMapper;

    @Autowired
    private TrainingService trainingService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.TrainingSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingSearchRepository mockTrainingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .name(DEFAULT_NAME)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        training.setProfile(profile);
        // Add required entity
        ServiceCategory serviceCategory;
        if (TestUtil.findAll(em, ServiceCategory.class).isEmpty()) {
            serviceCategory = ServiceCategoryResourceIT.createEntity(em);
            em.persist(serviceCategory);
            em.flush();
        } else {
            serviceCategory = TestUtil.findAll(em, ServiceCategory.class).get(0);
        }
        training.setServiceCategory(serviceCategory);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        training.setServices(services);
        return training;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createUpdatedEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        training.setProfile(profile);
        // Add required entity
        ServiceCategory serviceCategory;
        if (TestUtil.findAll(em, ServiceCategory.class).isEmpty()) {
            serviceCategory = ServiceCategoryResourceIT.createUpdatedEntity(em);
            em.persist(serviceCategory);
            em.flush();
        } else {
            serviceCategory = TestUtil.findAll(em, ServiceCategory.class).get(0);
        }
        training.setServiceCategory(serviceCategory);
        // Add required entity
        Services services;
        if (TestUtil.findAll(em, Services.class).isEmpty()) {
            services = ServicesResourceIT.createUpdatedEntity(em);
            em.persist(services);
            em.flush();
        } else {
            services = TestUtil.findAll(em, Services.class).get(0);
        }
        training.setServices(services);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraining.getAmount()).isEqualTo(DEFAULT_AMOUNT);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).save(testTraining);
    }

    @Test
    @Transactional
    public void createTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training with an existing ID
        training.setId(1L);
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(0)).save(training);
    }


    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT);
        TrainingDTO trainingDTO = trainingMapper.toDto(updatedTraining);

        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraining.getAmount()).isEqualTo(UPDATED_AMOUNT);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).save(testTraining);
    }

    @Test
    @Transactional
    public void updateNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(0)).save(training);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).deleteById(training.getId());
    }

    @Test
    @Transactional
    public void searchTraining() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        when(mockTrainingSearchRepository.search(queryStringQuery("id:" + training.getId())))
            .thenReturn(Collections.singletonList(training));

        // Search the training
        restTrainingMockMvc.perform(get("/api/_search/trainings?query=id:" + training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
}
