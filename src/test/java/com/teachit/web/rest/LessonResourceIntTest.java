package com.teachit.web.rest;

import com.teachit.TeachitApp;
import com.teachit.domain.Lesson;
import com.teachit.repository.LessonRepository;
import com.teachit.service.LessonService;
import com.teachit.web.rest.dto.LessonDTO;
import com.teachit.web.rest.mapper.LessonMapper;

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
 * Test class for the LessonResource REST controller.
 *
 * @see LessonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachitApp.class)
@WebAppConfiguration
@IntegrationTest
public class LessonResourceIntTest {


    private static final Integer DEFAULT_ORDERING = 1;
    private static final Integer UPDATED_ORDERING = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private LessonRepository lessonRepository;

    @Inject
    private LessonMapper lessonMapper;

    @Inject
    private LessonService lessonService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLessonMockMvc;

    private Lesson lesson;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LessonResource lessonResource = new LessonResource();
        ReflectionTestUtils.setField(lessonResource, "lessonService", lessonService);
        ReflectionTestUtils.setField(lessonResource, "lessonMapper", lessonMapper);
        this.restLessonMockMvc = MockMvcBuilders.standaloneSetup(lessonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lesson = new Lesson();
        lesson.setOrdering(DEFAULT_ORDERING);
        lesson.setStartDate(DEFAULT_START_DATE);
        lesson.setName(DEFAULT_NAME);
        lesson.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLesson() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        // Create the Lesson
        LessonDTO lessonDTO = lessonMapper.lessonToLessonDTO(lesson);

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
                .andExpect(status().isCreated());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeCreate + 1);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getOrdering()).isEqualTo(DEFAULT_ORDERING);
        assertThat(testLesson.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLesson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLesson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLessons() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get all the lessons
        restLessonMockMvc.perform(get("/api/lessons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
                .andExpect(jsonPath("$.[*].ordering").value(hasItem(DEFAULT_ORDERING)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
            .andExpect(jsonPath("$.ordering").value(DEFAULT_ORDERING))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLesson() throws Exception {
        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);
        int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        // Update the lesson
        Lesson updatedLesson = new Lesson();
        updatedLesson.setId(lesson.getId());
        updatedLesson.setOrdering(UPDATED_ORDERING);
        updatedLesson.setStartDate(UPDATED_START_DATE);
        updatedLesson.setName(UPDATED_NAME);
        updatedLesson.setDescription(UPDATED_DESCRIPTION);
        LessonDTO lessonDTO = lessonMapper.lessonToLessonDTO(updatedLesson);

        restLessonMockMvc.perform(put("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
                .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeUpdate);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getOrdering()).isEqualTo(UPDATED_ORDERING);
        assertThat(testLesson.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLesson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLesson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);
        int databaseSizeBeforeDelete = lessonRepository.findAll().size();

        // Get the lesson
        restLessonMockMvc.perform(delete("/api/lessons/{id}", lesson.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
