/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class AgentManagerImpl implements AgentManager {

    public AgentManagerImpl(Connection conn) {
        this.connection = conn;
    }
    private Connection connection;

    @Override
    public void createAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent is null");
        }
        if (agent.getId() != null) {
            throw new IllegalArgumentException("ID already set");
        }
        if (agent.getName() == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (agent.getName().length() == 0) {
            throw new IllegalArgumentException("No name");
        }
        if (agent.getBorn() == null) {
            throw new IllegalArgumentException("Born date is null");
        }
        if (agent.getRank() < 0) {
            throw new IllegalArgumentException("Negative rank");
        }

        //check database string length

        //how old is he
        Calendar actualDate = Calendar.getInstance();
        int age = actualDate.get(Calendar.YEAR) - agent.getBorn().get(Calendar.YEAR);
        if (actualDate.get(Calendar.DAY_OF_YEAR) <= agent.getBorn().get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if (age < 18) {
            throw new IllegalArgumentException("agent should be adult");
        }

        try (PreparedStatement st = connection.prepareStatement("insert into AGENT (NAME, BORN, ACTIVE, RANK, NOTES) "
                + "values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {
            st.setString(1, agent.getName());
            st.setDate(2, new java.sql.Date(agent.getBorn().getTimeInMillis()));
            st.setBoolean(3, agent.isActive());
            st.setInt(4, agent.getRank());
            st.setString(5, agent.getNotes());

            int newRows = st.executeUpdate();
            if (newRows != 1) {
                throw new SQLException("More rows then excepted");
            }

            ResultSet keys = st.getGeneratedKeys();

            if (keys.next()) {
                if (keys.getMetaData().getColumnCount() != 1) {
                    throw new SQLException("Key table has more collumns then excepted");
                }
                Long tempKey = keys.getLong(1);
                if (keys.next()) {
                    throw new SQLException("More then one row in key table");
                }
                agent.setId(tempKey);
            } else {
                throw new SQLException("No row in key table");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgentManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateAgent(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //mám vymazať agenta aj podľa ID???
    @Override
    public void removeAgent(Agent agent) {
        try (PreparedStatement st = connection.prepareStatement(
                "delete from AGENT where NAME = ? and BORN = ? and ACTIVE = ? and RANK = ? and NOTES = ?");) {
            st.setString(1, agent.getName());
            st.setDate(2, new java.sql.Date(agent.getBorn().getTimeInMillis()));
            st.setBoolean(3, agent.isActive());
            st.setInt(4, agent.getRank());
            st.setString(5, agent.getNotes());
            st.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AgentManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Agent getAgent(long id) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(
                "select ID, NAME, BORN, ACTIVE, RANK, NOTES from AGENT where ID = ?");) {
            st.setLong(1, id);
            ResultSet agents = st.executeQuery();

            if (agents.next()) {
                Agent agent = new Agent();
                agent.setId(id);
                agent.setName(agents.getString("NAME"));
                agent.setActive(agents.getBoolean("ACTIVE"));
                agent.setRank(agents.getInt("RANK"));
                agent.setNotes(agents.getString("NOTES"));
                Calendar born = Calendar.getInstance();
                born.setTimeInMillis(agents.getDate("BORN").getTime());
                agent.setBorn(born);
                if (agents.next()) {
                    throw new SQLException("There is more then one agent with same id");
                }
                return agent;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new SQLException("Error in creatign prepared statement");
        }
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

    @Override
    public boolean checkAgent(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
