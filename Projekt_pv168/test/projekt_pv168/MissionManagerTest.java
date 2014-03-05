/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Collection;
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
    public void testUpdateMission() {
        System.out.println("updateMission");
        Mission mission = null;
        MissionManager instance = new MissionManagerImpl();
        instance.updateMission(mission);
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

    @Test
    public void testGetMission() {
        System.out.println("getMission");
        long id = 0L;
        MissionManager instance = new MissionManagerImpl();
        Mission expResult = null;
        Mission result = instance.getMission(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindAllMissions() {
        System.out.println("findAllMissions");
        MissionManager instance = new MissionManagerImpl();
        Collection expResult = null;
        Collection result = instance.findAllMissions();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class MissionManagerImpl implements MissionManager {

        public void createMission(Mission mission) {
        }

        public void updateMission(Mission mission) {
        }

        public void removeMission(Mission mission) {
        }

        public Mission getMission(long id) {
            return null;
        }

        public Collection<Mission> findAllMissions() {
            return null;
        }
    }
}