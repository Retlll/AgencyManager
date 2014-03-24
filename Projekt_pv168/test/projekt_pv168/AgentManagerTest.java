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
public class AgentManagerTest {

    private AgentManagerImpl manager;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/AgencyManager;create=true", "xmalych", "123456");
        connection.prepareStatement("create table AGENT (id int primary key not null generated  always as identity, "
                + "name varchar(50), "
                + "born date, "
                + "active boolean, "
                + "rank int, "
                + "notes varchar(50))").executeUpdate();

        manager = new AgentManagerImpl(connection);
    }

    @After
    public void tearDown() throws SQLException {
        connection.prepareStatement("drop table AGENT").executeUpdate();
        connection.close();
    }
    
    @Test
    public void testCreateAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
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
    public void testCreateWrongAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        //give id
        agent.setId(1l);
        tryCreateAgent(agent);
        agent.setId(null);

        //null name
        buildAgent(agent, null, birthday, true, 1, "");
        tryCreateAgent(agent);

        //short name
        buildAgent(agent, "", birthday, true, 1, "");
        tryCreateAgent(agent);

        //negativ rank
        buildAgent(agent, "James Bond", birthday, true, -1, "");
        tryCreateAgent(agent);

        //should be OK
        buildAgent(agent, "James Bond", birthday, true, 1, null);
        manager.createAgent(agent);
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent.getId()).getNotes());
    }

    @Test
    public void testUpdateAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.clear();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");
        
        //update unexist agent
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception ex) {
        }

        manager.createAgent(agent);
        manager.createAgent(agent2);

        assertDeepEquals(agent, manager.getAgent(agent.getId()));
        assertDeepEquals(agent2, manager.getAgent(agent2.getId()));

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
        assertDeepEquals(agent2, manager.getAgent(agent2.getId()));
    }

    @Test
    public void testUpdateWrongAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");

        manager.createAgent(agent);
        manager.createAgent(agent2);

        //null name
        buildAgent(agent, null, birthday, true, 1, "");
        tryUpdateAgent(agent);

        //short name
        buildAgent(agent, "", birthday, true, 1, "");
        tryUpdateAgent(agent);

        //negativ rank
        buildAgent(agent, "James Bond", birthday, true, -1, "");
        tryUpdateAgent(agent);

        //should be OK
        buildAgent(agent, "James Bond", birthday, true, 1, null);
        manager.updateAgent(agent);
        
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent.getId()).getNotes());        
    }
    
    @Test
    public void testRemoveAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");

        manager.createAgent(agent);
        manager.createAgent(agent2);

        assertNotNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));

        manager.removeAgent(agent);

        assertNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));

        agent.setId(null);
        manager.createAgent(agent);

        assertNotNull(manager.getAgent(agent.getId()));
        assertNotNull(manager.getAgent(agent2.getId()));

        manager.removeAgent(agent2);

        assertNotNull(manager.getAgent(agent.getId()));
        assertNull(manager.getAgent(agent2.getId()));
    }

    @Test
    public void testGetAgent() throws SQLException {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");
        manager.createAgent(agent);

        assertNotNull(manager.getAgent(agent.getId()));
        assertEquals(agent, manager.getAgent(agent.getId()));
        assertDeepEquals(agent, manager.getAgent(agent.getId()));
    }

    @Ignore
    @Test
    public void testGetAgentWithRank_int() {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");

        manager.createAgent(agent);
        manager.createAgent(agent2);

        assertTrue(manager.getAgentWithRank(6).isEmpty());

        assertTrue(manager.getAgentWithRank(5).size() == 1);

        assertTrue(manager.getAgentWithRank(1).size() == 2);

        agent.setRank(5);
        manager.updateAgent(agent);

        assertTrue(manager.getAgentWithRank(5).size() == 2);
    }

    @Ignore
    @Test
    public void testGetAgentWithRank_int_int() {
        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");

        manager.createAgent(agent);
        manager.createAgent(agent2);

        assertTrue(manager.getAgentWithRank(6, 7).isEmpty());
        assertTrue(manager.getAgentWithRank(7, 6).isEmpty());

        assertTrue(manager.getAgentWithRank(5, 7).size() == 1);
        assertTrue(manager.getAgentWithRank(7, 5).size() == 1);

        assertTrue(manager.getAgentWithRank(1, 4).size() == 1);
        assertTrue(manager.getAgentWithRank(4, 1).size() == 1);

        assertTrue(manager.getAgentWithRank(1, 5).size() == 2);
        assertTrue(manager.getAgentWithRank(5, 1).size() == 2);

        agent.setRank(5);
        manager.updateAgent(agent);

        assertTrue(manager.getAgentWithRank(1, 5).size() == 2);
        assertTrue(manager.getAgentWithRank(5, 1).size() == 2);
    }

    @Ignore
    @Test
    public void testGetAllAgents() {
        assertTrue(manager.getAllAgents().isEmpty());

        Calendar birthday = Calendar.getInstance();
        birthday.clear();
        birthday.set(1994, 3, 9);
        Agent agent = new Agent();
        buildAgent(agent, "James Bond", birthday, true, 1, "");

        Calendar birthday2 = Calendar.getInstance();
        birthday2.set(1991, 5, 10);
        Agent agent2 = new Agent();
        buildAgent(agent2, "Peter Novak", birthday2, false, 5, "Some notes");

        manager.createAgent(agent);
        manager.createAgent(agent2);

        List<Agent> expected = Arrays.asList(agent, agent2);
        List<Agent> actual = manager.getAllAgents();

        Collections.sort(expected, idComparator);
        Collections.sort(actual, idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }

    private void buildAgent(Agent agent, String name, Calendar birthday, boolean active, int rank, String notes) {
        //agent.setId(null);
        agent.setName(name);
        agent.setBorn(birthday);
        agent.setActive(active);
        agent.setRank(rank);
        agent.setNotes(notes);
    }

    private void assertDeepEquals(Agent agent, Agent agent2) {
        assertEquals(agent.getId(), agent2.getId());
        assertEquals(agent.getName(), agent2.getName());        
        assertEquals(agent.getBorn().getTimeInMillis(), agent2.getBorn().getTimeInMillis());
        assertEquals(agent.getRank(), agent2.getRank());
        assertEquals(agent.isActive(), agent2.isActive());

        assertEquals(agent.getNotes(), agent2.getNotes());
    }

    private void tryCreateAgent(Agent agent) {
        try {
            manager.createAgent(agent);
            fail();
        } catch (Exception ex) {
        }
    }

    private void tryUpdateAgent(Agent agent) {
        try {
            manager.updateAgent(agent);
            fail();
        } catch (Exception ex) {
        }
    }
    
    private static Comparator<Agent> idComparator = new Comparator<Agent>() {
        @Override
        public int compare(Agent o1, Agent o2) {
            return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
        }
    };

    private void assertDeepEquals(List<Agent> expected, List<Agent> actual) {
        for (int i = 0; i < expected.size(); i++) {
            assertDeepEquals(expected.get(i), actual.get(i));
        }
    }
}