package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.MultipleChoiceQuestion;
import com.teachit.repository.MultipleChoiceQuestionRepository;

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
 * Test class for the MultipleChoiceQuestionResource REST controller.
 *
 * @see MultipleChoiceQuestionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class MultipleChoiceQuestionResourceIntTest {


    private static final Integer DEFAULT_ORDERING = 1;
    private static final Integer UPDATED_ORDERING = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_QUESTION = "AAAAA";
    private static final String UPDATED_QUESTION = "BBBBB";

    @Inject
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMultipleChoiceQuestionMockMvc;

    private MultipleChoiceQuestion multipleChoiceQuestion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MultipleChoiceQuestionResource multipleChoiceQuestionResource = new MultipleChoiceQuestionResource();
        ReflectionTestUtils.setField(multipleChoiceQuestionResource, "multipleChoiceQuestionRepository", multipleChoiceQuestionRepository);
        this.restMultipleChoiceQuestionMockMvc = MockMvcBuilders.standaloneSetup(multipleChoiceQuestionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setOrdering(DEFAULT_ORDERING);
        multipleChoiceQuestion.setStartDate(DEFAULT_START_DATE);
        multipleChoiceQuestion.setQuestion(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    public void createMultipleChoiceQuestion() throws Exception {
        int databaseSizeBeforeCreate = multipleChoiceQuestionRepository.findAll().size();

        // Create the MultipleChoiceQuestion

        restMultipleChoiceQuestionMockMvc.perform(post("/api/multiple-choice-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multipleChoiceQuestion)))
                .andExpect(status().isCreated());

        // Validate the MultipleChoiceQuestion in the database
        List<MultipleChoiceQuestion> multipleChoiceQuestions = multipleChoiceQuestionRepository.findAll();
        assertThat(multipleChoiceQuestions).hasSize(databaseSizeBeforeCreate + 1);
        MultipleChoiceQuestion testMultipleChoiceQuestion = multipleChoiceQuestions.get(multipleChoiceQuestions.size() - 1);
        assertThat(testMultipleChoiceQuestion.getOrdering()).isEqualTo(DEFAULT_ORDERING);
        assertThat(testMultipleChoiceQuestion.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMultipleChoiceQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    public void getAllMultipleChoiceQuestions() throws Exception {
        // Initialize the database
        multipleChoiceQuestionRepository.saveAndFlush(multipleChoiceQuestion);

        // Get all the multipleChoiceQuestions
        restMultipleChoiceQuestionMockMvc.perform(get("/api/multiple-choice-questions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(multipleChoiceQuestion.getId().intValue())))
                .andExpect(jsonPath("$.[*].ordering").value(hasItem(DEFAULT_ORDERING)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())));
    }

    @Test
    @Transactional
    public void getMultipleChoiceQuestion() throws Exception {
        // Initialize the database
        multipleChoiceQuestionRepository.saveAndFlush(multipleChoiceQuestion);

        // Get the multipleChoiceQuestion
        restMultipleChoiceQuestionMockMvc.perform(get("/api/multiple-choice-questions/{id}", multipleChoiceQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(multipleChoiceQuestion.getId().intValue()))
            .andExpect(jsonPath("$.ordering").value(DEFAULT_ORDERING))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMultipleChoiceQuestion() throws Exception {
        // Get the multipleChoiceQuestion
        restMultipleChoiceQuestionMockMvc.perform(get("/api/multiple-choice-questions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultipleChoiceQuestion() throws Exception {
        // Initialize the database
        multipleChoiceQuestionRepository.saveAndFlush(multipleChoiceQuestion);
        int databaseSizeBeforeUpdate = multipleChoiceQuestionRepository.findAll().size();

        // Update the multipleChoiceQuestion
        MultipleChoiceQuestion updatedMultipleChoiceQuestion = new MultipleChoiceQuestion();
        updatedMultipleChoiceQuestion.setId(multipleChoiceQuestion.getId());
        updatedMultipleChoiceQuestion.setOrdering(UPDATED_ORDERING);
        updatedMultipleChoiceQuestion.setStartDate(UPDATED_START_DATE);
        updatedMultipleChoiceQuestion.setQuestion(UPDATED_QUESTION);

        restMultipleChoiceQuestionMockMvc.perform(put("/api/multiple-choice-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMultipleChoiceQuestion)))
                .andExpect(status().isOk());

        // Validate the MultipleChoiceQuestion in the database
        List<MultipleChoiceQuestion> multipleChoiceQuestions = multipleChoiceQuestionRepository.findAll();
        assertThat(multipleChoiceQuestions).hasSize(databaseSizeBeforeUpdate);
        MultipleChoiceQuestion testMultipleChoiceQuestion = multipleChoiceQuestions.get(multipleChoiceQuestions.size() - 1);
        assertThat(testMultipleChoiceQuestion.getOrdering()).isEqualTo(UPDATED_ORDERING);
        assertThat(testMultipleChoiceQuestion.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMultipleChoiceQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void deleteMultipleChoiceQuestion() throws Exception {
        // Initialize the database
        multipleChoiceQuestionRepository.saveAndFlush(multipleChoiceQuestion);
        int databaseSizeBeforeDelete = multipleChoiceQuestionRepository.findAll().size();

        // Get the multipleChoiceQuestion
        restMultipleChoiceQuestionMockMvc.perform(delete("/api/multiple-choice-questions/{id}", multipleChoiceQuestion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MultipleChoiceQuestion> multipleChoiceQuestions = multipleChoiceQuestionRepository.findAll();
        assertThat(multipleChoiceQuestions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
