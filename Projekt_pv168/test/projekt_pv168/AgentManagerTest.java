/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Calendar;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Lenovo
 */
public class AgentManagerTest {
    
    private AgentManagerImpl manager;
    
    @Before
    public void setUp() {
        manager = new AgentManagerImpl();
    }

    @Test
    public void testCreateAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");
        
        manager.createAgent(agent);
        
        Long id = agent.getId();
        assertNotNull(id);
        assertEquals(agent, manager.getAgent(id));
        assertNotSame(agent, manager.getAgent(id));
        assertDeepEquals(agent, manager.getAgent(id));
    }
    
    @Test
    public void testCreateWrongAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");
        
        //give id
        agent.setId(1l);
        try {
            manager.createAgent(agent);
            fail();
        } catch (Exception ex) {}
        
        //null name
        buildAgent(agent, null, birthday, true, 1, "");
        try {
            manager.createAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //short name
        buildAgent(agent, "", birthday, true, 1, "");
        try {
            manager.createAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //negativ rank
        buildAgent(agent, "James Bond", birthday, true, -1, "");
        try {
            manager.createAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //should be OK
        buildAgent(agent, "James Bond", birthday, true, 1, null);
        manager.createAgent(agent);
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent.getId()).getNotes());
    }

    @Test
    public void testUpdateAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");
        
        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");
        
        //update unexist agent
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception ex) {}
        
        manager.createAgent(agent);
        manager.createAgent(agent2);
        
        assertDeepEquals(agent, manager.getAgent(agent.getId()));
        
        agent.setName("Lukas Novotny");
        manager.updateAgent(agent);
        assertEquals("Lukas Novotny", agent.getName());
        assertEquals(birthday, agent.getBorn());
        assertEquals(true, agent.isActive());
        assertEquals(1, agent.getRank());
        assertEquals("", agent.getNotes());
        
        birthday.set(1995, 7, 16);
        agent.setBorn(birthday);
        manager.updateAgent(agent);
        assertEquals("Lukas Novotny", agent.getName());
        assertEquals(birthday, agent.getBorn());
        assertEquals(true, agent.isActive());
        assertEquals(1, agent.getRank());
        assertEquals("", agent.getNotes());
        
        agent.setActive(false);
        manager.updateAgent(agent);
        assertEquals("Lukas Novotny", agent.getName());
        assertEquals(birthday, agent.getBorn());
        assertEquals(false, agent.isActive());
        assertEquals(1, agent.getRank());
        assertEquals("", agent.getNotes());
        
        agent.setRank(2);
        manager.updateAgent(agent);
        assertEquals("Lukas Novotny", agent.getName());
        assertEquals(birthday, agent.getBorn());
        assertEquals(false, agent.isActive());
        assertEquals(2, agent.getRank());
        assertEquals("", agent.getNotes());
        
        agent.setNotes(null);
        manager.updateAgent(agent);
        assertEquals("Lukas Novotny", agent.getName());
        assertEquals(birthday, agent.getBorn());
        assertEquals(false, agent.isActive());
        assertEquals(2, agent.getRank());
        assertNull(agent.getNotes());        
        
        assertEquals(agent2, manager.getAgent(2));
        
        //update absent agent
        Calendar birthday3 = Calendar.getInstance();
        birthday3.set(1990, 2, 7);
        Agent agent3 = new Agent();
        buildAgent(agent3, "Karol Ignac", birthday3, true, 10, "123");
        manager.updateAgent(agent3);
        assertNull(manager.getAgent(3));
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertEquals(agent2, manager.getAgent(agent2.getId()));
        
        //give id
        agent.setId(1l);
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception ex) {}
        
        //null name
        buildAgent(agent, null, birthday, true, 1, "");
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //short name
        buildAgent(agent, "", birthday, true, 1, "");
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //negativ rank
        buildAgent(agent, "James Bond", birthday, true, -1, "");
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception  ex) {}
        
        //should be OK
        buildAgent(agent, "James Bond", birthday, true, 1, null);
        manager.updateAgent(agent);
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent.getId()).getNotes());
    }

    @Test
    public void testRemoveAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");
        
        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent, "Peter Novak", birthday2, false, 5, "Some notes");
        
        manager.createAgent(agent);
        manager.createAgent(agent2);
        
        assertNotNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));
        
        manager.removeAgent(agent);
        
        assertNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));
        
        manager.createAgent(agent);
        
        assertNotNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));
        
        manager.removeAgent(agent2);
        
        assertNotNull(manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent2.getId()));
    }

    @Test
    public void testGetAgent() {        
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1), "James Bond", birthday, true, 1, "");
        manager.createAgent(agent);
        
        assertNotNull(manager.getAgent(agent.getId()));
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertDeepEquals(agent, manager.getAgent(agent.getId()));
    }

    @Ignore("not ready yet") @Test
    public void testGetAgentWithRank_int() {
        System.out.println("getAgentWithRank");
        int minRank = 0;
        AgentManager instance = new AgentManagerImpl();
        Collection expResult = null;
        Collection result = instance.getAgentWithRank(minRank);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Ignore("not ready yet") @Test
    public void testGetAgentWithRank_int_int() {
        System.out.println("getAgentWithRank");
        int minRank = 0;
        int maxRank = 0;
        AgentManager instance = new AgentManagerImpl();
        Collection expResult = null;
        Collection result = instance.getAgentWithRank(minRank, maxRank);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Ignore("not ready yet") @Test
    public void testGetAllAgents() {
        System.out.println("getAllAgents");
        AgentManager instance = new AgentManagerImpl();
        Collection expResult = null;
        Collection result = instance.getAllAgents();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
    public void buildAgent(Agent agent, String name, Calendar birthday, boolean active, int rank, String notes){
        agent.setName(name);
        agent.setBorn(birthday);
        agent.setActive(active);
        agent.setRank(rank);
        agent.setNotes(notes);
    }
        
    public void assertDeepEquals(Agent agent, Agent agent2)
    {
        assertEquals(agent.getId(), agent2.getId());
        assertEquals(agent.getName(), agent2.getName());
        assertEquals(agent.getBorn(), agent2.getBorn());
        assertEquals(agent.getRank(), agent2.getRank());
        assertEquals(agent.isActive(), agent2.isActive());
        
        assertEquals(agent.getNotes(), agent2.getNotes());
    }
}