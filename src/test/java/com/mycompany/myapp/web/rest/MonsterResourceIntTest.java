package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyapptestApp;

import com.mycompany.myapp.domain.Monster;
import com.mycompany.myapp.repository.MonsterRepository;
import com.mycompany.myapp.service.MonsterService;
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
 * Test class for the MonsterResource REST controller.
 *
 * @see MonsterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapptestApp.class)
public class MonsterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_NO = 1;
    private static final Integer UPDATED_PAGE_NO = 2;

    private static final Integer DEFAULT_MONSTER_ID = 1;
    private static final Integer UPDATED_MONSTER_ID = 2;

    private static final Float DEFAULT_CHALLENGE = 1F;
    private static final Float UPDATED_CHALLENGE = 2F;

    private static final Integer DEFAULT_ENVIRONMENT = 0;
    private static final Integer UPDATED_ENVIRONMENT = 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    @Autowired
    private MonsterRepository monsterRepository;

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMonsterMockMvc;

    private Monster monster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonsterResource monsterResource = new MonsterResource(monsterService);
        this.restMonsterMockMvc = MockMvcBuilders.standaloneSetup(monsterResource)
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
    public static Monster createEntity(EntityManager em) {
        Monster monster = new Monster()
            .name(DEFAULT_NAME)
            .pageNo(DEFAULT_PAGE_NO)
            .monsterID(DEFAULT_MONSTER_ID)
            .challenge(DEFAULT_CHALLENGE)
            .environment(DEFAULT_ENVIRONMENT)
            .description(DEFAULT_DESCRIPTION)
            .size(DEFAULT_SIZE);
        return monster;
    }

    @Before
    public void initTest() {
        monster = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonster() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isCreated());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate + 1);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMonster.getPageNo()).isEqualTo(DEFAULT_PAGE_NO);
        assertThat(testMonster.getMonsterID()).isEqualTo(DEFAULT_MONSTER_ID);
        assertThat(testMonster.getChallenge()).isEqualTo(DEFAULT_CHALLENGE);
        assertThat(testMonster.getEnvironment()).isEqualTo(DEFAULT_ENVIRONMENT);
        assertThat(testMonster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMonster.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createMonsterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster with an existing ID
        monster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isBadRequest());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMonsterIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = monsterRepository.findAll().size();
        // set the field null
        monster.setMonsterID(null);

        // Create the Monster, which fails.

        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isBadRequest());

        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonsters() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get all the monsterList
        restMonsterMockMvc.perform(get("/api/monsters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pageNo").value(hasItem(DEFAULT_PAGE_NO)))
            .andExpect(jsonPath("$.[*].monsterID").value(hasItem(DEFAULT_MONSTER_ID)))
            .andExpect(jsonPath("$.[*].challenge").value(hasItem(DEFAULT_CHALLENGE.doubleValue())))
            .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", monster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pageNo").value(DEFAULT_PAGE_NO))
            .andExpect(jsonPath("$.monsterID").value(DEFAULT_MONSTER_ID))
            .andExpect(jsonPath("$.challenge").value(DEFAULT_CHALLENGE.doubleValue()))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMonster() throws Exception {
        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonster() throws Exception {
        // Initialize the database
        monsterService.save(monster);

        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Update the monster
        Monster updatedMonster = monsterRepository.findOne(monster.getId());
        // Disconnect from session so that the updates on updatedMonster are not directly saved in db
        em.detach(updatedMonster);
        updatedMonster
            .name(UPDATED_NAME)
            .pageNo(UPDATED_PAGE_NO)
            .monsterID(UPDATED_MONSTER_ID)
            .challenge(UPDATED_CHALLENGE)
            .environment(UPDATED_ENVIRONMENT)
            .description(UPDATED_DESCRIPTION)
            .size(UPDATED_SIZE);

        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonster)))
            .andExpect(status().isOk());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMonster.getPageNo()).isEqualTo(UPDATED_PAGE_NO);
        assertThat(testMonster.getMonsterID()).isEqualTo(UPDATED_MONSTER_ID);
        assertThat(testMonster.getChallenge()).isEqualTo(UPDATED_CHALLENGE);
        assertThat(testMonster.getEnvironment()).isEqualTo(UPDATED_ENVIRONMENT);
        assertThat(testMonster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMonster.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingMonster() throws Exception {
        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Create the Monster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isCreated());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMonster() throws Exception {
        // Initialize the database
        monsterService.save(monster);

        int databaseSizeBeforeDelete = monsterRepository.findAll().size();

        // Get the monster
        restMonsterMockMvc.perform(delete("/api/monsters/{id}", monster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monster.class);
        Monster monster1 = new Monster();
        monster1.setId(1L);
        Monster monster2 = new Monster();
        monster2.setId(monster1.getId());
        assertThat(monster1).isEqualTo(monster2);
        monster2.setId(2L);
        assertThat(monster1).isNotEqualTo(monster2);
        monster1.setId(null);
        assertThat(monster1).isNotEqualTo(monster2);
    }
}
