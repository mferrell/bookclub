package com.mferrell.bookclub.web.rest;

import com.mferrell.bookclub.BookclubApp;
import com.mferrell.bookclub.domain.Chapter;
import com.mferrell.bookclub.repository.ChapterRepository;
import com.mferrell.bookclub.service.ChapterService;
import com.mferrell.bookclub.service.dto.ChapterDTO;
import com.mferrell.bookclub.service.mapper.ChapterMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChapterResource} REST controller.
 */
@SpringBootTest(classes = BookclubApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChapterResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChapterMockMvc;

    private Chapter chapter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chapter createEntity(EntityManager em) {
        Chapter chapter = new Chapter()
            .title(DEFAULT_TITLE);
        return chapter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chapter createUpdatedEntity(EntityManager em) {
        Chapter chapter = new Chapter()
            .title(UPDATED_TITLE);
        return chapter;
    }

    @BeforeEach
    public void initTest() {
        chapter = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapter() throws Exception {
        int databaseSizeBeforeCreate = chapterRepository.findAll().size();
        // Create the Chapter
        ChapterDTO chapterDTO = chapterMapper.toDto(chapter);
        restChapterMockMvc.perform(post("/api/chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapterDTO)))
            .andExpect(status().isCreated());

        // Validate the Chapter in the database
        List<Chapter> chapterList = chapterRepository.findAll();
        assertThat(chapterList).hasSize(databaseSizeBeforeCreate + 1);
        Chapter testChapter = chapterList.get(chapterList.size() - 1);
        assertThat(testChapter.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createChapterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chapterRepository.findAll().size();

        // Create the Chapter with an existing ID
        chapter.setId(1L);
        ChapterDTO chapterDTO = chapterMapper.toDto(chapter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChapterMockMvc.perform(post("/api/chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapter in the database
        List<Chapter> chapterList = chapterRepository.findAll();
        assertThat(chapterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChapters() throws Exception {
        // Initialize the database
        chapterRepository.saveAndFlush(chapter);

        // Get all the chapterList
        restChapterMockMvc.perform(get("/api/chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapter.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getChapter() throws Exception {
        // Initialize the database
        chapterRepository.saveAndFlush(chapter);

        // Get the chapter
        restChapterMockMvc.perform(get("/api/chapters/{id}", chapter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chapter.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingChapter() throws Exception {
        // Get the chapter
        restChapterMockMvc.perform(get("/api/chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapter() throws Exception {
        // Initialize the database
        chapterRepository.saveAndFlush(chapter);

        int databaseSizeBeforeUpdate = chapterRepository.findAll().size();

        // Update the chapter
        Chapter updatedChapter = chapterRepository.findById(chapter.getId()).get();
        // Disconnect from session so that the updates on updatedChapter are not directly saved in db
        em.detach(updatedChapter);
        updatedChapter
            .title(UPDATED_TITLE);
        ChapterDTO chapterDTO = chapterMapper.toDto(updatedChapter);

        restChapterMockMvc.perform(put("/api/chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapterDTO)))
            .andExpect(status().isOk());

        // Validate the Chapter in the database
        List<Chapter> chapterList = chapterRepository.findAll();
        assertThat(chapterList).hasSize(databaseSizeBeforeUpdate);
        Chapter testChapter = chapterList.get(chapterList.size() - 1);
        assertThat(testChapter.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingChapter() throws Exception {
        int databaseSizeBeforeUpdate = chapterRepository.findAll().size();

        // Create the Chapter
        ChapterDTO chapterDTO = chapterMapper.toDto(chapter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChapterMockMvc.perform(put("/api/chapters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapter in the database
        List<Chapter> chapterList = chapterRepository.findAll();
        assertThat(chapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChapter() throws Exception {
        // Initialize the database
        chapterRepository.saveAndFlush(chapter);

        int databaseSizeBeforeDelete = chapterRepository.findAll().size();

        // Delete the chapter
        restChapterMockMvc.perform(delete("/api/chapters/{id}", chapter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chapter> chapterList = chapterRepository.findAll();
        assertThat(chapterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
