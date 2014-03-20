/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lenovo
 */
public class MissionManagerTest {

    private MissionManagerImpl manager;

    @Before
    public void setUp() {
        manager = new MissionManagerImpl();
    }

    @Test
    public void testCreateMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");

        manager.createMission(mission);

        assertNotNull(mission.getId());
        assertEquals(mission, manager.getMission(mission.getId()));
        assertNotSame(mission, manager.getMission(mission.getId()));
        assertDeepEquals(mission, manager.getMission(mission.getId()));
    }

    @Test
    public void testCreateWrongMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");

        //cant set id
        mission.setId(1l);
        tryCreateMission(mission);
        mission.setId(null);

        //null name
        buildMission(mission, null, 1, "Trenčín", "Just save him.");
        tryCreateMission(mission);

        //no name
        buildMission(mission, "", 1, "Trenčín", "Just save him.");
        tryCreateMission(mission);

        //negativ difficulty
        buildMission(mission, "Save general", -1, "Trenčín", "Just save him.");
        tryCreateMission(mission);

        //no location
        buildMission(mission, "Save general", 1, "", "Just save him.");
        tryCreateMission(mission);

        //null location
        buildMission(mission, "Save general", 1, null, "Just save him.");
        tryCreateMission(mission);

        //can be
        buildMission(mission, "Save general", 11, "Trenčín", null);
        tryCreateMission(mission);
    }

    @Test
    public void testUpdateMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        try{
            manager.updateMission(mission);
            fail();
        }catch(Exception ex){
        }
        assertNull(manager.getMission(mission.getId()));
        
        manager.createMission(mission);
        manager.createMission(mission2);
        
        assertDeepEquals(mission, manager.getMission(mission.getId()));
        assertDeepEquals(mission2, manager.getMission(mission2.getId()));
        
        mission.setName("Rescue general");
        manager.updateMission(mission);
        assertEquals("Rescue general", manager.getMission(mission.getId()).getName());        
        assertEquals(1, manager.getMission(mission.getId()).getDifficulty());      
        assertEquals("Trenčín", manager.getMission(mission.getId()).getLocation());      
        assertEquals("Just save him.", manager.getMission(mission.getId()).getDetails());
        
        mission.setDifficulty(3);
        manager.updateMission(mission);
        assertEquals("Rescue general", manager.getMission(mission.getId()).getName());        
        assertEquals(3, manager.getMission(mission.getId()).getDifficulty());      
        assertEquals("Trenčín", manager.getMission(mission.getId()).getLocation());      
        assertEquals("Just save him.", manager.getMission(mission.getId()).getDetails());
        
        mission.setLocation("Unknown");
        manager.updateMission(mission);
        assertEquals("Rescue general", manager.getMission(mission.getId()).getName());        
        assertEquals(3, manager.getMission(mission.getId()).getDifficulty());      
        assertEquals("Unknown", manager.getMission(mission.getId()).getLocation());      
        assertEquals("Just save him.", manager.getMission(mission.getId()).getDetails());
        
        mission.setDetails("");
        manager.updateMission(mission);
        assertEquals("Rescue general", manager.getMission(mission.getId()).getName());        
        assertEquals(3, manager.getMission(mission.getId()).getDifficulty());      
        assertEquals("Unknown", manager.getMission(mission.getId()).getLocation());      
        assertEquals("", manager.getMission(mission.getId()).getDetails());
        
        assertDeepEquals(mission2, manager.getMission(mission2.getId()));
    }
    
    @Test
    public void testUpdateWrongMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        manager.createMission(mission);
        manager.createMission(mission2);
        
        //cant set id
        mission.setId(1l);
        tryUpdateMission(mission);
        mission.setId(null);

        //null name
        buildMission(mission, null, 1, "Trenčín", "Just save him.");
        tryUpdateMission(mission);

        //no name
        buildMission(mission, "", 1, "Trenčín", "Just save him.");
        tryUpdateMission(mission);

        //negativ difficulty
        buildMission(mission, "Save general", -1, "Trenčín", "Just save him.");
        tryUpdateMission(mission);

        //no location
        buildMission(mission, "Save general", 1, "", "Just save him.");
        tryUpdateMission(mission);

        //null location
        buildMission(mission, "Save general", 1, null, "Just save him.");
        tryUpdateMission(mission);

        //can be
        buildMission(mission, "Save general", 11, "Trenčín", null);
        tryUpdateMission(mission);
    }

    @Test
    public void testRemoveMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        manager.createMission(mission);
        manager.createMission(mission2);
        
        assertNotNull(manager.getMission(mission.getId()));
        assertNotNull(manager.getMission(mission2.getId()));
        
        manager.removeMission(mission);
        
        assertNull(manager.getMission(mission.getId()));
        assertNotNull(manager.getMission(mission2.getId()));
        
        manager.createMission(mission);
        
        assertNotNull(manager.getMission(mission.getId()));
        assertNotNull(manager.getMission(mission2.getId()));
        
        manager.removeMission(mission2);
        
        assertNull(manager.getMission(mission2.getId()));
        assertNotNull(manager.getMission(mission.getId()));
    }

    @Test
    public void testGetMission() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");

        manager.createMission(mission);
        
        assertNotNull(manager.getMission(mission.getId()));
        assertEquals(mission, manager.getMission(mission.getId()));
        assertDeepEquals(mission, manager.getMission(mission.getId()));
    }

    @Test
    public void testGetAllMissions() {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        manager.createMission(mission);
        manager.createMission(mission2);
        
        List<Mission> expected = Arrays.asList(mission, mission2);
        List<Mission> actual = manager.getAllMissions();
        
        Collections.sort(expected, idComparator);
        Collections.sort(actual, idComparator);
        
        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }

    private void buildMission(Mission mission, String name, int difficulty, String location, String details) {
        mission.setName(name);
        mission.setDifficulty(difficulty);
        mission.setDetails(details);
        mission.setLocation(location);
    }

    private void assertDeepEquals(Mission mission, Mission mission2) {
        assertEquals(mission.getId(), mission.getId());
        assertEquals(mission.getDifficulty(), mission.getDifficulty());
        assertEquals(mission.getName(), mission.getName());
        assertEquals(mission.getDetails(), mission.getDetails());
        assertEquals(mission.getLocation(), mission.getLocation());
    }

    private void tryCreateMission(Mission mission) {
        try {
            manager.createMission(mission);
            fail();
        } catch (Exception ex) {
        }
    }
    
    private void tryUpdateMission(Mission mission) {
        try {
            manager.updateMission(mission);
            fail();
        } catch (Exception ex) {
        }
    }
    
    private static Comparator<Mission> idComparator = new Comparator<Mission>() {

        @Override
        public int compare(Mission o1, Mission o2) {
            return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
        }
    };
    
    private void assertDeepEquals(List<Mission> expected, List<Mission> actual) {
        for(int i = 0; i < expected.size(); i++)
            assertDeepEquals(expected.get(i), actual.get(i));
    }
}