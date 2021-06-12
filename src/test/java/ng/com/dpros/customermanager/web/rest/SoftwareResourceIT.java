package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Software;
import ng.com.dpros.customermanager.domain.ServiceCategory;
import ng.com.dpros.customermanager.domain.Payment;
import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.domain.Review;
import ng.com.dpros.customermanager.domain.Profile;
import ng.com.dpros.customermanager.repository.SoftwareRepository;
import ng.com.dpros.customermanager.repository.search.SoftwareSearchRepository;
import ng.com.dpros.customermanager.service.SoftwareService;
import ng.com.dpros.customermanager.service.dto.SoftwareDTO;
import ng.com.dpros.customermanager.service.mapper.SoftwareMapper;
import ng.com.dpros.customermanager.service.dto.SoftwareCriteria;
import ng.com.dpros.customermanager.service.SoftwareQueryService;

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

import ng.com.dpros.customermanager.domain.enumeration.Technology;
/**
 * Integration tests for the {@link SoftwareResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SoftwareResourceIT {

    private static final Technology DEFAULT_TECHNOLOGY = Technology.WEB_DESIGN;
    private static final Technology UPDATED_TECHNOLOGY = Technology.WEB_DEVELOPMENT;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareService softwareService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.SoftwareSearchRepositoryMockConfiguration
     */
    @Autowired
    private SoftwareSearchRepository mockSoftwareSearchRepository;

    @Autowired
    private SoftwareQueryService softwareQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoftwareMockMvc;

    private Software software;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createEntity(EntityManager em) {
        Software software = new Software()
            .technology(DEFAULT_TECHNOLOGY)
            .amount(DEFAULT_AMOUNT)
            .details(DEFAULT_DETAILS);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        software.setProfile(profile);
        return software;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Software createUpdatedEntity(EntityManager em) {
        Software software = new Software()
            .technology(UPDATED_TECHNOLOGY)
            .amount(UPDATED_AMOUNT)
            .details(UPDATED_DETAILS);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createUpdatedEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        software.setProfile(profile);
        return software;
    }

    @BeforeEach
    public void initTest() {
        software = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoftware() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();
        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);
        restSoftwareMockMvc.perform(post("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isCreated());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate + 1);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testSoftware.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSoftware.getDetails()).isEqualTo(DEFAULT_DETAILS);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).save(testSoftware);
    }

    @Test
    @Transactional
    public void createSoftwareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = softwareRepository.findAll().size();

        // Create the Software with an existing ID
        software.setId(1L);
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoftwareMockMvc.perform(post("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeCreate);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(0)).save(software);
    }


    @Test
    @Transactional
    public void getAllSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList
        restSoftwareMockMvc.perform(get("/api/software?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get the software
        restSoftwareMockMvc.perform(get("/api/software/{id}", software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(software.getId().intValue()))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }


    @Test
    @Transactional
    public void getSoftwareByIdFiltering() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        Long id = software.getId();

        defaultSoftwareShouldBeFound("id.equals=" + id);
        defaultSoftwareShouldNotBeFound("id.notEquals=" + id);

        defaultSoftwareShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSoftwareShouldNotBeFound("id.greaterThan=" + id);

        defaultSoftwareShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSoftwareShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSoftwareByTechnologyIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where technology equals to DEFAULT_TECHNOLOGY
        defaultSoftwareShouldBeFound("technology.equals=" + DEFAULT_TECHNOLOGY);

        // Get all the softwareList where technology equals to UPDATED_TECHNOLOGY
        defaultSoftwareShouldNotBeFound("technology.equals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllSoftwareByTechnologyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where technology not equals to DEFAULT_TECHNOLOGY
        defaultSoftwareShouldNotBeFound("technology.notEquals=" + DEFAULT_TECHNOLOGY);

        // Get all the softwareList where technology not equals to UPDATED_TECHNOLOGY
        defaultSoftwareShouldBeFound("technology.notEquals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllSoftwareByTechnologyIsInShouldWork() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where technology in DEFAULT_TECHNOLOGY or UPDATED_TECHNOLOGY
        defaultSoftwareShouldBeFound("technology.in=" + DEFAULT_TECHNOLOGY + "," + UPDATED_TECHNOLOGY);

        // Get all the softwareList where technology equals to UPDATED_TECHNOLOGY
        defaultSoftwareShouldNotBeFound("technology.in=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllSoftwareByTechnologyIsNullOrNotNull() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where technology is not null
        defaultSoftwareShouldBeFound("technology.specified=true");

        // Get all the softwareList where technology is null
        defaultSoftwareShouldNotBeFound("technology.specified=false");
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount equals to DEFAULT_AMOUNT
        defaultSoftwareShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount equals to UPDATED_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount not equals to DEFAULT_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount not equals to UPDATED_AMOUNT
        defaultSoftwareShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultSoftwareShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the softwareList where amount equals to UPDATED_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount is not null
        defaultSoftwareShouldBeFound("amount.specified=true");

        // Get all the softwareList where amount is null
        defaultSoftwareShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultSoftwareShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount is greater than or equal to UPDATED_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount is less than or equal to DEFAULT_AMOUNT
        defaultSoftwareShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount is less than or equal to SMALLER_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount is less than DEFAULT_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount is less than UPDATED_AMOUNT
        defaultSoftwareShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSoftwareByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where amount is greater than DEFAULT_AMOUNT
        defaultSoftwareShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the softwareList where amount is greater than SMALLER_AMOUNT
        defaultSoftwareShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllSoftwareByDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details equals to DEFAULT_DETAILS
        defaultSoftwareShouldBeFound("details.equals=" + DEFAULT_DETAILS);

        // Get all the softwareList where details equals to UPDATED_DETAILS
        defaultSoftwareShouldNotBeFound("details.equals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllSoftwareByDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details not equals to DEFAULT_DETAILS
        defaultSoftwareShouldNotBeFound("details.notEquals=" + DEFAULT_DETAILS);

        // Get all the softwareList where details not equals to UPDATED_DETAILS
        defaultSoftwareShouldBeFound("details.notEquals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllSoftwareByDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details in DEFAULT_DETAILS or UPDATED_DETAILS
        defaultSoftwareShouldBeFound("details.in=" + DEFAULT_DETAILS + "," + UPDATED_DETAILS);

        // Get all the softwareList where details equals to UPDATED_DETAILS
        defaultSoftwareShouldNotBeFound("details.in=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllSoftwareByDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details is not null
        defaultSoftwareShouldBeFound("details.specified=true");

        // Get all the softwareList where details is null
        defaultSoftwareShouldNotBeFound("details.specified=false");
    }
                @Test
    @Transactional
    public void getAllSoftwareByDetailsContainsSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details contains DEFAULT_DETAILS
        defaultSoftwareShouldBeFound("details.contains=" + DEFAULT_DETAILS);

        // Get all the softwareList where details contains UPDATED_DETAILS
        defaultSoftwareShouldNotBeFound("details.contains=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllSoftwareByDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        // Get all the softwareList where details does not contain DEFAULT_DETAILS
        defaultSoftwareShouldNotBeFound("details.doesNotContain=" + DEFAULT_DETAILS);

        // Get all the softwareList where details does not contain UPDATED_DETAILS
        defaultSoftwareShouldBeFound("details.doesNotContain=" + UPDATED_DETAILS);
    }


    @Test
    @Transactional
    public void getAllSoftwareByServiceCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        ServiceCategory serviceCategory = ServiceCategoryResourceIT.createEntity(em);
        em.persist(serviceCategory);
        em.flush();
        software.setServiceCategory(serviceCategory);
        softwareRepository.saveAndFlush(software);
        Long serviceCategoryId = serviceCategory.getId();

        // Get all the softwareList where serviceCategory equals to serviceCategoryId
        defaultSoftwareShouldBeFound("serviceCategoryId.equals=" + serviceCategoryId);

        // Get all the softwareList where serviceCategory equals to serviceCategoryId + 1
        defaultSoftwareShouldNotBeFound("serviceCategoryId.equals=" + (serviceCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSoftwareByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        software.addPayment(payment);
        softwareRepository.saveAndFlush(software);
        Long paymentId = payment.getId();

        // Get all the softwareList where payment equals to paymentId
        defaultSoftwareShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the softwareList where payment equals to paymentId + 1
        defaultSoftwareShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }


    @Test
    @Transactional
    public void getAllSoftwareByServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        Services services = ServicesResourceIT.createEntity(em);
        em.persist(services);
        em.flush();
        software.setServices(services);
        softwareRepository.saveAndFlush(software);
        Long servicesId = services.getId();

        // Get all the softwareList where services equals to servicesId
        defaultSoftwareShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the softwareList where services equals to servicesId + 1
        defaultSoftwareShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }


    @Test
    @Transactional
    public void getAllSoftwareByReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        Review review = ReviewResourceIT.createEntity(em);
        em.persist(review);
        em.flush();
        software.addReview(review);
        softwareRepository.saveAndFlush(software);
        Long reviewId = review.getId();

        // Get all the softwareList where review equals to reviewId
        defaultSoftwareShouldBeFound("reviewId.equals=" + reviewId);

        // Get all the softwareList where review equals to reviewId + 1
        defaultSoftwareShouldNotBeFound("reviewId.equals=" + (reviewId + 1));
    }


    @Test
    @Transactional
    public void getAllSoftwareByProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        Profile profile = software.getProfile();
        softwareRepository.saveAndFlush(software);
        Long profileId = profile.getId();

        // Get all the softwareList where profile equals to profileId
        defaultSoftwareShouldBeFound("profileId.equals=" + profileId);

        // Get all the softwareList where profile equals to profileId + 1
        defaultSoftwareShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSoftwareShouldBeFound(String filter) throws Exception {
        restSoftwareMockMvc.perform(get("/api/software?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));

        // Check, that the count call also returns 1
        restSoftwareMockMvc.perform(get("/api/software/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSoftwareShouldNotBeFound(String filter) throws Exception {
        restSoftwareMockMvc.perform(get("/api/software?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSoftwareMockMvc.perform(get("/api/software/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSoftware() throws Exception {
        // Get the software
        restSoftwareMockMvc.perform(get("/api/software/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Update the software
        Software updatedSoftware = softwareRepository.findById(software.getId()).get();
        // Disconnect from session so that the updates on updatedSoftware are not directly saved in db
        em.detach(updatedSoftware);
        updatedSoftware
            .technology(UPDATED_TECHNOLOGY)
            .amount(UPDATED_AMOUNT)
            .details(UPDATED_DETAILS);
        SoftwareDTO softwareDTO = softwareMapper.toDto(updatedSoftware);

        restSoftwareMockMvc.perform(put("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isOk());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);
        Software testSoftware = softwareList.get(softwareList.size() - 1);
        assertThat(testSoftware.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testSoftware.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSoftware.getDetails()).isEqualTo(UPDATED_DETAILS);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).save(testSoftware);
    }

    @Test
    @Transactional
    public void updateNonExistingSoftware() throws Exception {
        int databaseSizeBeforeUpdate = softwareRepository.findAll().size();

        // Create the Software
        SoftwareDTO softwareDTO = softwareMapper.toDto(software);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoftwareMockMvc.perform(put("/api/software")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(softwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Software in the database
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(0)).save(software);
    }

    @Test
    @Transactional
    public void deleteSoftware() throws Exception {
        // Initialize the database
        softwareRepository.saveAndFlush(software);

        int databaseSizeBeforeDelete = softwareRepository.findAll().size();

        // Delete the software
        restSoftwareMockMvc.perform(delete("/api/software/{id}", software.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Software> softwareList = softwareRepository.findAll();
        assertThat(softwareList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Software in Elasticsearch
        verify(mockSoftwareSearchRepository, times(1)).deleteById(software.getId());
    }

    @Test
    @Transactional
    public void searchSoftware() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        softwareRepository.saveAndFlush(software);
        when(mockSoftwareSearchRepository.search(queryStringQuery("id:" + software.getId())))
            .thenReturn(Collections.singletonList(software));

        // Search the software
        restSoftwareMockMvc.perform(get("/api/_search/software?query=id:" + software.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(software.getId().intValue())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
}
