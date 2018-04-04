package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyapptestApp;

import com.mycompany.myapp.domain.Monstergroup;
import com.mycompany.myapp.repository.MonstergroupRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MonstergroupResource REST controller.
 *
 * @see MonstergroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapptestApp.class)
public class MonstergroupResourceIntTest {

    private static final String DEFAULT_MONSTERS = "AAAAAAAAAA";
    private static final String UPDATED_MONSTERS = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private MonstergroupRepository monstergroupRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMonstergroupMockMvc;

    private Monstergroup monstergroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonstergroupResource monstergroupResource = new MonstergroupResource(monstergroupRepository);
        this.restMonstergroupMockMvc = MockMvcBuilders.standaloneSetup(monstergroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monstergroup createEntity(EntityManager em) {
        Monstergroup monstergroup = new Monstergroup()
            .monsters(DEFAULT_MONSTERS)
            .user(DEFAULT_USER);
        return monstergroup;
    }

    @Before
    public void initTest() {
        monstergroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonstergroup() throws Exception {
        int databaseSizeBeforeCreate = monstergroupRepository.findAll().size();

        // Create the Monstergroup
        restMonstergroupMockMvc.perform(post("/api/monstergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monstergroup)))
            .andExpect(status().isCreated());

        // Validate the Monstergroup in the database
        List<Monstergroup> monstergroupList = monstergroupRepository.findAll();
        assertThat(monstergroupList).hasSize(databaseSizeBeforeCreate + 1);
        Monstergroup testMonstergroup = monstergroupList.get(monstergroupList.size() - 1);
        assertThat(testMonstergroup.getMonsters()).isEqualTo(DEFAULT_MONSTERS);
        assertThat(testMonstergroup.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    public void createMonstergroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monstergroupRepository.findAll().size();

        // Create the Monstergroup with an existing ID
        monstergroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonstergroupMockMvc.perform(post("/api/monstergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monstergroup)))
            .andExpect(status().isBadRequest());

        // Validate the Monstergroup in the database
        List<Monstergroup> monstergroupList = monstergroupRepository.findAll();
        assertThat(monstergroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonstergroups() throws Exception {
        // Initialize the database
        monstergroupRepository.saveAndFlush(monstergroup);

        // Get all the monstergroupList
        restMonstergroupMockMvc.perform(get("/api/monstergroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monstergroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].monsters").value(hasItem(DEFAULT_MONSTERS.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())));
    }

    @Test
    @Transactional
    public void getMonstergroup() throws Exception {
        // Initialize the database
        monstergroupRepository.saveAndFlush(monstergroup);

        // Get the monstergroup
        restMonstergroupMockMvc.perform(get("/api/monstergroups/{id}", monstergroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monstergroup.getId().intValue()))
            .andExpect(jsonPath("$.monsters").value(DEFAULT_MONSTERS.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMonstergroup() throws Exception {
        // Get the monstergroup
        restMonstergroupMockMvc.perform(get("/api/monstergroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonstergroup() throws Exception {
        // Initialize the database
        monstergroupRepository.saveAndFlush(monstergroup);
        int databaseSizeBeforeUpdate = monstergroupRepository.findAll().size();

        // Update the monstergroup
        Monstergroup updatedMonstergroup = monstergroupRepository.findOne(monstergroup.getId());
        // Disconnect from session so that the updates on updatedMonstergroup are not directly saved in db
        em.detach(updatedMonstergroup);
        updatedMonstergroup
            .monsters(UPDATED_MONSTERS)
            .user(UPDATED_USER);

        restMonstergroupMockMvc.perform(put("/api/monstergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonstergroup)))
            .andExpect(status().isOk());

        // Validate the Monstergroup in the database
        List<Monstergroup> monstergroupList = monstergroupRepository.findAll();
        assertThat(monstergroupList).hasSize(databaseSizeBeforeUpdate);
        Monstergroup testMonstergroup = monstergroupList.get(monstergroupList.size() - 1);
        assertThat(testMonstergroup.getMonsters()).isEqualTo(UPDATED_MONSTERS);
        assertThat(testMonstergroup.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingMonstergroup() throws Exception {
        int databaseSizeBeforeUpdate = monstergroupRepository.findAll().size();

        // Create the Monstergroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMonstergroupMockMvc.perform(put("/api/monstergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monstergroup)))
            .andExpect(status().isCreated());

        // Validate the Monstergroup in the database
        List<Monstergroup> monstergroupList = monstergroupRepository.findAll();
        assertThat(monstergroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMonstergroup() throws Exception {
        // Initialize the database
        monstergroupRepository.saveAndFlush(monstergroup);
        int databaseSizeBeforeDelete = monstergroupRepository.findAll().size();

        // Get the monstergroup
        restMonstergroupMockMvc.perform(delete("/api/monstergroups/{id}", monstergroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monstergroup> monstergroupList = monstergroupRepository.findAll();
        assertThat(monstergroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monstergroup.class);
        Monstergroup monstergroup1 = new Monstergroup();
        monstergroup1.setId(1L);
        Monstergroup monstergroup2 = new Monstergroup();
        monstergroup2.setId(monstergroup1.getId());
        assertThat(monstergroup1).isEqualTo(monstergroup2);
        monstergroup2.setId(2L);
        assertThat(monstergroup1).isNotEqualTo(monstergroup2);
        monstergroup1.setId(null);
        assertThat(monstergroup1).isNotEqualTo(monstergroup2);
    }
}
