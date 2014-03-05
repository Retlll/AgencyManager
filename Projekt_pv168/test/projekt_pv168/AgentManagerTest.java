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
        //instance.createAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEditAgent() {
        System.out.println("editAgent");
        Agent agent = null;
        AgentManager instance = new AgentManagerImpl();
        //instance.editAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveAgent() {
        System.out.println("removeAgent");
        Agent agent = null;
        AgentManager instance = new AgentManagerImpl();
        //instance.removeAgent(agent);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAgent() {
        System.out.println("getAgent");
        Long id = null;
        AgentManager instance = new AgentManagerImpl();
        Agent expResult = null;
        //Agent result = instance.getAgent(id);
        //assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class AgentManagerImpl implements AgentManager {

        public void createAgent(Agent agent) {
        }

        public void editAgent(Agent agent) {
        }

        public void removeAgent(Agent agent) {
        }

        public Agent getAgent(Long id) {
            return null;
        }
    }
}