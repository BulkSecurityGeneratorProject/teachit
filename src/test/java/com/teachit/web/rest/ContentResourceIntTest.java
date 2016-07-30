package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.Content;
import com.teachit.repository.ContentRepository;

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
 * Test class for the ContentResource REST controller.
 *
 * @see ContentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class ContentResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";

    @Inject
    private ContentRepository contentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContentMockMvc;

    private Content content;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentResource contentResource = new ContentResource();
        ReflectionTestUtils.setField(contentResource, "contentRepository", contentRepository);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        content = new Content();
        content.setFileName(DEFAULT_FILE_NAME);
        content.setStartDate(DEFAULT_START_DATE);
        content.setDescription(DEFAULT_DESCRIPTION);
        content.setText(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content

        restContentMockMvc.perform(post("/api/contents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(content)))
                .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contents.get(contents.size() - 1);
        assertThat(testContent.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testContent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testContent.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contents
        restContentMockMvc.perform(get("/api/contents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
                .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = new Content();
        updatedContent.setId(content.getId());
        updatedContent.setFileName(UPDATED_FILE_NAME);
        updatedContent.setStartDate(UPDATED_START_DATE);
        updatedContent.setDescription(UPDATED_DESCRIPTION);
        updatedContent.setText(UPDATED_TEXT);

        restContentMockMvc.perform(put("/api/contents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedContent)))
                .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contents.get(contents.size() - 1);
        assertThat(testContent.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testContent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testContent.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);
        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Get the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Content> contents = contentRepository.findAll();
        assertThat(contents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
