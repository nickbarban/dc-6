package com.dancekvartal.app.web.rest;

import com.dancekvartal.app.DancekvartalApp;

import com.dancekvartal.app.domain.Parent;
import com.dancekvartal.app.repository.ParentRepository;
import com.dancekvartal.app.service.ParentService;
import com.dancekvartal.app.repository.search.ParentSearchRepository;
import com.dancekvartal.app.service.dto.ParentDTO;
import com.dancekvartal.app.service.mapper.ParentMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParentResource REST controller.
 *
 * @see ParentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DancekvartalApp.class)
public class ParentResourceIntTest {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentSearchRepository parentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParentMockMvc;

    private Parent parent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParentResource parentResource = new ParentResource(parentService);
        this.restParentMockMvc = MockMvcBuilders.standaloneSetup(parentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parent createEntity(EntityManager em) {
        Parent parent = new Parent()
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .birthday(DEFAULT_BIRTHDAY)
                .address(DEFAULT_ADDRESS)
                .phone1(DEFAULT_PHONE_1)
                .phone2(DEFAULT_PHONE_2)
                .email(DEFAULT_EMAIL)
                .active(DEFAULT_ACTIVE)
                .photoUrl(DEFAULT_PHOTO_URL);
        return parent;
    }

    @Before
    public void initTest() {
        parentSearchRepository.deleteAll();
        parent = createEntity(em);
    }

    @Test
    @Transactional
    public void createParent() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate + 1);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testParent.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testParent.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testParent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testParent.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testParent.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testParent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParent.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testParent.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);

        // Validate the Parent in Elasticsearch
        Parent parentEs = parentSearchRepository.findOne(testParent.getId());
        assertThat(parentEs).isEqualToComparingFieldByField(testParent);
    }

    @Test
    @Transactional
    public void createParentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent with an existing ID
        Parent existingParent = new Parent();
        existingParent.setId(1L);
        ParentDTO existingParentDTO = parentMapper.parentToParentDTO(existingParent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setFirstname(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setLastname(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setBirthday(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setAddress(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhone1IsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setPhone1(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhone2IsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setPhone2(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setActive(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParents() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get all the parentList
        restParentMockMvc.perform(get("/api/parents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL.toString())));
    }

    @Test
    @Transactional
    public void getParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parent.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParent() throws Exception {
        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);
        parentSearchRepository.save(parent);
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent
        Parent updatedParent = parentRepository.findOne(parent.getId());
        updatedParent
                .firstname(UPDATED_FIRSTNAME)
                .lastname(UPDATED_LASTNAME)
                .birthday(UPDATED_BIRTHDAY)
                .address(UPDATED_ADDRESS)
                .phone1(UPDATED_PHONE_1)
                .phone2(UPDATED_PHONE_2)
                .email(UPDATED_EMAIL)
                .active(UPDATED_ACTIVE)
                .photoUrl(UPDATED_PHOTO_URL);
        ParentDTO parentDTO = parentMapper.parentToParentDTO(updatedParent);

        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testParent.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testParent.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testParent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testParent.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testParent.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testParent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParent.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testParent.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);

        // Validate the Parent in Elasticsearch
        Parent parentEs = parentSearchRepository.findOne(testParent.getId());
        assertThat(parentEs).isEqualToComparingFieldByField(testParent);
    }

    @Test
    @Transactional
    public void updateNonExistingParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Create the Parent
        ParentDTO parentDTO = parentMapper.parentToParentDTO(parent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);
        parentSearchRepository.save(parent);
        int databaseSizeBeforeDelete = parentRepository.findAll().size();

        // Get the parent
        restParentMockMvc.perform(delete("/api/parents/{id}", parent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean parentExistsInEs = parentSearchRepository.exists(parent.getId());
        assertThat(parentExistsInEs).isFalse();

        // Validate the database is empty
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);
        parentSearchRepository.save(parent);

        // Search the parent
        restParentMockMvc.perform(get("/api/_search/parents?query=id:" + parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parent.class);
    }
}
