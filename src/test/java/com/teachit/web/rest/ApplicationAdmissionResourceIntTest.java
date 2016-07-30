package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.ApplicationAdmission;
import com.teachit.repository.ApplicationAdmissionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ApplicationAdmissionResource REST controller.
 *
 * @see ApplicationAdmissionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class ApplicationAdmissionResourceIntTest {


    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACCEPTED = false;
    private static final Boolean UPDATED_ACCEPTED = true;

    @Inject
    private ApplicationAdmissionRepository applicationAdmissionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApplicationAdmissionMockMvc;

    private ApplicationAdmission applicationAdmission;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicationAdmissionResource applicationAdmissionResource = new ApplicationAdmissionResource();
        ReflectionTestUtils.setField(applicationAdmissionResource, "applicationAdmissionRepository", applicationAdmissionRepository);
        this.restApplicationAdmissionMockMvc = MockMvcBuilders.standaloneSetup(applicationAdmissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        applicationAdmission = new ApplicationAdmission();
        applicationAdmission.setRequestDate(DEFAULT_REQUEST_DATE);
        applicationAdmission.setAccepted(DEFAULT_ACCEPTED);
    }

    @Test
    @Transactional
    public void createApplicationAdmission() throws Exception {
        int databaseSizeBeforeCreate = applicationAdmissionRepository.findAll().size();

        // Create the ApplicationAdmission

        restApplicationAdmissionMockMvc.perform(post("/api/application-admissions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationAdmission)))
                .andExpect(status().isCreated());

        // Validate the ApplicationAdmission in the database
        List<ApplicationAdmission> applicationAdmissions = applicationAdmissionRepository.findAll();
        assertThat(applicationAdmissions).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationAdmission testApplicationAdmission = applicationAdmissions.get(applicationAdmissions.size() - 1);
        assertThat(testApplicationAdmission.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testApplicationAdmission.isAccepted()).isEqualTo(DEFAULT_ACCEPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationAdmissions() throws Exception {
        // Initialize the database
        applicationAdmissionRepository.saveAndFlush(applicationAdmission);

        // Get all the applicationAdmissions
        restApplicationAdmissionMockMvc.perform(get("/api/application-admissions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(applicationAdmission.getId().intValue())))
                .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
                .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getApplicationAdmission() throws Exception {
        // Initialize the database
        applicationAdmissionRepository.saveAndFlush(applicationAdmission);

        // Get the applicationAdmission
        restApplicationAdmissionMockMvc.perform(get("/api/application-admissions/{id}", applicationAdmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(applicationAdmission.getId().intValue()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.accepted").value(DEFAULT_ACCEPTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationAdmission() throws Exception {
        // Get the applicationAdmission
        restApplicationAdmissionMockMvc.perform(get("/api/application-admissions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationAdmission() throws Exception {
        // Initialize the database
        applicationAdmissionRepository.saveAndFlush(applicationAdmission);
        int databaseSizeBeforeUpdate = applicationAdmissionRepository.findAll().size();

        // Update the applicationAdmission
        ApplicationAdmission updatedApplicationAdmission = new ApplicationAdmission();
        updatedApplicationAdmission.setId(applicationAdmission.getId());
        updatedApplicationAdmission.setRequestDate(UPDATED_REQUEST_DATE);
        updatedApplicationAdmission.setAccepted(UPDATED_ACCEPTED);

        restApplicationAdmissionMockMvc.perform(put("/api/application-admissions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedApplicationAdmission)))
                .andExpect(status().isOk());

        // Validate the ApplicationAdmission in the database
        List<ApplicationAdmission> applicationAdmissions = applicationAdmissionRepository.findAll();
        assertThat(applicationAdmissions).hasSize(databaseSizeBeforeUpdate);
        ApplicationAdmission testApplicationAdmission = applicationAdmissions.get(applicationAdmissions.size() - 1);
        assertThat(testApplicationAdmission.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testApplicationAdmission.isAccepted()).isEqualTo(UPDATED_ACCEPTED);
    }

    @Test
    @Transactional
    public void deleteApplicationAdmission() throws Exception {
        // Initialize the database
        applicationAdmissionRepository.saveAndFlush(applicationAdmission);
        int databaseSizeBeforeDelete = applicationAdmissionRepository.findAll().size();

        // Get the applicationAdmission
        restApplicationAdmissionMockMvc.perform(delete("/api/application-admissions/{id}", applicationAdmission.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationAdmission> applicationAdmissions = applicationAdmissionRepository.findAll();
        assertThat(applicationAdmissions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
