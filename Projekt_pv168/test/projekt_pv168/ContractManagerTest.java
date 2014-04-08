/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lenovo
 */
public class ContractManagerTest {

    private ContractManagerImpl manager;
    private MissionManager missionManager;
    private AgentManager agentManager;
    private DataSource ds;

    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby://localhost:1527/AgencyManager;create=true");
        ds.setUsername("xmalych");
        ds.setPassword("123456");
        return ds;
    }

    @Before
    public void setUp() throws SQLException {
        
        //connection = DriverManager.getConnection("jdbc:derby://localhost:1527/AgencyManager;create=true", "xmalych", "123456");
        ds = prepareDataSource();
        ds.getConnection().prepareStatement("create table CONTRACT("
                + "missionId bigint,"
                + "agentId bigint,"
                + "budget bigint,"
                + "startTime Date,"
                + "endTime Date)").executeUpdate();

        missionManager = new MissionManagerImplTest();
        agentManager = new AgentManagerImplTest();
        manager = new ContractManagerImpl(ds, missionManager, agentManager);
    }

    @After
    public void tearDown() throws SQLException {
        ds.getConnection().prepareStatement("drop table CONTRACT").executeUpdate();
    }

    @Test
    public void testCreateContract() {
        Mission mission = newMission1();
        Agent agent = newAgent1();
        Long missionID = mission.getId();
        Long agentID1 = agent.getId();
        Contract contract = newContract1(mission, agent);

        manager.createContract(contract);
        agent = contract.getAgent();
        mission = contract.getMission();

        assertNotNull("Agent is null.", agent);
        assertNotNull("Mission is null.", mission);
        assertEquals("Contracts do not match.", contract, manager.getContract(mission, agent));
        assertNotSame(contract, manager.getContract(mission, agent));
        assertDeepEquals(contract, manager.getContract(mission, agent));

        contract = newContract1(null, agent);
        try {
            manager.createContract(contract);
            fail("Mission is null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        contract = newContract1(mission, null);
        try {
            manager.createContract(contract);
            fail("Agent is null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        contract = newContract1(null, null);
        try {
            manager.createContract(contract);
            fail("Agent and Mission are null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        Contract c2 = newContract2(mission, agent);
        try {
            manager.createContract(c2);
            fail("Multiple instaces for the same mission - agent allowed.");
        } catch (Exception ex) {
        }

        Agent a2 = newAgent2();
        Long agentID2 = a2.getId();
        c2 = newContract2(mission, a2);
        c2.setBudget(-1l);

        try {
            manager.createContract(c2);
            fail("Budget is negative, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        c2.setBudget(1000000000000000001l);
        try {
            manager.createContract(c2);
            fail("Budget is too high, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        c2.setBudget(10l);

        Calendar tmp = c2.getEndTime();
        c2.setEndTime(c2.getStartTime());
        c2.setStartTime(tmp);

        try {
            manager.createContract(c2);
            fail("Start time is later then end time, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        tmp = c2.getEndTime();
        c2.setEndTime(c2.getStartTime());
        c2.setStartTime(tmp);

        mission.setId(null);

        try {
            manager.createContract(c2);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        a2.setId(null);

        try {
            manager.createContract(c2);
            fail("Mission and Agent have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        mission.setId(missionID);
        try {
            manager.createContract(c2);
            fail("Agent have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        a2.setId(agentID2 + agentID1 + 1);
        try {
            manager.createContract(c2);
            fail("Agent have unknown ID, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        a2.setId(agentID2);
        mission.setId(missionID + 1);

        try {
            manager.createContract(c2);
            fail("Mission have unknown ID, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        mission.setId(missionID);
        c2.setStartTime(null);
        manager.createContract(c2);
        try {
            manager.createContract(c2);
            fail("Multiple instances for same mission allowed");
        } catch (IllegalArgumentException ex) {
        }

    }

    @Test
    public void testUpdateContract() {
        Contract contract = newContract2(newMission1(), newAgent2());
        Contract c2 = newContract1(newMission2(), newAgent1());

        manager.createContract(contract);
        manager.createContract(c2);

        Mission mission = contract.getMission();
        Agent agent = contract.getAgent();
        Long missionID1 = mission.getId();
        Long agentID1 = agent.getId();
        Calendar start = contract.getStartTime();
        Calendar end = contract.getEndTime();
        long budget = contract.getBudget();

        contract = manager.getContract(mission, agent);
        contract.setBudget(5054);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, budget, start, end);
        manager.updateContract(contract);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, start, end);

        contract = manager.getContract(mission, agent);
        Calendar calStr = new GregorianCalendar();
        calStr.clear();
        contract.setStartTime(calStr);
        calStr.set(1856, 5, 6);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, start, end);
        manager.updateContract(contract);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, end);

        contract = manager.getContract(mission, agent);
        Calendar calEnd = new GregorianCalendar();
        calEnd.clear();
        calEnd.set(2015, 5, 6);
        contract.setEndTime(calEnd);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, end);
        manager.updateContract(contract);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, calEnd);

        contract = manager.getContract(mission, agent);
        contract.setEndTime(null);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, calEnd);
        manager.updateContract(contract);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, null);

        contract = manager.getContract(mission, agent);
        contract.setStartTime(null);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, calStr, null);
        manager.updateContract(contract);
        assertDeepEquals(manager.getContract(contract.getMission(), contract.getAgent()), mission, agent, 5054, null, null);

        mission = c2.getMission();
        agent = c2.getAgent();
        Long missionID2 = mission.getId();
        Long agentID2 = agent.getId();

        assertDeepEquals(c2, manager.getContract(mission, agent));

        c2.setBudget(-1l);

        try {
            manager.updateContract(c2);
            fail("Budget is negative, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        c2.setBudget(1000000000000000001l);
        try {
            manager.updateContract(c2);
            fail("Budget is too high, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        c2.setBudget(10l);
        c2.setEndTime(calStr);
        c2.setStartTime(calEnd);

        try {
            manager.updateContract(c2);
            fail("End time is sooner than start time, yet still accepted");
        } catch (IllegalArgumentException ex) {
        }

        c2.setEndTime(calEnd);
        c2.setStartTime(calStr);
        mission.setId(null);

        try {
            manager.updateContract(c2);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        agent.setId(null);

        try {
            manager.updateContract(c2);
            fail("Mission and Agent have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        mission.setId(missionID2);

        try {
            manager.updateContract(c2);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        agent.setId(agentID2 + agentID1 + 1);
        try {
            manager.updateContract(c2);
            fail("Agent have unknown ID, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        
        agent.setId(agentID2);
        mission.setId(missionID2 + missionID1 + 1);
        
        try {
            manager.updateContract(c2);
            fail("Mission have uknown ID, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        mission.setId(missionID2);
        c2.setStartTime(null);
        manager.updateContract(c2);
        assertEquals(c2, manager.getContract(c2.getMission(), c2.getAgent()));
    }

    @Test
    public void testRemoveContract() {
        Mission m1 = newMission1();
        Mission m2 = newMission2();
        Agent a1 = newAgent1();
        Agent a2 = newAgent2();
        Contract c1 = newContract1(m1, a1);
        Contract c2 = newContract2(m2, a2);

        manager.createContract(c1);
        manager.createContract(c2);

        assertNotNull(manager.getContract(m1, a1));
        assertNotNull(manager.getContract(m2, a2));

        manager.removeContract(c2);

        assertNotNull(manager.getContract(m1, a1));
        assertNull(manager.getContract(m2, a2));

        manager.removeContract(c1);

        assertNull(manager.getContract(m1, a1));
        assertNull(manager.getContract(m2, a2));

        try {
            manager.removeContract(null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        manager.createContract(c2);
        c2.setAgent(a1);
        try {
            manager.removeContract(c2);
            fail();
        } catch (Exception ex) {
        }

        c2.setAgent(null);
        try {
            manager.removeContract(c2);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c2.setAgent(a2);
        manager.removeContract(c2);
        manager.createContract(c2);

        c2.setMission(m1);
        try {
            manager.removeContract(c2);
            fail();
        } catch (Exception ex) {
        }

        c2.setMission(null);
        try {
            manager.removeContract(c2);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c2.setAgent(null);
        try {
            manager.removeContract(c2);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        manager.createContract(c1);

        m1.setId(null);

        try {
            manager.removeContract(c1);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        a1.setId(null);

        try {
            manager.removeContract(c1);
            fail("Mission and Agent have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        m1.setId(Long.valueOf(1l));

        try {
            manager.removeContract(c1);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }


    }

    @Test
    public void testGetContract() {
        Mission mission = newMission1();
        Agent agent = newAgent1();

        assertNull(manager.getContract(mission, agent));
        assertNull(manager.getContract(mission.getId().longValue(), agent.getId().longValue()));

        try {
            manager.getContract(null, agent);
            fail("Mission is null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        try {
            manager.getContract(mission, null);
            fail("Agent is null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        try {
            manager.getContract(null, null);
            fail("Agent and Mission are null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        mission.setId(null);

        try {
            manager.getContract(mission, agent);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        agent.setId(null);

        try {
            manager.getContract(mission, agent);
            fail("Mission and Agent have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        mission.setId(Long.valueOf(1l));

        try {
            manager.getContract(mission, agent);
            fail("Mission have ID null, yet still accepted");
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        agent.setId(Long.valueOf(1l));

        Contract contract = newContract1(mission, agent);
        manager.createContract(contract);
        agent = contract.getAgent();
        mission = contract.getMission();

        assertEquals(contract, manager.getContract(mission, agent));
        assertNotSame(contract, manager.getContract(mission, agent));
        assertDeepEquals(contract, manager.getContract(mission, agent));
        assertEquals(contract, manager.getContract(mission.getId(), agent.getId()));
        assertNotSame(contract, manager.getContract(mission.getId(), agent.getId()));
        assertDeepEquals(contract, manager.getContract(mission.getId(), agent.getId()));
    }

    @Test
    public void testFindAllContracts() {

        Contract c1 = newContract1(newMission1(), newAgent1());
        Contract c2 = newContract2(newMission2(), newAgent2());
        Contract c3 = newContract2(c1.getMission(), c2.getAgent());

        Long missID = c1.getMission().getId();
        Long agentID = c1.getAgent().getId();

        try {
            manager.findAllContracts((Agent) null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        try {
            manager.findAllContracts((Mission) null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getAgent().setId(null);
        c1.getMission().setId(null);

        try {
            manager.findAllContracts(c1.getAgent());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        try {
            manager.findAllContracts(c1.getMission());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getAgent().setId(agentID);
        c1.getMission().setId(missID);

        assertEquals(0, manager.findAllContracts().size());
        assertEquals(0, manager.findAllContracts(c1.getMission()).size());
        assertEquals(0, manager.findAllContracts(c2.getAgent()).size());

        manager.createContract(c1);
        assertEquals(1, manager.findAllContracts().size());
        assertEquals(1, manager.findAllContracts(c1.getMission()).size());
        assertEquals(0, manager.findAllContracts(c2.getAgent()).size());

        manager.createContract(c2);
        assertEquals(2, manager.findAllContracts().size());
        assertEquals(1, manager.findAllContracts(c1.getMission()).size());
        assertEquals(1, manager.findAllContracts(c2.getAgent()).size());

        manager.createContract(c3);
        assertEquals(3, manager.findAllContracts().size());
        assertEquals(2, manager.findAllContracts(c1.getMission()).size());
        assertEquals(2, manager.findAllContracts(c2.getAgent()).size());


        List<Contract> expected = Arrays.asList(c1, c2, c3);
        List<Contract> actual = manager.findAllContracts();

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual); // only if order is required.

        expected = Arrays.asList(c2, c3);
        actual = manager.findAllContracts(c2.getAgent());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual); // only if order is required.

        expected = Arrays.asList(c1, c3);
        actual = manager.findAllContracts(c1.getMission());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual); // only if order is required.

        manager.removeContract(c1);
        assertEquals(2, manager.findAllContracts().size());
        assertEquals(1, manager.findAllContracts(c1.getMission()).size());
        assertEquals(2, manager.findAllContracts(c2.getAgent()).size());
    }

    @Test
    public void testAllMissionsForAgent() {
        Contract c1 = newContract1(newMission1(), newAgent1());
        Contract c2 = newContract2(newMission2(), newAgent2());
        Contract c3 = newContract1(newMission3(), c1.getAgent());
        Contract c4 = newContract2(c2.getMission(), c1.getAgent());
        Contract c5 = newContract1(c1.getMission(), c2.getAgent());
        
        Long agentID = c1.getAgent().getId();

        try {
            manager.findAllMissionsForAgent(null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        
        try {
            manager.removeAllMissionsForAgent((Agent)null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getAgent().setId(null);

        try {
            manager.findAllMissionsForAgent(c1.getAgent());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        
        try {
            manager.removeAllMissionsForAgent(c1.getAgent());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getAgent().setId(agentID);

        assertEquals(0, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(0, manager.findAllMissionsForAgent(c2.getAgent()).size());


        manager.createContract(c1);
        assertEquals(1, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(0, manager.findAllMissionsForAgent(c2.getAgent()).size());

        manager.createContract(c2);
        assertEquals(1, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(1, manager.findAllMissionsForAgent(c2.getAgent()).size());

        manager.createContract(c3);
        assertEquals(2, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(1, manager.findAllMissionsForAgent(c2.getAgent()).size());

        manager.createContract(c4);
        assertEquals(3, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(1, manager.findAllMissionsForAgent(c2.getAgent()).size());

        manager.createContract(c5);
        assertEquals(3, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(2, manager.findAllMissionsForAgent(c2.getAgent()).size());

        List<Mission> expected = Arrays.asList(c1.getMission(), c3.getMission(), c4.getMission());
        List<Mission> actual = manager.findAllMissionsForAgent(c1.getAgent());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);

        expected = Arrays.asList(c2.getMission(), c5.getMission());
        actual = manager.findAllMissionsForAgent(c2.getAgent());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
        
        manager.removeAllMissionsForAgent(c1.getAgent());
        assertEquals(0, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(2, manager.findAllMissionsForAgent(c2.getAgent()).size());
        
        manager.removeAllMissionsForAgent(c2.getAgent());
        assertEquals(0, manager.findAllMissionsForAgent(c1.getAgent()).size());
        assertEquals(0, manager.findAllMissionsForAgent(c2.getAgent()).size());
    }

    @Test
    public void testAllAgentsForMission() {
        Contract c1 = newContract1(newMission1(), newAgent1());
        Contract c2 = newContract2(newMission2(), newAgent2());
        Contract c3 = newContract1(newMission3(), c1.getAgent());
        Contract c4 = newContract2(c2.getMission(), c1.getAgent());
        Contract c5 = newContract1(c1.getMission(), c2.getAgent());
        
        Long missID = c1.getAgent().getId();

        try {
            manager.findAllAgentsForMission(null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        
        try {
            manager.removeAllAgentsForMission(null);
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getMission().setId(null);

        try {
            manager.findAllAgentsForMission(c1.getMission());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }
        
        try {
            manager.removeAllAgentsForMission(c1.getMission());
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
        }

        c1.getMission().setId(missID);

        assertEquals(0, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c3.getMission()).size());

        manager.createContract(c1);
        assertEquals(1, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c3.getMission()).size());

        manager.createContract(c2);
        assertEquals(1, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c3.getMission()).size());

        manager.createContract(c3);
        assertEquals(1, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c3.getMission()).size());

        manager.createContract(c4);
        assertEquals(1, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(2, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c3.getMission()).size());

        manager.createContract(c5);
        assertEquals(2, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(2, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c3.getMission()).size());

        List<Agent> expected = Arrays.asList(c1.getAgent(), c5.getAgent());
        List<Agent> actual = manager.findAllAgentsForMission(c1.getMission());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);

        expected = Arrays.asList(c4.getAgent(), c2.getAgent());
        actual = manager.findAllAgentsForMission(c2.getMission());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);

        expected = Arrays.asList(c3.getAgent());
        actual = manager.findAllAgentsForMission(c3.getMission());

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
        
        manager.removeAllAgentsForMission(c1.getMission());
        assertEquals(0, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(2, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c3.getMission()).size());
        
        manager.removeAllAgentsForMission(c2.getMission());
        assertEquals(0, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(1, manager.findAllAgentsForMission(c3.getMission()).size());
        
        manager.removeAllAgentsForMission(c3.getMission());
        assertEquals(0, manager.findAllAgentsForMission(c1.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c2.getMission()).size());
        assertEquals(0, manager.findAllAgentsForMission(c3.getMission()).size());
    }

    private Contract newContract1(Mission mission, Agent agent) {
        Calendar start = Calendar.getInstance();
        start.clear();
        start.set(1993, 12, 5);
        Contract contract = new Contract(mission, agent, 1000000000, start, null);
        return contract;
    }

    private Contract newContract2(Mission mission, Agent agent) {
        Calendar start = Calendar.getInstance();
        start.clear();
        start.set(1998, 11, 5);
        Calendar end = Calendar.getInstance();
        end.clear();
        end.set(1999, 12, 5);
        Contract contract = new Contract(mission, agent, 100008000, start, end);
        return contract;
    }

    private Agent newAgent1() {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent(Long.valueOf(1l), "James Bond", birthday, true, 1, "");
        agentManager.createAgent(agent);
        return agent;
    }

    private Agent newAgent2() {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1998, 5, 5);
        Agent agent = new Agent(Long.valueOf(2l), "me", birthday, false, 1, "");
        agentManager.createAgent(agent);
        return agent;
    }

    private Mission newMission1() {
        Mission mission = new Mission();
        mission.setId(Long.valueOf(1l));
        missionManager.createMission(mission);
        return mission;
    }

    private Mission newMission2() {
        Mission mission = new Mission();
        mission.setId(Long.valueOf(2l));
        missionManager.createMission(mission);
        return mission;
    }

    private Mission newMission3() {
        Mission mission = new Mission();
        mission.setId(Long.valueOf(3l));
        missionManager.createMission(mission);
        return mission;
    }

    private void assertDeepEquals(Contract expected, Contract result) {
        assertEquals(expected.getAgent(), result.getAgent());
        assertEquals(expected.getMission(), result.getMission());
        assertEquals(expected.getBudget(), result.getBudget());
        assertEquals(expected.getStartTime(), result.getStartTime());
        assertEquals(expected.getEndTime(), result.getEndTime());
    }

    private void assertDeepEquals(Contract result, Mission mission, Agent agent, long budget, Calendar starTime, Calendar endTime) {
        assertEquals(agent, result.getAgent());
        assertEquals(mission, result.getMission());
        assertEquals(budget, result.getBudget());
        assertEquals(starTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
    }

    private void assertDeepEquals(List<Contract> expectedList, List<Contract> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Contract expected = expectedList.get(i);
            Contract actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}

class AgentManagerImplTest implements AgentManager {

    private Map<Long, Agent> agents = new TreeMap<>();

    @Override
    public void createAgent(Agent agent) {
        agents.put(agent.getId(), agent);
    }

    @Override
    public void updateAgent(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAgent(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Agent getAgent(long id) throws SQLException {
        return agents.get(id);
    }

    @Override
    public List<Agent> getAgentWithRank(int minRank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Agent> getAgentWithRank(int minRank, int maxRank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Agent> getAllAgents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class MissionManagerImplTest implements MissionManager {

    private Map<Long, Mission> missions = new TreeMap<>();

    @Override
    public void createMission(Mission mission) {
        missions.put(mission.getId(), mission);
    }

    @Override
    public void updateMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mission getMission(long id) throws SQLException {
        return missions.get(id);
    }

    @Override
    public List<Mission> getAllMissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}