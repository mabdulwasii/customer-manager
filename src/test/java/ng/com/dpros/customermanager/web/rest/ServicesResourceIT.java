package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.domain.Hardware;
import ng.com.dpros.customermanager.domain.Training;
import ng.com.dpros.customermanager.domain.Software;
import ng.com.dpros.customermanager.repository.ServiceRepository;
import ng.com.dpros.customermanager.repository.search.ServiceSearchRepository;
import ng.com.dpros.customermanager.service.ServiceService;
import ng.com.dpros.customermanager.service.ServicesQueryService;
import ng.com.dpros.customermanager.service.dto.ServiceDTO;
import ng.com.dpros.customermanager.service.mapper.ServiceMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServicesResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServicesResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_AGREE = false;
    private static final Boolean UPDATED_AGREE = true;

    private static final String DEFAULT_SIGN_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_SIGN_DOC_URL = "BBBBBBBBBB";

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ServiceService serviceService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.ServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceSearchRepository mockServiceSearchRepository;

    @Autowired
    private ServicesQueryService serviceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceMockMvc;

    private Services service;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createEntity(EntityManager em) {
        Services service = new Services()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .agree(DEFAULT_AGREE)
            .signDocUrl(DEFAULT_SIGN_DOC_URL);
        return service;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createUpdatedEntity(EntityManager em) {
        Services service = new Services()
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .agree(UPDATED_AGREE)
            .signDocUrl(UPDATED_SIGN_DOC_URL);
        return service;
    }

    @BeforeEach
    public void initTest() {
        service = createEntity(em);
    }

    @Test
    @Transactional
    public void createService() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();
        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);
        restServiceMockMvc.perform(post("/api/services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Service in the database
        List<Services> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate + 1);
        Services testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testService.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testService.isAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testService.getSignDocUrl()).isEqualTo(DEFAULT_SIGN_DOC_URL);

        // Validate the Service in Elasticsearch
        verify(mockServiceSearchRepository, times(1)).save(testService);
    }

    @Test
    @Transactional
    public void createServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service with an existing ID
        service.setId(1L);
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc.perform(post("/api/services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Services> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Service in Elasticsearch
        verify(mockServiceSearchRepository, times(0)).save(service);
    }


    @Test
    @Transactional
    public void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc.perform(get("/api/services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].signDocUrl").value(hasItem(DEFAULT_SIGN_DOC_URL)));
    }

    @Test
    @Transactional
    public void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.agree").value(DEFAULT_AGREE.booleanValue()))
            .andExpect(jsonPath("$.signDocUrl").value(DEFAULT_SIGN_DOC_URL));
    }


    @Test
    @Transactional
    public void getServicesByIdFiltering() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        Long id = service.getId();

        defaultServiceShouldBeFound("id.equals=" + id);
        defaultServiceShouldNotBeFound("id.notEquals=" + id);

        defaultServiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServicesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description equals to DEFAULT_DESCRIPTION
        defaultServiceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceList where description equals to UPDATED_DESCRIPTION
        defaultServiceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServicesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description not equals to DEFAULT_DESCRIPTION
        defaultServiceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceList where description not equals to UPDATED_DESCRIPTION
        defaultServiceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServicesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultServiceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the serviceList where description equals to UPDATED_DESCRIPTION
        defaultServiceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServicesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description is not null
        defaultServiceShouldBeFound("description.specified=true");

        // Get all the serviceList where description is null
        defaultServiceShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllServicesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description contains DEFAULT_DESCRIPTION
        defaultServiceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the serviceList where description contains UPDATED_DESCRIPTION
        defaultServiceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServicesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where description does not contain DEFAULT_DESCRIPTION
        defaultServiceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the serviceList where description does not contain UPDATED_DESCRIPTION
        defaultServiceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllServicesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where startDate equals to DEFAULT_START_DATE
        defaultServiceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the serviceList where startDate equals to UPDATED_START_DATE
        defaultServiceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServicesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where startDate not equals to DEFAULT_START_DATE
        defaultServiceShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the serviceList where startDate not equals to UPDATED_START_DATE
        defaultServiceShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServicesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultServiceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the serviceList where startDate equals to UPDATED_START_DATE
        defaultServiceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllServicesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where startDate is not null
        defaultServiceShouldBeFound("startDate.specified=true");

        // Get all the serviceList where startDate is null
        defaultServiceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServicesByAgreeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where agree equals to DEFAULT_AGREE
        defaultServiceShouldBeFound("agree.equals=" + DEFAULT_AGREE);

        // Get all the serviceList where agree equals to UPDATED_AGREE
        defaultServiceShouldNotBeFound("agree.equals=" + UPDATED_AGREE);
    }

    @Test
    @Transactional
    public void getAllServicesByAgreeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where agree not equals to DEFAULT_AGREE
        defaultServiceShouldNotBeFound("agree.notEquals=" + DEFAULT_AGREE);

        // Get all the serviceList where agree not equals to UPDATED_AGREE
        defaultServiceShouldBeFound("agree.notEquals=" + UPDATED_AGREE);
    }

    @Test
    @Transactional
    public void getAllServicesByAgreeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where agree in DEFAULT_AGREE or UPDATED_AGREE
        defaultServiceShouldBeFound("agree.in=" + DEFAULT_AGREE + "," + UPDATED_AGREE);

        // Get all the serviceList where agree equals to UPDATED_AGREE
        defaultServiceShouldNotBeFound("agree.in=" + UPDATED_AGREE);
    }

    @Test
    @Transactional
    public void getAllServicesByAgreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where agree is not null
        defaultServiceShouldBeFound("agree.specified=true");

        // Get all the serviceList where agree is null
        defaultServiceShouldNotBeFound("agree.specified=false");
    }

    @Test
    @Transactional
    public void getAllServicesBySignDocUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl equals to DEFAULT_SIGN_DOC_URL
        defaultServiceShouldBeFound("signDocUrl.equals=" + DEFAULT_SIGN_DOC_URL);

        // Get all the serviceList where signDocUrl equals to UPDATED_SIGN_DOC_URL
        defaultServiceShouldNotBeFound("signDocUrl.equals=" + UPDATED_SIGN_DOC_URL);
    }

    @Test
    @Transactional
    public void getAllServicesBySignDocUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl not equals to DEFAULT_SIGN_DOC_URL
        defaultServiceShouldNotBeFound("signDocUrl.notEquals=" + DEFAULT_SIGN_DOC_URL);

        // Get all the serviceList where signDocUrl not equals to UPDATED_SIGN_DOC_URL
        defaultServiceShouldBeFound("signDocUrl.notEquals=" + UPDATED_SIGN_DOC_URL);
    }

    @Test
    @Transactional
    public void getAllServicesBySignDocUrlIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl in DEFAULT_SIGN_DOC_URL or UPDATED_SIGN_DOC_URL
        defaultServiceShouldBeFound("signDocUrl.in=" + DEFAULT_SIGN_DOC_URL + "," + UPDATED_SIGN_DOC_URL);

        // Get all the serviceList where signDocUrl equals to UPDATED_SIGN_DOC_URL
        defaultServiceShouldNotBeFound("signDocUrl.in=" + UPDATED_SIGN_DOC_URL);
    }

    @Test
    @Transactional
    public void getAllServicesBySignDocUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl is not null
        defaultServiceShouldBeFound("signDocUrl.specified=true");

        // Get all the serviceList where signDocUrl is null
        defaultServiceShouldNotBeFound("signDocUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllServicesBySignDocUrlContainsSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl contains DEFAULT_SIGN_DOC_URL
        defaultServiceShouldBeFound("signDocUrl.contains=" + DEFAULT_SIGN_DOC_URL);

        // Get all the serviceList where signDocUrl contains UPDATED_SIGN_DOC_URL
        defaultServiceShouldNotBeFound("signDocUrl.contains=" + UPDATED_SIGN_DOC_URL);
    }

    @Test
    @Transactional
    public void getAllServicesBySignDocUrlNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where signDocUrl does not contain DEFAULT_SIGN_DOC_URL
        defaultServiceShouldNotBeFound("signDocUrl.doesNotContain=" + DEFAULT_SIGN_DOC_URL);

        // Get all the serviceList where signDocUrl does not contain UPDATED_SIGN_DOC_URL
        defaultServiceShouldBeFound("signDocUrl.doesNotContain=" + UPDATED_SIGN_DOC_URL);
    }


    @Test
    @Transactional
    public void getAllServicesByHardwareIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);
        Hardware hardware = HardwareResourceIT.createEntity(em);
        em.persist(hardware);
        em.flush();
        service.setHardware(hardware);
        hardware.setServices(service);
        serviceRepository.saveAndFlush(service);
        Long hardwareId = hardware.getId();

        // Get all the serviceList where hardware equals to hardwareId
        defaultServiceShouldBeFound("hardwareId.equals=" + hardwareId);

        // Get all the serviceList where hardware equals to hardwareId + 1
        defaultServiceShouldNotBeFound("hardwareId.equals=" + (hardwareId + 1));
    }


    @Test
    @Transactional
    public void getAllServicesByTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);
        Training training = TrainingResourceIT.createEntity(em);
        em.persist(training);
        em.flush();
        service.setTraining(training);
        training.setServices(service);
        serviceRepository.saveAndFlush(service);
        Long trainingId = training.getId();

        // Get all the serviceList where training equals to trainingId
        defaultServiceShouldBeFound("trainingId.equals=" + trainingId);

        // Get all the serviceList where training equals to trainingId + 1
        defaultServiceShouldNotBeFound("trainingId.equals=" + (trainingId + 1));
    }


    @Test
    @Transactional
    public void getAllServicesBySoftwareIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);
        Software software = SoftwareResourceIT.createEntity(em);
        em.persist(software);
        em.flush();
        service.setSoftware(software);
        software.setServices(service);
        serviceRepository.saveAndFlush(service);
        Long softwareId = software.getId();

        // Get all the serviceList where software equals to softwareId
        defaultServiceShouldBeFound("softwareId.equals=" + softwareId);

        // Get all the serviceList where software equals to softwareId + 1
        defaultServiceShouldNotBeFound("softwareId.equals=" + (softwareId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceShouldBeFound(String filter) throws Exception {
        restServiceMockMvc.perform(get("/api/services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].signDocUrl").value(hasItem(DEFAULT_SIGN_DOC_URL)));

        // Check, that the count call also returns 1
        restServiceMockMvc.perform(get("/api/services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceShouldNotBeFound(String filter) throws Exception {
        restServiceMockMvc.perform(get("/api/services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceMockMvc.perform(get("/api/services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service
        Services updatedService = serviceRepository.findById(service.getId()).get();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .agree(UPDATED_AGREE)
            .signDocUrl(UPDATED_SIGN_DOC_URL);
        ServiceDTO serviceDTO = serviceMapper.toDto(updatedService);

        restServiceMockMvc.perform(put("/api/services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceDTO)))
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Services> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Services testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testService.isAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testService.getSignDocUrl()).isEqualTo(UPDATED_SIGN_DOC_URL);

        // Validate the Service in Elasticsearch
        verify(mockServiceSearchRepository, times(1)).save(testService);
    }

    @Test
    @Transactional
    public void updateNonExistingService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc.perform(put("/api/services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Services> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Service in Elasticsearch
        verify(mockServiceSearchRepository, times(0)).save(service);
    }

    @Test
    @Transactional
    public void deleteService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        int databaseSizeBeforeDelete = serviceRepository.findAll().size();

        // Delete the service
        restServiceMockMvc.perform(delete("/api/services/{id}", service.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Services> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Service in Elasticsearch
        verify(mockServiceSearchRepository, times(1)).deleteById(service.getId());
    }

    @Test
    @Transactional
    public void searchService() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        serviceRepository.saveAndFlush(service);
        when(mockServiceSearchRepository.search(queryStringQuery("id:" + service.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(service), PageRequest.of(0, 1), 1));

        // Search the service
        restServiceMockMvc.perform(get("/api/_search/services?query=id:" + service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].signDocUrl").value(hasItem(DEFAULT_SIGN_DOC_URL)));
    }
}
