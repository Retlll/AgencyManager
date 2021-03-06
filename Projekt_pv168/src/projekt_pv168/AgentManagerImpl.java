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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Lenovo
 */
public class AgentManagerImpl implements AgentManager {

    private DataSource dataSource;
    private static final Logger logger = Logger.getLogger(ContractManagerImpl.class.getName());

    public AgentManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    @Override
    public void createAgent(Agent agent) throws ServiceFailureException {
        checkDataSource();

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
        
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ("Creating agent " + agent));
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

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(
                    "insert into AGENT (NAME, BORN, ACTIVE, RANK, NOTES) values (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);) {
                st.setString(1, agent.getName());
                st.setDate(2, new java.sql.Date(agent.getBorn().getTimeInMillis()));
                st.setBoolean(3, agent.isActive());
                st.setInt(4, agent.getRank());
                st.setString(5, agent.getNotes());

                int newRows = st.executeUpdate();
                if (newRows != 1) {
                    throw new ServiceFailureException("More rows in database than excepted when adding agent.");
                }

                ResultSet keys = st.getGeneratedKeys();
                if (keys.getMetaData().getColumnCount() != 1) {
                    throw new IllegalArgumentException("ResultSet contains more columns");
                }
                if (keys.next()) {
                    Long result = keys.getLong(1);
                    if (keys.next()) {
                        throw new IllegalArgumentException("ResultSet contains more rows");
                    }
                    agent.setId(result);
                } else {
                    throw new IllegalArgumentException("ResultSet contain no rows");
                }
                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Unable to work with database, agent is not create", ex);
            } finally {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit and doing rollback", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when working with database.";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void updateAgent(Agent agent) {
        checkDataSource();

        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }
        if (agent.getId() == null) {
            throw new IllegalArgumentException("Agents id is null");
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
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ("Updating agent " + agent));
        }
        
        try (Connection connection = dataSource.getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(
                    "update AGENT set NAME = ?, BORN = ?, ACTIVE = ?, RANK = ?, NOTES = ? where id = ?");) {
                st.setString(1, agent.getName());
                st.setDate(2, new java.sql.Date(agent.getBorn().getTimeInMillis()));
                st.setBoolean(3, agent.isActive());
                st.setInt(4, agent.getRank());
                st.setString(5, agent.getNotes());
                st.setLong(6, agent.getId());

                int count = st.executeUpdate();
                if (count == 0) {
                    throw new IllegalArgumentException("Agent: " + agent + "does not exist in database");
                }
                if (count != 1) {
                    throw new ServiceFailureException("More row than excepten in database.");
                }

                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit and doing rollback", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when updating agent in database";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void removeAgent(Agent agent) {
        checkDataSource();

        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }
        if (agent.getId() == null) {
            throw new IllegalArgumentException("Agents id is null");
        }
        
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ("Deleting agent " + agent));
        }

        try (Connection connection = dataSource.getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement("delete from AGENT where ID = ?");) {
                st.setLong(1, agent.getId());

                int count = st.executeUpdate();
                if (count == 0) {
                    throw new IllegalArgumentException("Agent: " + agent + "does not exist in database");
                }
                if (count != 1) {
                    throw new ServiceFailureException("More row than excepten in database.");
                }

                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit and doing rollback", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when deleting agent from database";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public Agent getAgent(long id) throws SQLException {
        checkDataSource();

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, ("Start geting agent with id:" + id));
        }
        
        try (Connection connection = dataSource.getConnection();) {
            try (PreparedStatement st = connection.prepareStatement("select ID, NAME, BORN, ACTIVE, RANK, NOTES from AGENT where ID = ?");) {
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
                    //born.clear();
                    born.setTimeInMillis(agents.getDate("BORN").getTime());
                    agent.setBorn(born);
                    if (agents.next()) {
                        throw new ServiceFailureException("There is more then one agent with same id");
                    }
                    if(logger.isLoggable(Level.FINE)){
                        logger.log(Level.FINE, "Successful, agent " + agent);
                    }
                    return agent;
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                String msg = "Error when getting agent with id = " + id + " from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Agent> getAgentWithRank(int minRank) {
        checkDataSource();

        try (Connection connection = dataSource.getConnection();) {
            try (PreparedStatement st = connection.prepareStatement("select ID, NAME, BORN, ACTIVE, RANK, NOTES from AGENT where RANK >= ?");) {
                st.setLong(1, minRank);
                ResultSet agents = st.executeQuery();
                if(logger.isLoggable(Level.FINE)){
                    logger.log(Level.FINE, "Get agents with rank " + minRank);
                }

                return getAgentsFromResultSet(agents);

            } catch (SQLException ex) {
                String msg = "Error when getting agents from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Agent> getAgentsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Agent> agents = new ArrayList<>();
        while (resultSet.next()) {
            Agent agent = new Agent();
            agent.setId(resultSet.getLong("ID"));
            agent.setName(resultSet.getString("NAME"));
            agent.setActive(resultSet.getBoolean("ACTIVE"));
            agent.setRank(resultSet.getInt("RANK"));
            agent.setNotes(resultSet.getString("NOTES"));

            Calendar born = Calendar.getInstance();
            //born.clear();
            born.setTimeInMillis(resultSet.getDate("BORN").getTime());
            agent.setBorn(born);

            agents.add(agent);
        }
        return agents;
    }

    @Override
    public List<Agent> getAgentWithRank(int minRank, int maxRank) {
        checkDataSource();

        if (minRank > maxRank) {
            throw new IllegalArgumentException("MaxRank is greater then minRankf");
        }

        try (Connection connection = dataSource.getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement("select ID, NAME, BORN, ACTIVE, RANK, NOTES from AGENT where RANK >= ? AND RANK <= ?");) {
                st.setLong(1, minRank);
                st.setLong(2, maxRank);
                ResultSet agents = st.executeQuery();
                connection.commit();
                
                if(logger.isLoggable(Level.FINE)){
                    logger.log(Level.FINE, "Get agents with rank " + minRank);
                }

                return getAgentsFromResultSet(agents);
            } catch (SQLException ex) {
                String msg = "Error when getting agents from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Agent> getAllAgents() {
        checkDataSource();

        try (Connection connection = dataSource.getConnection();) {
            try (PreparedStatement st = connection.prepareStatement("select ID, NAME, BORN, ACTIVE, RANK, NOTES from AGENT");) {
                ResultSet agents = st.executeQuery();

                if(logger.isLoggable(Level.FINE)){
                    logger.log(Level.FINE, "Geting all agents");
                }
                
                return getAgentsFromResultSet(agents);

            } catch (SQLException ex) {
                String msg = "Error when getting agents from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
