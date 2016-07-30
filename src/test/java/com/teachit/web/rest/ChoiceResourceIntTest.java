package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.Choice;
import com.teachit.repository.ChoiceRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ChoiceResource REST controller.
 *
 * @see ChoiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class ChoiceResourceIntTest {

    private static final String DEFAULT_RESPONSE = "AAAAA";
    private static final String UPDATED_RESPONSE = "BBBBB";

    private static final Boolean DEFAULT_CORRECT = false;
    private static final Boolean UPDATED_CORRECT = true;

    private static final Integer DEFAULT_ORDERING = 1;
    private static final Integer UPDATED_ORDERING = 2;

    @Inject
    private ChoiceRepository choiceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restChoiceMockMvc;

    private Choice choice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChoiceResource choiceResource = new ChoiceResource();
        ReflectionTestUtils.setField(choiceResource, "choiceRepository", choiceRepository);
        this.restChoiceMockMvc = MockMvcBuilders.standaloneSetup(choiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        choice = new Choice();
        choice.setResponse(DEFAULT_RESPONSE);
        choice.setCorrect(DEFAULT_CORRECT);
        choice.setOrdering(DEFAULT_ORDERING);
    }

    @Test
    @Transactional
    public void createChoice() throws Exception {
        int databaseSizeBeforeCreate = choiceRepository.findAll().size();

        // Create the Choice

        restChoiceMockMvc.perform(post("/api/choices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(choice)))
                .andExpect(status().isCreated());

        // Validate the Choice in the database
        List<Choice> choices = choiceRepository.findAll();
        assertThat(choices).hasSize(databaseSizeBeforeCreate + 1);
        Choice testChoice = choices.get(choices.size() - 1);
        assertThat(testChoice.getResponse()).isEqualTo(DEFAULT_RESPONSE);
        assertThat(testChoice.isCorrect()).isEqualTo(DEFAULT_CORRECT);
        assertThat(testChoice.getOrdering()).isEqualTo(DEFAULT_ORDERING);
    }

    @Test
    @Transactional
    public void getAllChoices() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choices
        restChoiceMockMvc.perform(get("/api/choices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
                .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE.toString())))
                .andExpect(jsonPath("$.[*].correct").value(hasItem(DEFAULT_CORRECT.booleanValue())))
                .andExpect(jsonPath("$.[*].ordering").value(hasItem(DEFAULT_ORDERING)));
    }

    @Test
    @Transactional
    public void getChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get the choice
        restChoiceMockMvc.perform(get("/api/choices/{id}", choice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(choice.getId().intValue()))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE.toString()))
            .andExpect(jsonPath("$.correct").value(DEFAULT_CORRECT.booleanValue()))
            .andExpect(jsonPath("$.ordering").value(DEFAULT_ORDERING));
    }

    @Test
    @Transactional
    public void getNonExistingChoice() throws Exception {
        // Get the choice
        restChoiceMockMvc.perform(get("/api/choices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice
        Choice updatedChoice = new Choice();
        updatedChoice.setId(choice.getId());
        updatedChoice.setResponse(UPDATED_RESPONSE);
        updatedChoice.setCorrect(UPDATED_CORRECT);
        updatedChoice.setOrdering(UPDATED_ORDERING);

        restChoiceMockMvc.perform(put("/api/choices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedChoice)))
                .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choices = choiceRepository.findAll();
        assertThat(choices).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choices.get(choices.size() - 1);
        assertThat(testChoice.getResponse()).isEqualTo(UPDATED_RESPONSE);
        assertThat(testChoice.isCorrect()).isEqualTo(UPDATED_CORRECT);
        assertThat(testChoice.getOrdering()).isEqualTo(UPDATED_ORDERING);
    }

    @Test
    @Transactional
    public void deleteChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);
        int databaseSizeBeforeDelete = choiceRepository.findAll().size();

        // Get the choice
        restChoiceMockMvc.perform(delete("/api/choices/{id}", choice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Choice> choices = choiceRepository.findAll();
        assertThat(choices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
