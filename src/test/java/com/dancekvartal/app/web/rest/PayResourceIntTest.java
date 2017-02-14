package com.dancekvartal.app.web.rest;

import com.dancekvartal.app.DancekvartalApp;

import com.dancekvartal.app.domain.Pay;
import com.dancekvartal.app.repository.PayRepository;
import com.dancekvartal.app.service.PayService;
import com.dancekvartal.app.repository.search.PaySearchRepository;
import com.dancekvartal.app.service.dto.PayDTO;
import com.dancekvartal.app.service.mapper.PayMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PayResource REST controller.
 *
 * @see PayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DancekvartalApp.class)
public class PayResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUM = new BigDecimal(2);

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private PayService payService;

    @Autowired
    private PaySearchRepository paySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPayMockMvc;

    private Pay pay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayResource payResource = new PayResource(payService);
        this.restPayMockMvc = MockMvcBuilders.standaloneSetup(payResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pay createEntity(EntityManager em) {
        Pay pay = new Pay()
                .date(DEFAULT_DATE)
                .sum(DEFAULT_SUM);
        return pay;
    }

    @Before
    public void initTest() {
        paySearchRepository.deleteAll();
        pay = createEntity(em);
    }

    @Test
    @Transactional
    public void createPay() throws Exception {
        int databaseSizeBeforeCreate = payRepository.findAll().size();

        // Create the Pay
        PayDTO payDTO = payMapper.payToPayDTO(pay);

        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payDTO)))
            .andExpect(status().isCreated());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeCreate + 1);
        Pay testPay = payList.get(payList.size() - 1);
        assertThat(testPay.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPay.getSum()).isEqualTo(DEFAULT_SUM);

        // Validate the Pay in Elasticsearch
        Pay payEs = paySearchRepository.findOne(testPay.getId());
        assertThat(payEs).isEqualToComparingFieldByField(testPay);
    }

    @Test
    @Transactional
    public void createPayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payRepository.findAll().size();

        // Create the Pay with an existing ID
        Pay existingPay = new Pay();
        existingPay.setId(1L);
        PayDTO existingPayDTO = payMapper.payToPayDTO(existingPay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPays() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);

        // Get all the payList
        restPayMockMvc.perform(get("/api/pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pay.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM.intValue())));
    }

    @Test
    @Transactional
    public void getPay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);

        // Get the pay
        restPayMockMvc.perform(get("/api/pays/{id}", pay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pay.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPay() throws Exception {
        // Get the pay
        restPayMockMvc.perform(get("/api/pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);
        paySearchRepository.save(pay);
        int databaseSizeBeforeUpdate = payRepository.findAll().size();

        // Update the pay
        Pay updatedPay = payRepository.findOne(pay.getId());
        updatedPay
                .date(UPDATED_DATE)
                .sum(UPDATED_SUM);
        PayDTO payDTO = payMapper.payToPayDTO(updatedPay);

        restPayMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payDTO)))
            .andExpect(status().isOk());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeUpdate);
        Pay testPay = payList.get(payList.size() - 1);
        assertThat(testPay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPay.getSum()).isEqualTo(UPDATED_SUM);

        // Validate the Pay in Elasticsearch
        Pay payEs = paySearchRepository.findOne(testPay.getId());
        assertThat(payEs).isEqualToComparingFieldByField(testPay);
    }

    @Test
    @Transactional
    public void updateNonExistingPay() throws Exception {
        int databaseSizeBeforeUpdate = payRepository.findAll().size();

        // Create the Pay
        PayDTO payDTO = payMapper.payToPayDTO(pay);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payDTO)))
            .andExpect(status().isCreated());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);
        paySearchRepository.save(pay);
        int databaseSizeBeforeDelete = payRepository.findAll().size();

        // Get the pay
        restPayMockMvc.perform(delete("/api/pays/{id}", pay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean payExistsInEs = paySearchRepository.exists(pay.getId());
        assertThat(payExistsInEs).isFalse();

        // Validate the database is empty
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);
        paySearchRepository.save(pay);

        // Search the pay
        restPayMockMvc.perform(get("/api/_search/pays?query=id:" + pay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pay.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM.intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pay.class);
    }
}
