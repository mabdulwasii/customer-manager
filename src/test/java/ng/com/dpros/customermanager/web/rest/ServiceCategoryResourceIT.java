package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.ServiceCategory;
import ng.com.dpros.customermanager.repository.ServiceCategoryRepository;
import ng.com.dpros.customermanager.repository.search.ServiceCategorySearchRepository;
import ng.com.dpros.customermanager.service.ServiceCategoryService;
import ng.com.dpros.customermanager.service.dto.ServiceCategoryDTO;
import ng.com.dpros.customermanager.service.mapper.ServiceCategoryMapper;

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
 * Integration tests for the {@link ServiceCategoryResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FIXED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FIXED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_HAS_FIXED_PRICE = false;
    private static final Boolean UPDATED_HAS_FIXED_PRICE = true;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private ServiceCategoryMapper serviceCategoryMapper;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.ServiceCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceCategorySearchRepository mockServiceCategorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceCategoryMockMvc;

    private ServiceCategory serviceCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceCategory createEntity(EntityManager em) {
        ServiceCategory serviceCategory = new ServiceCategory()
            .name(DEFAULT_NAME)
            .fixedAmount(DEFAULT_FIXED_AMOUNT)
            .hasFixedPrice(DEFAULT_HAS_FIXED_PRICE);
        return serviceCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceCategory createUpdatedEntity(EntityManager em) {
        ServiceCategory serviceCategory = new ServiceCategory()
            .name(UPDATED_NAME)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .hasFixedPrice(UPDATED_HAS_FIXED_PRICE);
        return serviceCategory;
    }

    @BeforeEach
    public void initTest() {
        serviceCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceCategory() throws Exception {
        int databaseSizeBeforeCreate = serviceCategoryRepository.findAll().size();
        // Create the ServiceCategory
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryMapper.toDto(serviceCategory);
        restServiceCategoryMockMvc.perform(post("/api/service-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        assertThat(serviceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceCategory testServiceCategory = serviceCategoryList.get(serviceCategoryList.size() - 1);
        assertThat(testServiceCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceCategory.getFixedAmount()).isEqualTo(DEFAULT_FIXED_AMOUNT);
        assertThat(testServiceCategory.isHasFixedPrice()).isEqualTo(DEFAULT_HAS_FIXED_PRICE);

        // Validate the ServiceCategory in Elasticsearch
        verify(mockServiceCategorySearchRepository, times(1)).save(testServiceCategory);
    }

    @Test
    @Transactional
    public void createServiceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceCategoryRepository.findAll().size();

        // Create the ServiceCategory with an existing ID
        serviceCategory.setId(1L);
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryMapper.toDto(serviceCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceCategoryMockMvc.perform(post("/api/service-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        assertThat(serviceCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceCategory in Elasticsearch
        verify(mockServiceCategorySearchRepository, times(0)).save(serviceCategory);
    }


    @Test
    @Transactional
    public void getAllServiceCategories() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        // Get all the serviceCategoryList
        restServiceCategoryMockMvc.perform(get("/api/service-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fixedAmount").value(hasItem(DEFAULT_FIXED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].hasFixedPrice").value(hasItem(DEFAULT_HAS_FIXED_PRICE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        // Get the serviceCategory
        restServiceCategoryMockMvc.perform(get("/api/service-categories/{id}", serviceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fixedAmount").value(DEFAULT_FIXED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.hasFixedPrice").value(DEFAULT_HAS_FIXED_PRICE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceCategory() throws Exception {
        // Get the serviceCategory
        restServiceCategoryMockMvc.perform(get("/api/service-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        int databaseSizeBeforeUpdate = serviceCategoryRepository.findAll().size();

        // Update the serviceCategory
        ServiceCategory updatedServiceCategory = serviceCategoryRepository.findById(serviceCategory.getId()).get();
        // Disconnect from session so that the updates on updatedServiceCategory are not directly saved in db
        em.detach(updatedServiceCategory);
        updatedServiceCategory
            .name(UPDATED_NAME)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .hasFixedPrice(UPDATED_HAS_FIXED_PRICE);
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryMapper.toDto(updatedServiceCategory);

        restServiceCategoryMockMvc.perform(put("/api/service-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        assertThat(serviceCategoryList).hasSize(databaseSizeBeforeUpdate);
        ServiceCategory testServiceCategory = serviceCategoryList.get(serviceCategoryList.size() - 1);
        assertThat(testServiceCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceCategory.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testServiceCategory.isHasFixedPrice()).isEqualTo(UPDATED_HAS_FIXED_PRICE);

        // Validate the ServiceCategory in Elasticsearch
        verify(mockServiceCategorySearchRepository, times(1)).save(testServiceCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceCategory() throws Exception {
        int databaseSizeBeforeUpdate = serviceCategoryRepository.findAll().size();

        // Create the ServiceCategory
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryMapper.toDto(serviceCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceCategoryMockMvc.perform(put("/api/service-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceCategory in the database
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        assertThat(serviceCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceCategory in Elasticsearch
        verify(mockServiceCategorySearchRepository, times(0)).save(serviceCategory);
    }

    @Test
    @Transactional
    public void deleteServiceCategory() throws Exception {
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);

        int databaseSizeBeforeDelete = serviceCategoryRepository.findAll().size();

        // Delete the serviceCategory
        restServiceCategoryMockMvc.perform(delete("/api/service-categories/{id}", serviceCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        assertThat(serviceCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceCategory in Elasticsearch
        verify(mockServiceCategorySearchRepository, times(1)).deleteById(serviceCategory.getId());
    }

    @Test
    @Transactional
    public void searchServiceCategory() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        serviceCategoryRepository.saveAndFlush(serviceCategory);
        when(mockServiceCategorySearchRepository.search(queryStringQuery("id:" + serviceCategory.getId())))
            .thenReturn(Collections.singletonList(serviceCategory));

        // Search the serviceCategory
        restServiceCategoryMockMvc.perform(get("/api/_search/service-categories?query=id:" + serviceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fixedAmount").value(hasItem(DEFAULT_FIXED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].hasFixedPrice").value(hasItem(DEFAULT_HAS_FIXED_PRICE.booleanValue())));
    }
}
