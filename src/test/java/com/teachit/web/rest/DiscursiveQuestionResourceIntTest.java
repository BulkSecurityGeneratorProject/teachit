package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.DiscursiveQuestion;
import com.teachit.repository.DiscursiveQuestionRepository;
import com.teachit.service.DiscursiveQuestionService;
import com.teachit.web.rest.dto.DiscursiveQuestionDTO;
import com.teachit.web.rest.mapper.DiscursiveQuestionMapper;

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
 * Test class for the DiscursiveQuestionResource REST controller.
 *
 * @see DiscursiveQuestionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class DiscursiveQuestionResourceIntTest {


    private static final Integer DEFAULT_ORDERING = 1;
    private static final Integer UPDATED_ORDERING = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_QUESTION = "AAAAA";
    private static final String UPDATED_QUESTION = "BBBBB";

    @Inject
    private DiscursiveQuestionRepository discursiveQuestionRepository;

    @Inject
    private DiscursiveQuestionMapper discursiveQuestionMapper;

    @Inject
    private DiscursiveQuestionService discursiveQuestionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDiscursiveQuestionMockMvc;

    private DiscursiveQuestion discursiveQuestion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiscursiveQuestionResource discursiveQuestionResource = new DiscursiveQuestionResource();
        ReflectionTestUtils.setField(discursiveQuestionResource, "discursiveQuestionService", discursiveQuestionService);
        ReflectionTestUtils.setField(discursiveQuestionResource, "discursiveQuestionMapper", discursiveQuestionMapper);
        this.restDiscursiveQuestionMockMvc = MockMvcBuilders.standaloneSetup(discursiveQuestionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        discursiveQuestion = new DiscursiveQuestion();
        discursiveQuestion.setOrdering(DEFAULT_ORDERING);
        discursiveQuestion.setStartDate(DEFAULT_START_DATE);
        discursiveQuestion.setQuestion(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    public void createDiscursiveQuestion() throws Exception {
        int databaseSizeBeforeCreate = discursiveQuestionRepository.findAll().size();

        // Create the DiscursiveQuestion
        DiscursiveQuestionDTO discursiveQuestionDTO = discursiveQuestionMapper.discursiveQuestionToDiscursiveQuestionDTO(discursiveQuestion);

        restDiscursiveQuestionMockMvc.perform(post("/api/discursive-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discursiveQuestionDTO)))
                .andExpect(status().isCreated());

        // Validate the DiscursiveQuestion in the database
        List<DiscursiveQuestion> discursiveQuestions = discursiveQuestionRepository.findAll();
        assertThat(discursiveQuestions).hasSize(databaseSizeBeforeCreate + 1);
        DiscursiveQuestion testDiscursiveQuestion = discursiveQuestions.get(discursiveQuestions.size() - 1);
        assertThat(testDiscursiveQuestion.getOrdering()).isEqualTo(DEFAULT_ORDERING);
        assertThat(testDiscursiveQuestion.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDiscursiveQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
    }

    @Test
    @Transactional
    public void getAllDiscursiveQuestions() throws Exception {
        // Initialize the database
        discursiveQuestionRepository.saveAndFlush(discursiveQuestion);

        // Get all the discursiveQuestions
        restDiscursiveQuestionMockMvc.perform(get("/api/discursive-questions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(discursiveQuestion.getId().intValue())))
                .andExpect(jsonPath("$.[*].ordering").value(hasItem(DEFAULT_ORDERING)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())));
    }

    @Test
    @Transactional
    public void getDiscursiveQuestion() throws Exception {
        // Initialize the database
        discursiveQuestionRepository.saveAndFlush(discursiveQuestion);

        // Get the discursiveQuestion
        restDiscursiveQuestionMockMvc.perform(get("/api/discursive-questions/{id}", discursiveQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(discursiveQuestion.getId().intValue()))
            .andExpect(jsonPath("$.ordering").value(DEFAULT_ORDERING))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscursiveQuestion() throws Exception {
        // Get the discursiveQuestion
        restDiscursiveQuestionMockMvc.perform(get("/api/discursive-questions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscursiveQuestion() throws Exception {
        // Initialize the database
        discursiveQuestionRepository.saveAndFlush(discursiveQuestion);
        int databaseSizeBeforeUpdate = discursiveQuestionRepository.findAll().size();

        // Update the discursiveQuestion
        DiscursiveQuestion updatedDiscursiveQuestion = new DiscursiveQuestion();
        updatedDiscursiveQuestion.setId(discursiveQuestion.getId());
        updatedDiscursiveQuestion.setOrdering(UPDATED_ORDERING);
        updatedDiscursiveQuestion.setStartDate(UPDATED_START_DATE);
        updatedDiscursiveQuestion.setQuestion(UPDATED_QUESTION);
        DiscursiveQuestionDTO discursiveQuestionDTO = discursiveQuestionMapper.discursiveQuestionToDiscursiveQuestionDTO(updatedDiscursiveQuestion);

        restDiscursiveQuestionMockMvc.perform(put("/api/discursive-questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discursiveQuestionDTO)))
                .andExpect(status().isOk());

        // Validate the DiscursiveQuestion in the database
        List<DiscursiveQuestion> discursiveQuestions = discursiveQuestionRepository.findAll();
        assertThat(discursiveQuestions).hasSize(databaseSizeBeforeUpdate);
        DiscursiveQuestion testDiscursiveQuestion = discursiveQuestions.get(discursiveQuestions.size() - 1);
        assertThat(testDiscursiveQuestion.getOrdering()).isEqualTo(UPDATED_ORDERING);
        assertThat(testDiscursiveQuestion.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDiscursiveQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
    }

    @Test
    @Transactional
    public void deleteDiscursiveQuestion() throws Exception {
        // Initialize the database
        discursiveQuestionRepository.saveAndFlush(discursiveQuestion);
        int databaseSizeBeforeDelete = discursiveQuestionRepository.findAll().size();

        // Get the discursiveQuestion
        restDiscursiveQuestionMockMvc.perform(delete("/api/discursive-questions/{id}", discursiveQuestion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DiscursiveQuestion> discursiveQuestions = discursiveQuestionRepository.findAll();
        assertThat(discursiveQuestions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
