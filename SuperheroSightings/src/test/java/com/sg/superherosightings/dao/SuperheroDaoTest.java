/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.SetupSuperherosUnitTests;
import com.sg.superherosightings.model.Superhero;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jswan
 */
public class SuperheroDaoTest {

    SuperheroDao superDao;

    public SuperheroDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        SetupSuperherosUnitTests superSetup = new SetupSuperherosUnitTests();
        superDao = ctx.getBean("superheroDao", SuperheroDao.class);


    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperhero method, of class SuperheroDao.
     */
    @Test
    public void testAddSuperhero() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("Chief Wiggum");
        super1.setSuperheroDescription("GoodGuy");
        super1.setSuperpower("People protector power");

        superDao.addSuperhero(super1);

        Superhero fromDao = superDao.getSuperheroById(super1.getSuperheroId());

        assertEquals(fromDao, super1);

    }

    /**
     * Test of updateSuperhero method, of class SuperheroDao.
     */
    @Test
    public void testUpdateSuperhero() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("Chief Wiggum");
        super1.setSuperheroDescription("GoodGuy");
        super1.setSuperpower("People protector power");

        superDao.addSuperhero(super1);
        Superhero fromDb = superDao.getSuperheroById(super1.getSuperheroId());
        fromDb.setSuperpower("A SUPER POWER");

        superDao.updateSuperhero(fromDb);
        assertEquals(fromDb.getSuperpower(), "A SUPER POWER");


    }

    /**
     * Test of getSuperheroById method, of class SuperheroDao.
     */
    @Test
    public void testGetSuperheroById() {

        Superhero super1 = new Superhero();
        super1.setSuperheroName("Chief Wiggum");
        super1.setSuperheroDescription("GoodGuy");
        super1.setSuperpower("People protector power");

        superDao.addSuperhero(super1);

        Superhero super2 = new Superhero();
        super2.setSuperheroName("Hacker");
        super2.setSuperheroDescription("Bad Guy");
        super2.setSuperpower("SuperEvil");

        superDao.addSuperhero(super2);

        List<Superhero> heroList = superDao.getAllSuperheros();
        Superhero ourSuperhero = heroList.get(0);

        Superhero fromDb = superDao.getSuperheroById(ourSuperhero.getSuperheroId());
        assertEquals(fromDb.getSuperpower(), "People protector power");

    }

    /**
     * Test of getAllSuperheros method, of class SuperheroDao.
     */
    @Test
    public void testGetAllSuperheros() {
        Superhero super1 = new Superhero();
        super1.setSuperheroName("Chief Wiggum");
        super1.setSuperheroDescription("GoodGuy");
        super1.setSuperpower("People protector power");

        superDao.addSuperhero(super1);

        Superhero super2 = new Superhero();
        super2.setSuperheroName("Hacker");
        super2.setSuperheroDescription("Bad Guy");
        super2.setSuperpower("SuperEvil");

        superDao.addSuperhero(super2);

        List<Superhero> heroList = superDao.getAllSuperheros();

        assertTrue(heroList.size() == 2);

    }

}
