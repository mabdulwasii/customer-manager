package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Hardware;
import ng.com.dpros.customermanager.domain.Services;
import ng.com.dpros.customermanager.domain.Review;
import ng.com.dpros.customermanager.domain.Payment;
import ng.com.dpros.customermanager.domain.ServiceCategory;
import ng.com.dpros.customermanager.domain.Profile;
import ng.com.dpros.customermanager.repository.HardwareRepository;
import ng.com.dpros.customermanager.repository.search.HardwareSearchRepository;
import ng.com.dpros.customermanager.service.HardwareService;
import ng.com.dpros.customermanager.service.dto.HardwareDTO;
import ng.com.dpros.customermanager.service.mapper.HardwareMapper;
import ng.com.dpros.customermanager.service.dto.HardwareCriteria;
import ng.com.dpros.customermanager.service.HardwareQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ng.com.dpros.customermanager.domain.enumeration.Gadget;
/**
 * Integration tests for the {@link HardwareResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HardwareResourceIT {

    private static final Gadget DEFAULT_GADGET = Gadget.PHONE;
    private static final Gadget UPDATED_GADGET = Gadget.TABLET;

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IMEI_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IMEI_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private HardwareRepository hardwareRepository;

    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private HardwareService hardwareService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.HardwareSearchRepositoryMockConfiguration
     */
    @Autowired
    private HardwareSearchRepository mockHardwareSearchRepository;

    @Autowired
    private HardwareQueryService hardwareQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHardwareMockMvc;

    private Hardware hardware;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hardware createEntity(EntityManager em) {
        Hardware hardware = new Hardware()
            .gadget(DEFAULT_GADGET)
            .model(DEFAULT_MODEL)
            .brandName(DEFAULT_BRAND_NAME)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .imeiNumber(DEFAULT_IMEI_NUMBER)
            .state(DEFAULT_STATE);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        hardware.setProfile(profile);
        return hardware;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hardware createUpdatedEntity(EntityManager em) {
        Hardware hardware = new Hardware()
            .gadget(UPDATED_GADGET)
            .model(UPDATED_MODEL)
            .brandName(UPDATED_BRAND_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .imeiNumber(UPDATED_IMEI_NUMBER)
            .state(UPDATED_STATE);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createUpdatedEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        hardware.setProfile(profile);
        return hardware;
    }

    @BeforeEach
    public void initTest() {
        hardware = createEntity(em);
    }

    @Test
    @Transactional
    public void createHardware() throws Exception {
        int databaseSizeBeforeCreate = hardwareRepository.findAll().size();
        // Create the Hardware
        HardwareDTO hardwareDTO = hardwareMapper.toDto(hardware);
        restHardwareMockMvc.perform(post("/api/hardware")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hardwareDTO)))
            .andExpect(status().isCreated());

        // Validate the Hardware in the database
        List<Hardware> hardwareList = hardwareRepository.findAll();
        assertThat(hardwareList).hasSize(databaseSizeBeforeCreate + 1);
        Hardware testHardware = hardwareList.get(hardwareList.size() - 1);
        assertThat(testHardware.getGadget()).isEqualTo(DEFAULT_GADGET);
        assertThat(testHardware.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testHardware.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testHardware.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testHardware.getImeiNumber()).isEqualTo(DEFAULT_IMEI_NUMBER);
        assertThat(testHardware.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the Hardware in Elasticsearch
        verify(mockHardwareSearchRepository, times(1)).save(testHardware);
    }

    @Test
    @Transactional
    public void createHardwareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hardwareRepository.findAll().size();

        // Create the Hardware with an existing ID
        hardware.setId(1L);
        HardwareDTO hardwareDTO = hardwareMapper.toDto(hardware);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHardwareMockMvc.perform(post("/api/hardware")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hardwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hardware in the database
        List<Hardware> hardwareList = hardwareRepository.findAll();
        assertThat(hardwareList).hasSize(databaseSizeBeforeCreate);

        // Validate the Hardware in Elasticsearch
        verify(mockHardwareSearchRepository, times(0)).save(hardware);
    }


    @Test
    @Transactional
    public void getAllHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList
        restHardwareMockMvc.perform(get("/api/hardware?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardware.getId().intValue())))
            .andExpect(jsonPath("$.[*].gadget").value(hasItem(DEFAULT_GADGET.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].imeiNumber").value(hasItem(DEFAULT_IMEI_NUMBER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get the hardware
        restHardwareMockMvc.perform(get("/api/hardware/{id}", hardware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hardware.getId().intValue()))
            .andExpect(jsonPath("$.gadget").value(DEFAULT_GADGET.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.imeiNumber").value(DEFAULT_IMEI_NUMBER))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }


    @Test
    @Transactional
    public void getHardwareByIdFiltering() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        Long id = hardware.getId();

        defaultHardwareShouldBeFound("id.equals=" + id);
        defaultHardwareShouldNotBeFound("id.notEquals=" + id);

        defaultHardwareShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHardwareShouldNotBeFound("id.greaterThan=" + id);

        defaultHardwareShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHardwareShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHardwareByGadgetIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where gadget equals to DEFAULT_GADGET
        defaultHardwareShouldBeFound("gadget.equals=" + DEFAULT_GADGET);

        // Get all the hardwareList where gadget equals to UPDATED_GADGET
        defaultHardwareShouldNotBeFound("gadget.equals=" + UPDATED_GADGET);
    }

    @Test
    @Transactional
    public void getAllHardwareByGadgetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where gadget not equals to DEFAULT_GADGET
        defaultHardwareShouldNotBeFound("gadget.notEquals=" + DEFAULT_GADGET);

        // Get all the hardwareList where gadget not equals to UPDATED_GADGET
        defaultHardwareShouldBeFound("gadget.notEquals=" + UPDATED_GADGET);
    }

    @Test
    @Transactional
    public void getAllHardwareByGadgetIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where gadget in DEFAULT_GADGET or UPDATED_GADGET
        defaultHardwareShouldBeFound("gadget.in=" + DEFAULT_GADGET + "," + UPDATED_GADGET);

        // Get all the hardwareList where gadget equals to UPDATED_GADGET
        defaultHardwareShouldNotBeFound("gadget.in=" + UPDATED_GADGET);
    }

    @Test
    @Transactional
    public void getAllHardwareByGadgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where gadget is not null
        defaultHardwareShouldBeFound("gadget.specified=true");

        // Get all the hardwareList where gadget is null
        defaultHardwareShouldNotBeFound("gadget.specified=false");
    }

    @Test
    @Transactional
    public void getAllHardwareByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model equals to DEFAULT_MODEL
        defaultHardwareShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the hardwareList where model equals to UPDATED_MODEL
        defaultHardwareShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllHardwareByModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model not equals to DEFAULT_MODEL
        defaultHardwareShouldNotBeFound("model.notEquals=" + DEFAULT_MODEL);

        // Get all the hardwareList where model not equals to UPDATED_MODEL
        defaultHardwareShouldBeFound("model.notEquals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllHardwareByModelIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultHardwareShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the hardwareList where model equals to UPDATED_MODEL
        defaultHardwareShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllHardwareByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model is not null
        defaultHardwareShouldBeFound("model.specified=true");

        // Get all the hardwareList where model is null
        defaultHardwareShouldNotBeFound("model.specified=false");
    }
                @Test
    @Transactional
    public void getAllHardwareByModelContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model contains DEFAULT_MODEL
        defaultHardwareShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the hardwareList where model contains UPDATED_MODEL
        defaultHardwareShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllHardwareByModelNotContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where model does not contain DEFAULT_MODEL
        defaultHardwareShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the hardwareList where model does not contain UPDATED_MODEL
        defaultHardwareShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }


    @Test
    @Transactional
    public void getAllHardwareByBrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName equals to DEFAULT_BRAND_NAME
        defaultHardwareShouldBeFound("brandName.equals=" + DEFAULT_BRAND_NAME);

        // Get all the hardwareList where brandName equals to UPDATED_BRAND_NAME
        defaultHardwareShouldNotBeFound("brandName.equals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllHardwareByBrandNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName not equals to DEFAULT_BRAND_NAME
        defaultHardwareShouldNotBeFound("brandName.notEquals=" + DEFAULT_BRAND_NAME);

        // Get all the hardwareList where brandName not equals to UPDATED_BRAND_NAME
        defaultHardwareShouldBeFound("brandName.notEquals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllHardwareByBrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName in DEFAULT_BRAND_NAME or UPDATED_BRAND_NAME
        defaultHardwareShouldBeFound("brandName.in=" + DEFAULT_BRAND_NAME + "," + UPDATED_BRAND_NAME);

        // Get all the hardwareList where brandName equals to UPDATED_BRAND_NAME
        defaultHardwareShouldNotBeFound("brandName.in=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllHardwareByBrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName is not null
        defaultHardwareShouldBeFound("brandName.specified=true");

        // Get all the hardwareList where brandName is null
        defaultHardwareShouldNotBeFound("brandName.specified=false");
    }
                @Test
    @Transactional
    public void getAllHardwareByBrandNameContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName contains DEFAULT_BRAND_NAME
        defaultHardwareShouldBeFound("brandName.contains=" + DEFAULT_BRAND_NAME);

        // Get all the hardwareList where brandName contains UPDATED_BRAND_NAME
        defaultHardwareShouldNotBeFound("brandName.contains=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllHardwareByBrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where brandName does not contain DEFAULT_BRAND_NAME
        defaultHardwareShouldNotBeFound("brandName.doesNotContain=" + DEFAULT_BRAND_NAME);

        // Get all the hardwareList where brandName does not contain UPDATED_BRAND_NAME
        defaultHardwareShouldBeFound("brandName.doesNotContain=" + UPDATED_BRAND_NAME);
    }


    @Test
    @Transactional
    public void getAllHardwareBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultHardwareShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the hardwareList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultHardwareShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareBySerialNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber not equals to DEFAULT_SERIAL_NUMBER
        defaultHardwareShouldNotBeFound("serialNumber.notEquals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the hardwareList where serialNumber not equals to UPDATED_SERIAL_NUMBER
        defaultHardwareShouldBeFound("serialNumber.notEquals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultHardwareShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the hardwareList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultHardwareShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber is not null
        defaultHardwareShouldBeFound("serialNumber.specified=true");

        // Get all the hardwareList where serialNumber is null
        defaultHardwareShouldNotBeFound("serialNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllHardwareBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber contains DEFAULT_SERIAL_NUMBER
        defaultHardwareShouldBeFound("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER);

        // Get all the hardwareList where serialNumber contains UPDATED_SERIAL_NUMBER
        defaultHardwareShouldNotBeFound("serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where serialNumber does not contain DEFAULT_SERIAL_NUMBER
        defaultHardwareShouldNotBeFound("serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER);

        // Get all the hardwareList where serialNumber does not contain UPDATED_SERIAL_NUMBER
        defaultHardwareShouldBeFound("serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllHardwareByImeiNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber equals to DEFAULT_IMEI_NUMBER
        defaultHardwareShouldBeFound("imeiNumber.equals=" + DEFAULT_IMEI_NUMBER);

        // Get all the hardwareList where imeiNumber equals to UPDATED_IMEI_NUMBER
        defaultHardwareShouldNotBeFound("imeiNumber.equals=" + UPDATED_IMEI_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareByImeiNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber not equals to DEFAULT_IMEI_NUMBER
        defaultHardwareShouldNotBeFound("imeiNumber.notEquals=" + DEFAULT_IMEI_NUMBER);

        // Get all the hardwareList where imeiNumber not equals to UPDATED_IMEI_NUMBER
        defaultHardwareShouldBeFound("imeiNumber.notEquals=" + UPDATED_IMEI_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareByImeiNumberIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber in DEFAULT_IMEI_NUMBER or UPDATED_IMEI_NUMBER
        defaultHardwareShouldBeFound("imeiNumber.in=" + DEFAULT_IMEI_NUMBER + "," + UPDATED_IMEI_NUMBER);

        // Get all the hardwareList where imeiNumber equals to UPDATED_IMEI_NUMBER
        defaultHardwareShouldNotBeFound("imeiNumber.in=" + UPDATED_IMEI_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareByImeiNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber is not null
        defaultHardwareShouldBeFound("imeiNumber.specified=true");

        // Get all the hardwareList where imeiNumber is null
        defaultHardwareShouldNotBeFound("imeiNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllHardwareByImeiNumberContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber contains DEFAULT_IMEI_NUMBER
        defaultHardwareShouldBeFound("imeiNumber.contains=" + DEFAULT_IMEI_NUMBER);

        // Get all the hardwareList where imeiNumber contains UPDATED_IMEI_NUMBER
        defaultHardwareShouldNotBeFound("imeiNumber.contains=" + UPDATED_IMEI_NUMBER);
    }

    @Test
    @Transactional
    public void getAllHardwareByImeiNumberNotContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where imeiNumber does not contain DEFAULT_IMEI_NUMBER
        defaultHardwareShouldNotBeFound("imeiNumber.doesNotContain=" + DEFAULT_IMEI_NUMBER);

        // Get all the hardwareList where imeiNumber does not contain UPDATED_IMEI_NUMBER
        defaultHardwareShouldBeFound("imeiNumber.doesNotContain=" + UPDATED_IMEI_NUMBER);
    }


    @Test
    @Transactional
    public void getAllHardwareByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state equals to DEFAULT_STATE
        defaultHardwareShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the hardwareList where state equals to UPDATED_STATE
        defaultHardwareShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllHardwareByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state not equals to DEFAULT_STATE
        defaultHardwareShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the hardwareList where state not equals to UPDATED_STATE
        defaultHardwareShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllHardwareByStateIsInShouldWork() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state in DEFAULT_STATE or UPDATED_STATE
        defaultHardwareShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the hardwareList where state equals to UPDATED_STATE
        defaultHardwareShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllHardwareByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state is not null
        defaultHardwareShouldBeFound("state.specified=true");

        // Get all the hardwareList where state is null
        defaultHardwareShouldNotBeFound("state.specified=false");
    }
                @Test
    @Transactional
    public void getAllHardwareByStateContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state contains DEFAULT_STATE
        defaultHardwareShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the hardwareList where state contains UPDATED_STATE
        defaultHardwareShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllHardwareByStateNotContainsSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        // Get all the hardwareList where state does not contain DEFAULT_STATE
        defaultHardwareShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the hardwareList where state does not contain UPDATED_STATE
        defaultHardwareShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }


    @Test
    @Transactional
    public void getAllHardwareByServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);
        Services services = ServicesResourceIT.createEntity(em);
        em.persist(services);
        em.flush();
        hardware.setServices(services);
        hardwareRepository.saveAndFlush(hardware);
        Long servicesId = services.getId();

        // Get all the hardwareList where services equals to servicesId
        defaultHardwareShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the hardwareList where services equals to servicesId + 1
        defaultHardwareShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }


    @Test
    @Transactional
    public void getAllHardwareByReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);
        Review review = ReviewResourceIT.createEntity(em);
        em.persist(review);
        em.flush();
        hardware.addReview(review);
        hardwareRepository.saveAndFlush(hardware);
        Long reviewId = review.getId();

        // Get all the hardwareList where review equals to reviewId
        defaultHardwareShouldBeFound("reviewId.equals=" + reviewId);

        // Get all the hardwareList where review equals to reviewId + 1
        defaultHardwareShouldNotBeFound("reviewId.equals=" + (reviewId + 1));
    }


    @Test
    @Transactional
    public void getAllHardwareByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        hardware.addPayment(payment);
        hardwareRepository.saveAndFlush(hardware);
        Long paymentId = payment.getId();

        // Get all the hardwareList where payment equals to paymentId
        defaultHardwareShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the hardwareList where payment equals to paymentId + 1
        defaultHardwareShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }


    @Test
    @Transactional
    public void getAllHardwareByServiceCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);
        ServiceCategory serviceCategory = ServiceCategoryResourceIT.createEntity(em);
        em.persist(serviceCategory);
        em.flush();
        hardware.setServiceCategory(serviceCategory);
        hardwareRepository.saveAndFlush(hardware);
        Long serviceCategoryId = serviceCategory.getId();

        // Get all the hardwareList where serviceCategory equals to serviceCategoryId
        defaultHardwareShouldBeFound("serviceCategoryId.equals=" + serviceCategoryId);

        // Get all the hardwareList where serviceCategory equals to serviceCategoryId + 1
        defaultHardwareShouldNotBeFound("serviceCategoryId.equals=" + (serviceCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllHardwareByProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        Profile profile = hardware.getProfile();
        hardwareRepository.saveAndFlush(hardware);
        Long profileId = profile.getId();

        // Get all the hardwareList where profile equals to profileId
        defaultHardwareShouldBeFound("profileId.equals=" + profileId);

        // Get all the hardwareList where profile equals to profileId + 1
        defaultHardwareShouldNotBeFound("profileId.equals=" + (profileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHardwareShouldBeFound(String filter) throws Exception {
        restHardwareMockMvc.perform(get("/api/hardware?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardware.getId().intValue())))
            .andExpect(jsonPath("$.[*].gadget").value(hasItem(DEFAULT_GADGET.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].imeiNumber").value(hasItem(DEFAULT_IMEI_NUMBER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));

        // Check, that the count call also returns 1
        restHardwareMockMvc.perform(get("/api/hardware/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHardwareShouldNotBeFound(String filter) throws Exception {
        restHardwareMockMvc.perform(get("/api/hardware?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHardwareMockMvc.perform(get("/api/hardware/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHardware() throws Exception {
        // Get the hardware
        restHardwareMockMvc.perform(get("/api/hardware/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        int databaseSizeBeforeUpdate = hardwareRepository.findAll().size();

        // Update the hardware
        Hardware updatedHardware = hardwareRepository.findById(hardware.getId()).get();
        // Disconnect from session so that the updates on updatedHardware are not directly saved in db
        em.detach(updatedHardware);
        updatedHardware
            .gadget(UPDATED_GADGET)
            .model(UPDATED_MODEL)
            .brandName(UPDATED_BRAND_NAME)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .imeiNumber(UPDATED_IMEI_NUMBER)
            .state(UPDATED_STATE);
        HardwareDTO hardwareDTO = hardwareMapper.toDto(updatedHardware);

        restHardwareMockMvc.perform(put("/api/hardware")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hardwareDTO)))
            .andExpect(status().isOk());

        // Validate the Hardware in the database
        List<Hardware> hardwareList = hardwareRepository.findAll();
        assertThat(hardwareList).hasSize(databaseSizeBeforeUpdate);
        Hardware testHardware = hardwareList.get(hardwareList.size() - 1);
        assertThat(testHardware.getGadget()).isEqualTo(UPDATED_GADGET);
        assertThat(testHardware.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testHardware.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testHardware.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testHardware.getImeiNumber()).isEqualTo(UPDATED_IMEI_NUMBER);
        assertThat(testHardware.getState()).isEqualTo(UPDATED_STATE);

        // Validate the Hardware in Elasticsearch
        verify(mockHardwareSearchRepository, times(1)).save(testHardware);
    }

    @Test
    @Transactional
    public void updateNonExistingHardware() throws Exception {
        int databaseSizeBeforeUpdate = hardwareRepository.findAll().size();

        // Create the Hardware
        HardwareDTO hardwareDTO = hardwareMapper.toDto(hardware);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHardwareMockMvc.perform(put("/api/hardware")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hardwareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hardware in the database
        List<Hardware> hardwareList = hardwareRepository.findAll();
        assertThat(hardwareList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Hardware in Elasticsearch
        verify(mockHardwareSearchRepository, times(0)).save(hardware);
    }

    @Test
    @Transactional
    public void deleteHardware() throws Exception {
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);

        int databaseSizeBeforeDelete = hardwareRepository.findAll().size();

        // Delete the hardware
        restHardwareMockMvc.perform(delete("/api/hardware/{id}", hardware.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hardware> hardwareList = hardwareRepository.findAll();
        assertThat(hardwareList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Hardware in Elasticsearch
        verify(mockHardwareSearchRepository, times(1)).deleteById(hardware.getId());
    }

    @Test
    @Transactional
    public void searchHardware() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        hardwareRepository.saveAndFlush(hardware);
        when(mockHardwareSearchRepository.search(queryStringQuery("id:" + hardware.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(hardware), PageRequest.of(0, 1), 1));

        // Search the hardware
        restHardwareMockMvc.perform(get("/api/_search/hardware?query=id:" + hardware.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardware.getId().intValue())))
            .andExpect(jsonPath("$.[*].gadget").value(hasItem(DEFAULT_GADGET.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].imeiNumber").value(hasItem(DEFAULT_IMEI_NUMBER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
}
