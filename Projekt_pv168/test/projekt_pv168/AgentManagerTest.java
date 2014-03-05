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
public class AgentManagerTest {
    
    public AgentManagerTest() {
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
    public void testCreateAgent() {
        System.out.println("createAgent");
        Agent agent = null;
        AgentManager instance = new AgentManagerImpl();
        instance.createAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateAgent() {
        System.out.println("updateAgent");
        Agent agent = null;
        AgentManager instance = new AgentManagerImpl();
        instance.updateAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveAgent() {
        System.out.println("removeAgent");
        Agent agent = null;
        AgentManager instance = new AgentManagerImpl();
        instance.removeAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAgent() {
        System.out.println("getAgent");
        long id = 0L;
        AgentManager instance = new AgentManagerImpl();
        Agent expResult = null;
        Agent result = instance.getAgent(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAgentWithRank_int() {
        System.out.println("getAgentWithRank");
        int minRank = 0;
        AgentManager instance = new AgentManagerImpl();
        Collection expResult = null;
        Collection result = instance.getAgentWithRank(minRank);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
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

    @Test
    public void testGetAllAgents() {
        System.out.println("getAllAgents");
        AgentManager instance = new AgentManagerImpl();
        Collection expResult = null;
        Collection result = instance.getAllAgents();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class AgentManagerImpl implements AgentManager {

        public void createAgent(Agent agent) {
        }

        public void updateAgent(Agent agent) {
        }

        public void removeAgent(Agent agent) {
        }

        public Agent getAgent(long id) {
            return null;
        }

        public Collection<Agent> getAgentWithRank(int minRank) {
            return null;
        }

        public Collection<Agent> getAgentWithRank(int minRank, int maxRank) {
            return null;
        }

        public Collection<Agent> getAllAgents() {
            return null;
        }
    }
}