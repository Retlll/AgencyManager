/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Lenovo
 */
public class MissionManagerTest {

    private MissionManagerImpl manager;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/AgencyManager;create=true", "xmalych", "123456");
        connection.prepareStatement("create table MISSION ( "
                + "id int primary key not null generated  always as identity, "
                + "name varchar(50), "
                + "difficulty int, "
                + "details varchar(50), "
                + "location varchar(50))").executeUpdate();
        manager = new MissionManagerImpl(connection);
    }
    
    @After
    public void tearDown() throws SQLException {
        connection.prepareStatement("drop table MISSION").executeUpdate();
        connection.close();
    }

    @Test
    public void testCreateMission() throws SQLException {
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

        //this can be
        buildMission(mission, "Save general", 11, "Trenčín", null);
        manager.createMission(mission);
    }

    @Test
    public void testUpdateMission() throws SQLException {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        try{
            manager.updateMission(mission);
            fail();
        }catch(Exception ex){
        }
        
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
    public void testUpdateWrongMission() throws SQLException {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");
        
        Mission mission2 = new Mission();
        buildMission(mission2, "Kill general", 2, "Trenčín", "");
        
        manager.createMission(mission);
        manager.createMission(mission2);

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

        //this can be, add assert
        buildMission(mission, "Save general", 11, "Trenčín", null);
        manager.updateMission(mission);
        
        assertEquals(mission, manager.getMission(mission.getId()));
        assertNull(manager.getMission(mission.getId()).getDetails());
    }

    @Test
    public void testRemoveMission() throws SQLException {
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
        
        mission.setId(null);
        manager.createMission(mission);
        
        assertNotNull(manager.getMission(mission.getId()));
        assertNotNull(manager.getMission(mission2.getId()));
        
        manager.removeMission(mission2);
        
        assertNull(manager.getMission(mission2.getId()));
        assertNotNull(manager.getMission(mission.getId()));
    }

    @Test
    public void testGetMission() throws SQLException {
        Mission mission = new Mission();
        buildMission(mission, "Save general", 1, "Trenčín", "Just save him.");

        manager.createMission(mission);
        
        assertNotNull(manager.getMission(mission.getId()));
        assertEquals(mission, manager.getMission(mission.getId()));
        assertDeepEquals(mission, manager.getMission(mission.getId()));
    }

    @Ignore
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
        mission.setLocation(location);
        mission.setDetails(details);
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