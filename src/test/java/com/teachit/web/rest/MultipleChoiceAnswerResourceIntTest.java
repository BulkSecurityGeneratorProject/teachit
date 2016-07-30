package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.MultipleChoiceAnswer;
import com.teachit.repository.MultipleChoiceAnswerRepository;

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
 * Test class for the MultipleChoiceAnswerResource REST controller.
 *
 * @see MultipleChoiceAnswerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class MultipleChoiceAnswerResourceIntTest {


    @Inject
    private MultipleChoiceAnswerRepository multipleChoiceAnswerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMultipleChoiceAnswerMockMvc;

    private MultipleChoiceAnswer multipleChoiceAnswer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MultipleChoiceAnswerResource multipleChoiceAnswerResource = new MultipleChoiceAnswerResource();
        ReflectionTestUtils.setField(multipleChoiceAnswerResource, "multipleChoiceAnswerRepository", multipleChoiceAnswerRepository);
        this.restMultipleChoiceAnswerMockMvc = MockMvcBuilders.standaloneSetup(multipleChoiceAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        multipleChoiceAnswer = new MultipleChoiceAnswer();
    }

    @Test
    @Transactional
    public void createMultipleChoiceAnswer() throws Exception {
        int databaseSizeBeforeCreate = multipleChoiceAnswerRepository.findAll().size();

        // Create the MultipleChoiceAnswer

        restMultipleChoiceAnswerMockMvc.perform(post("/api/multiple-choice-answers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(multipleChoiceAnswer)))
                .andExpect(status().isCreated());

        // Validate the MultipleChoiceAnswer in the database
        List<MultipleChoiceAnswer> multipleChoiceAnswers = multipleChoiceAnswerRepository.findAll();
        assertThat(multipleChoiceAnswers).hasSize(databaseSizeBeforeCreate + 1);
        MultipleChoiceAnswer testMultipleChoiceAnswer = multipleChoiceAnswers.get(multipleChoiceAnswers.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMultipleChoiceAnswers() throws Exception {
        // Initialize the database
        multipleChoiceAnswerRepository.saveAndFlush(multipleChoiceAnswer);

        // Get all the multipleChoiceAnswers
        restMultipleChoiceAnswerMockMvc.perform(get("/api/multiple-choice-answers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(multipleChoiceAnswer.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        multipleChoiceAnswerRepository.saveAndFlush(multipleChoiceAnswer);

        // Get the multipleChoiceAnswer
        restMultipleChoiceAnswerMockMvc.perform(get("/api/multiple-choice-answers/{id}", multipleChoiceAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(multipleChoiceAnswer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMultipleChoiceAnswer() throws Exception {
        // Get the multipleChoiceAnswer
        restMultipleChoiceAnswerMockMvc.perform(get("/api/multiple-choice-answers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        multipleChoiceAnswerRepository.saveAndFlush(multipleChoiceAnswer);
        int databaseSizeBeforeUpdate = multipleChoiceAnswerRepository.findAll().size();

        // Update the multipleChoiceAnswer
        MultipleChoiceAnswer updatedMultipleChoiceAnswer = new MultipleChoiceAnswer();
        updatedMultipleChoiceAnswer.setId(multipleChoiceAnswer.getId());

        restMultipleChoiceAnswerMockMvc.perform(put("/api/multiple-choice-answers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMultipleChoiceAnswer)))
                .andExpect(status().isOk());

        // Validate the MultipleChoiceAnswer in the database
        List<MultipleChoiceAnswer> multipleChoiceAnswers = multipleChoiceAnswerRepository.findAll();
        assertThat(multipleChoiceAnswers).hasSize(databaseSizeBeforeUpdate);
        MultipleChoiceAnswer testMultipleChoiceAnswer = multipleChoiceAnswers.get(multipleChoiceAnswers.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMultipleChoiceAnswer() throws Exception {
        // Initialize the database
        multipleChoiceAnswerRepository.saveAndFlush(multipleChoiceAnswer);
        int databaseSizeBeforeDelete = multipleChoiceAnswerRepository.findAll().size();

        // Get the multipleChoiceAnswer
        restMultipleChoiceAnswerMockMvc.perform(delete("/api/multiple-choice-answers/{id}", multipleChoiceAnswer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MultipleChoiceAnswer> multipleChoiceAnswers = multipleChoiceAnswerRepository.findAll();
        assertThat(multipleChoiceAnswers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
