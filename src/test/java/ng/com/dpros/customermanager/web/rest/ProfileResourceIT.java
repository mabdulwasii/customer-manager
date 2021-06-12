package ng.com.dpros.customermanager.web.rest;

import ng.com.dpros.customermanager.CustomerManagerApp;
import ng.com.dpros.customermanager.domain.Profile;
import ng.com.dpros.customermanager.domain.User;
import ng.com.dpros.customermanager.domain.Address;
import ng.com.dpros.customermanager.domain.Hardware;
import ng.com.dpros.customermanager.domain.Software;
import ng.com.dpros.customermanager.domain.Training;
import ng.com.dpros.customermanager.repository.ProfileRepository;
import ng.com.dpros.customermanager.repository.search.ProfileSearchRepository;
import ng.com.dpros.customermanager.service.ProfileService;
import ng.com.dpros.customermanager.service.dto.ProfileDTO;
import ng.com.dpros.customermanager.service.mapper.ProfileMapper;
import ng.com.dpros.customermanager.service.dto.ProfileCriteria;
import ng.com.dpros.customermanager.service.ProfileQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ng.com.dpros.customermanager.domain.enumeration.Gender;
/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = CustomerManagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfileResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PROFILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_ID = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_VALID_ID = "AAAAAAAAAA";
    private static final String UPDATED_VALID_ID = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    /**
     * This repository is mocked in the ng.com.dpros.customermanager.repository.search test package.
     *
     * @see ng.com.dpros.customermanager.repository.search.ProfileSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProfileSearchRepository mockProfileSearchRepository;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .profileId(DEFAULT_PROFILE_ID)
            .gender(DEFAULT_GENDER)
            .validId(DEFAULT_VALID_ID);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .profileId(UPDATED_PROFILE_ID)
            .gender(UPDATED_GENDER)
            .validId(UPDATED_VALID_ID);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.getProfileId()).isEqualTo(DEFAULT_PROFILE_ID);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getValidId()).isEqualTo(DEFAULT_VALID_ID);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).save(testProfile);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(0)).save(profile);
    }


    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].profileId").value(hasItem(DEFAULT_PROFILE_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].validId").value(hasItem(DEFAULT_VALID_ID)));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.profileId").value(DEFAULT_PROFILE_ID))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.validId").value(DEFAULT_VALID_ID));
    }


    @Test
    @Transactional
    public void getProfilesByIdFiltering() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        Long id = profile.getId();

        defaultProfileShouldBeFound("id.equals=" + id);
        defaultProfileShouldNotBeFound("id.notEquals=" + id);

        defaultProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the profileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the profileList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the profileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber is not null
        defaultProfileShouldBeFound("phoneNumber.specified=true");

        // Get all the profileList where phoneNumber is null
        defaultProfileShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProfilesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the profileList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultProfileShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the profileList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultProfileShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth is not null
        defaultProfileShouldBeFound("dateOfBirth.specified=true");

        // Get all the profileList where dateOfBirth is null
        defaultProfileShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllProfilesByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultProfileShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the profileList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultProfileShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllProfilesByProfileIdIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId equals to DEFAULT_PROFILE_ID
        defaultProfileShouldBeFound("profileId.equals=" + DEFAULT_PROFILE_ID);

        // Get all the profileList where profileId equals to UPDATED_PROFILE_ID
        defaultProfileShouldNotBeFound("profileId.equals=" + UPDATED_PROFILE_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByProfileIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId not equals to DEFAULT_PROFILE_ID
        defaultProfileShouldNotBeFound("profileId.notEquals=" + DEFAULT_PROFILE_ID);

        // Get all the profileList where profileId not equals to UPDATED_PROFILE_ID
        defaultProfileShouldBeFound("profileId.notEquals=" + UPDATED_PROFILE_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByProfileIdIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId in DEFAULT_PROFILE_ID or UPDATED_PROFILE_ID
        defaultProfileShouldBeFound("profileId.in=" + DEFAULT_PROFILE_ID + "," + UPDATED_PROFILE_ID);

        // Get all the profileList where profileId equals to UPDATED_PROFILE_ID
        defaultProfileShouldNotBeFound("profileId.in=" + UPDATED_PROFILE_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByProfileIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId is not null
        defaultProfileShouldBeFound("profileId.specified=true");

        // Get all the profileList where profileId is null
        defaultProfileShouldNotBeFound("profileId.specified=false");
    }
                @Test
    @Transactional
    public void getAllProfilesByProfileIdContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId contains DEFAULT_PROFILE_ID
        defaultProfileShouldBeFound("profileId.contains=" + DEFAULT_PROFILE_ID);

        // Get all the profileList where profileId contains UPDATED_PROFILE_ID
        defaultProfileShouldNotBeFound("profileId.contains=" + UPDATED_PROFILE_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByProfileIdNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where profileId does not contain DEFAULT_PROFILE_ID
        defaultProfileShouldNotBeFound("profileId.doesNotContain=" + DEFAULT_PROFILE_ID);

        // Get all the profileList where profileId does not contain UPDATED_PROFILE_ID
        defaultProfileShouldBeFound("profileId.doesNotContain=" + UPDATED_PROFILE_ID);
    }


    @Test
    @Transactional
    public void getAllProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender equals to DEFAULT_GENDER
        defaultProfileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender not equals to DEFAULT_GENDER
        defaultProfileShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the profileList where gender not equals to UPDATED_GENDER
        defaultProfileShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultProfileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the profileList where gender equals to UPDATED_GENDER
        defaultProfileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where gender is not null
        defaultProfileShouldBeFound("gender.specified=true");

        // Get all the profileList where gender is null
        defaultProfileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByValidIdIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId equals to DEFAULT_VALID_ID
        defaultProfileShouldBeFound("validId.equals=" + DEFAULT_VALID_ID);

        // Get all the profileList where validId equals to UPDATED_VALID_ID
        defaultProfileShouldNotBeFound("validId.equals=" + UPDATED_VALID_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByValidIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId not equals to DEFAULT_VALID_ID
        defaultProfileShouldNotBeFound("validId.notEquals=" + DEFAULT_VALID_ID);

        // Get all the profileList where validId not equals to UPDATED_VALID_ID
        defaultProfileShouldBeFound("validId.notEquals=" + UPDATED_VALID_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByValidIdIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId in DEFAULT_VALID_ID or UPDATED_VALID_ID
        defaultProfileShouldBeFound("validId.in=" + DEFAULT_VALID_ID + "," + UPDATED_VALID_ID);

        // Get all the profileList where validId equals to UPDATED_VALID_ID
        defaultProfileShouldNotBeFound("validId.in=" + UPDATED_VALID_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByValidIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId is not null
        defaultProfileShouldBeFound("validId.specified=true");

        // Get all the profileList where validId is null
        defaultProfileShouldNotBeFound("validId.specified=false");
    }
                @Test
    @Transactional
    public void getAllProfilesByValidIdContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId contains DEFAULT_VALID_ID
        defaultProfileShouldBeFound("validId.contains=" + DEFAULT_VALID_ID);

        // Get all the profileList where validId contains UPDATED_VALID_ID
        defaultProfileShouldNotBeFound("validId.contains=" + UPDATED_VALID_ID);
    }

    @Test
    @Transactional
    public void getAllProfilesByValidIdNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where validId does not contain DEFAULT_VALID_ID
        defaultProfileShouldNotBeFound("validId.doesNotContain=" + DEFAULT_VALID_ID);

        // Get all the profileList where validId does not contain UPDATED_VALID_ID
        defaultProfileShouldBeFound("validId.doesNotContain=" + UPDATED_VALID_ID);
    }


    @Test
    @Transactional
    public void getAllProfilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        profileRepository.saveAndFlush(profile);
        Long userId = user.getId();

        // Get all the profileList where user equals to userId
        defaultProfileShouldBeFound("userId.equals=" + userId);

        // Get all the profileList where user equals to userId + 1
        defaultProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        Address address = AddressResourceIT.createEntity(em);
        em.persist(address);
        em.flush();
        profile.setAddress(address);
        profileRepository.saveAndFlush(profile);
        Long addressId = address.getId();

        // Get all the profileList where address equals to addressId
        defaultProfileShouldBeFound("addressId.equals=" + addressId);

        // Get all the profileList where address equals to addressId + 1
        defaultProfileShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByHardwareIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        Hardware hardware = HardwareResourceIT.createEntity(em);
        em.persist(hardware);
        em.flush();
        profile.addHardware(hardware);
        profileRepository.saveAndFlush(profile);
        Long hardwareId = hardware.getId();

        // Get all the profileList where hardware equals to hardwareId
        defaultProfileShouldBeFound("hardwareId.equals=" + hardwareId);

        // Get all the profileList where hardware equals to hardwareId + 1
        defaultProfileShouldNotBeFound("hardwareId.equals=" + (hardwareId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesBySoftwareIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        Software software = SoftwareResourceIT.createEntity(em);
        em.persist(software);
        em.flush();
        profile.addSoftware(software);
        profileRepository.saveAndFlush(profile);
        Long softwareId = software.getId();

        // Get all the profileList where software equals to softwareId
        defaultProfileShouldBeFound("softwareId.equals=" + softwareId);

        // Get all the profileList where software equals to softwareId + 1
        defaultProfileShouldNotBeFound("softwareId.equals=" + (softwareId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilesByTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        Training training = TrainingResourceIT.createEntity(em);
        em.persist(training);
        em.flush();
        profile.addTraining(training);
        profileRepository.saveAndFlush(profile);
        Long trainingId = training.getId();

        // Get all the profileList where training equals to trainingId
        defaultProfileShouldBeFound("trainingId.equals=" + trainingId);

        // Get all the profileList where training equals to trainingId + 1
        defaultProfileShouldNotBeFound("trainingId.equals=" + (trainingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].profileId").value(hasItem(DEFAULT_PROFILE_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].validId").value(hasItem(DEFAULT_VALID_ID)));

        // Check, that the count call also returns 1
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .profileId(UPDATED_PROFILE_ID)
            .gender(UPDATED_GENDER)
            .validId(UPDATED_VALID_ID);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getProfileId()).isEqualTo(UPDATED_PROFILE_ID);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getValidId()).isEqualTo(UPDATED_VALID_ID);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).save(testProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(0)).save(profile);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).deleteById(profile.getId());
    }

    @Test
    @Transactional
    public void searchProfile() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        when(mockProfileSearchRepository.search(queryStringQuery("id:" + profile.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(profile), PageRequest.of(0, 1), 1));

        // Search the profile
        restProfileMockMvc.perform(get("/api/_search/profiles?query=id:" + profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].profileId").value(hasItem(DEFAULT_PROFILE_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].validId").value(hasItem(DEFAULT_VALID_ID)));
    }
}
