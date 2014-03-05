/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lenovo
 */
public class MissionManagerTest {
    
    public MissionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateMission() {
        System.out.println("createMission");
        Mission mission = null;
        MissionManager instance = new MissionManagerImpl();
        instance.createMission(mission);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveMission() {
        System.out.println("removeMission");
        Mission mission = null;
        MissionManager instance = new MissionManagerImpl();
        instance.removeMission(mission);
        fail("The test case is a prototype.");
    }

    public class MissionManagerImpl implements MissionManager {

        public void createMission(Mission mission) {
        }

        public void removeMission(Mission mission) {
        }
    }
}