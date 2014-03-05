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
public class ContractManagerTest {
    
    public ContractManagerTest() {
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
    public void testCreateContract() {
        System.out.println("createContract");
        Contract contract = null;
        ContractManager instance = new ContractManagerImpl();
        instance.createContract(contract);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateContract() {
        System.out.println("updateContract");
        Contract contract = null;
        ContractManager instance = new ContractManagerImpl();
        instance.updateContract(contract);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveContract() {
        System.out.println("removeContract");
        Contract contract = null;
        ContractManager instance = new ContractManagerImpl();
        instance.removeContract(contract);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindAllContracts() {
        System.out.println("findAllContracts");
        ContractManager instance = new ContractManagerImpl();
        Collection expResult = null;
        Collection result = instance.findAllContracts();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindAllMissionsForAgent() {
        System.out.println("findAllMissionsForAgent");
        Agent agent = null;
        ContractManager instance = new ContractManagerImpl();
        Collection expResult = null;
        Collection result = instance.findAllMissionsForAgent(agent);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindAllAgentsForMission() {
        System.out.println("findAllAgentsForMission");
        Mission mission = null;
        ContractManager instance = new ContractManagerImpl();
        Collection expResult = null;
        Collection result = instance.findAllAgentsForMission(mission);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class ContractManagerImpl implements ContractManager {

        public void createContract(Contract contract) {
        }

        public void updateContract(Contract contract) {
        }

        public void removeContract(Contract contract) {
        }

        public Collection<Contract> findAllContracts() {
            return null;
        }

        public Collection<Mission> findAllMissionsForAgent(Agent agent) {
            return null;
        }

        public Collection<Agent> findAllAgentsForMission(Mission mission) {
            return null;
        }
    }
}