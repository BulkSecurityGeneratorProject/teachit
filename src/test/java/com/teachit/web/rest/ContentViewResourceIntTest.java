package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.ContentView;
import com.teachit.repository.ContentViewRepository;
import com.teachit.service.ContentViewService;
import com.teachit.web.rest.dto.ContentViewDTO;
import com.teachit.web.rest.mapper.ContentViewMapper;

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
 * Test class for the ContentViewResource REST controller.
 *
 * @see ContentViewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class ContentViewResourceIntTest {


    private static final Boolean DEFAULT_VIEW = false;
    private static final Boolean UPDATED_VIEW = true;

    @Inject
    private ContentViewRepository contentViewRepository;

    @Inject
    private ContentViewMapper contentViewMapper;

    @Inject
    private ContentViewService contentViewService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContentViewMockMvc;

    private ContentView contentView;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentViewResource contentViewResource = new ContentViewResource();
        ReflectionTestUtils.setField(contentViewResource, "contentViewService", contentViewService);
        ReflectionTestUtils.setField(contentViewResource, "contentViewMapper", contentViewMapper);
        this.restContentViewMockMvc = MockMvcBuilders.standaloneSetup(contentViewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contentView = new ContentView();
        contentView.setView(DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void createContentView() throws Exception {
        int databaseSizeBeforeCreate = contentViewRepository.findAll().size();

        // Create the ContentView
        ContentViewDTO contentViewDTO = contentViewMapper.contentViewToContentViewDTO(contentView);

        restContentViewMockMvc.perform(post("/api/content-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contentViewDTO)))
                .andExpect(status().isCreated());

        // Validate the ContentView in the database
        List<ContentView> contentViews = contentViewRepository.findAll();
        assertThat(contentViews).hasSize(databaseSizeBeforeCreate + 1);
        ContentView testContentView = contentViews.get(contentViews.size() - 1);
        assertThat(testContentView.isView()).isEqualTo(DEFAULT_VIEW);
    }

    @Test
    @Transactional
    public void getAllContentViews() throws Exception {
        // Initialize the database
        contentViewRepository.saveAndFlush(contentView);

        // Get all the contentViews
        restContentViewMockMvc.perform(get("/api/content-views?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contentView.getId().intValue())))
                .andExpect(jsonPath("$.[*].view").value(hasItem(DEFAULT_VIEW.booleanValue())));
    }

    @Test
    @Transactional
    public void getContentView() throws Exception {
        // Initialize the database
        contentViewRepository.saveAndFlush(contentView);

        // Get the contentView
        restContentViewMockMvc.perform(get("/api/content-views/{id}", contentView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contentView.getId().intValue()))
            .andExpect(jsonPath("$.view").value(DEFAULT_VIEW.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContentView() throws Exception {
        // Get the contentView
        restContentViewMockMvc.perform(get("/api/content-views/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentView() throws Exception {
        // Initialize the database
        contentViewRepository.saveAndFlush(contentView);
        int databaseSizeBeforeUpdate = contentViewRepository.findAll().size();

        // Update the contentView
        ContentView updatedContentView = new ContentView();
        updatedContentView.setId(contentView.getId());
        updatedContentView.setView(UPDATED_VIEW);
        ContentViewDTO contentViewDTO = contentViewMapper.contentViewToContentViewDTO(updatedContentView);

        restContentViewMockMvc.perform(put("/api/content-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contentViewDTO)))
                .andExpect(status().isOk());

        // Validate the ContentView in the database
        List<ContentView> contentViews = contentViewRepository.findAll();
        assertThat(contentViews).hasSize(databaseSizeBeforeUpdate);
        ContentView testContentView = contentViews.get(contentViews.size() - 1);
        assertThat(testContentView.isView()).isEqualTo(UPDATED_VIEW);
    }

    @Test
    @Transactional
    public void deleteContentView() throws Exception {
        // Initialize the database
        contentViewRepository.saveAndFlush(contentView);
        int databaseSizeBeforeDelete = contentViewRepository.findAll().size();

        // Get the contentView
        restContentViewMockMvc.perform(delete("/api/content-views/{id}", contentView.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentView> contentViews = contentViewRepository.findAll();
        assertThat(contentViews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
