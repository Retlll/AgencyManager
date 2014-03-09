/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Calendar;
import java.util.Collection;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Lenovo
 */
public class AgentManagerTest {
    
    private AgentManagerImpl manager;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
        manager = new AgentManagerImpl();
    }

    @Test
    public void testCreateAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1), "James Bond", birthday, true, 1, "");
        manager.createAgent(agent);
        
        Long id = agent.getId();
        assertNotNull(id);
        assertEquals(agent, manager.getAgent(id));
        assertDeepEquals(agent, manager.getAgent(id));
    }

    @Test
    public void testUpdateAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1), "James Bond", birthday, true, 1, "");  
        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent(Long.valueOf(2), "Peter Novak", birthday2, false, 5, "Some notes");        
        manager.createAgent(agent);
        manager.createAgent(agent2);
        
        assertDeepEquals(agent, manager.getAgent(1));
        
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
        Agent agent3 = new Agent(Long.valueOf(3), "Karol Ignac", birthday3, true, 10, "123");
        manager.updateAgent(agent3);
        assertNull(manager.getAgent(3));
        assertEquals(agent, manager.getAgent(1));
        assertEquals(agent2, manager.getAgent(2));
        
        //update with wrong informations, but maybe in create agent
    }

    @Test
    public void testRemoveAgent() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1), "James Bond", birthday, true, 1, "");  
        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent(Long.valueOf(2), "Peter Novak", birthday2, false, 5, "Some notes");        
        manager.createAgent(agent);
        manager.createAgent(agent2);
        
        assertNotNull(manager.getAgent(1));
        assertNotNull(manager.getAgent(2));
        
        manager.removeAgent(agent);
        
        assertNull(manager.getAgent(1));
        assertNotNull(manager.getAgent(2));
    }

    @Test
    public void testGetAgent() {
        assertNull(manager.getAgent(1));
        
        Calendar birthday = Calendar.getInstance();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1), "James Bond", birthday, true, 1, "");
        manager.createAgent(agent);
        
        assertNotNull(manager.getAgent(1));
        assertEquals(agent, manager.getAgent(1));
        assertDeepEquals(agent, manager.getAgent(1));
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