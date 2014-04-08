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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Lenovo
 */
public class MissionManagerImpl implements MissionManager {

    private DataSource dataSource;
    private static final Logger logger = Logger.getLogger(AgentManagerImpl.class.getName());

    public MissionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    @Override
    public void createMission(Mission mission) {
        checkDataSource();

        if (mission.getId() != null) {
            throw new IllegalArgumentException("Id is not null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (mission.getName().length() == 0) {
            throw new IllegalArgumentException("Name is empty");
        }
        if (mission.getDifficulty() < 0) {
            throw new IllegalArgumentException("negative difficulty");
        }
        if (mission.getLocation() == null) {
            throw new IllegalArgumentException("Location is null");
        }
        if (mission.getLocation().length() == 0) {
            throw new IllegalArgumentException("Location is empty");
        }

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(
                    "insert into MISSION (NAME, DIFFICULTY, DETAILS, LOCATION) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {
                st.setString(1, mission.getName());
                st.setInt(2, mission.getDifficulty());
                st.setString(3, mission.getDetails());
                st.setString(4, mission.getLocation());

                int newRows = st.executeUpdate();
                if (newRows != 1) {
                    throw new ServiceFailureException("More rows then excepted");
                }

                ResultSet keys = st.getGeneratedKeys();
                if (keys.getMetaData().getColumnCount() != 1) {
                    throw new IllegalArgumentException("Key table has more collumns then excepted");
                }
                if (keys.next()) {
                    Long tempKey = keys.getLong(1);
                    if (keys.next()) {
                        throw new IllegalArgumentException("More then one row in key table");
                    }
                    mission.setId(tempKey);
                } else {
                    throw new IllegalArgumentException("No row in key table");
                }

                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                try {
                    connection.rollback();
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when doing rollback", ex);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when adding mission to database.";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void updateMission(Mission mission) {
        checkDataSource();

        if (mission.getId() == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (mission.getName().length() == 0) {
            throw new IllegalArgumentException("Name is empty");
        }
        if (mission.getDifficulty() < 0) {
            throw new IllegalArgumentException("negative difficulty");
        }
        if (mission.getLocation() == null) {
            throw new IllegalArgumentException("Location is null");
        }
        if (mission.getLocation().length() == 0) {
            throw new IllegalArgumentException("Location is empty");
        }
        try (Connection connection = dataSource.getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(
                    "update MISSION set NAME = ?, DIFFICULTY = ?, DETAILS = ?, LOCATION = ? where id = ?");) {
                st.setString(1, mission.getName());
                st.setInt(2, mission.getDifficulty());
                st.setString(3, mission.getDetails());
                st.setString(4, mission.getLocation());
                st.setLong(5, mission.getId());

                int count = st.executeUpdate();
                if (count == 0) {
                    throw new IllegalArgumentException("Mission: " + mission + "does not exist in database");
                }
                if (count != 1) {
                    throw new ServiceFailureException("More row than excepten in database.");
                }


                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                try {
                    connection.rollback();
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when doing rollback", ex);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when updating agent in database";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void removeMission(Mission mission) {
        checkDataSource();

        if (mission.getId() == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Name is null");
        }
        try (Connection connection = dataSource.getConnection();) {
            connection.setAutoCommit(false);
            try (PreparedStatement st = connection.prepareStatement(
                    "delete from MISSION where ID = ?");) {
                st.setLong(1, mission.getId());

                int count = st.executeUpdate();
                if (count == 0) {
                    throw new IllegalArgumentException("Mission: " + mission + "does not exist in database");
                }
                if (count != 1) {
                    throw new ServiceFailureException("More row than excepten in database.");
                }

                connection.commit();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                try {
                    connection.rollback();
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when doing rollback", ex);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException exc) {
                    logger.log(Level.SEVERE, "Error when setting autocommit", exc);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when deleting mission from database";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public Mission getMission(long id) throws SQLException {
        checkDataSource();

        try (Connection connection = dataSource.getConnection();) {
            try (PreparedStatement st = connection.prepareStatement(
                    "select ID, NAME, DIFFICULTY, DETAILS, LOCATION from MISSION where ID = ?");) {
                st.setLong(1, id);
                ResultSet missions = st.executeQuery();

                if (missions.next()) {
                    Mission mission = new Mission();
                    mission.setId(id);
                    mission.setName(missions.getString("NAME"));
                    mission.setDifficulty(missions.getInt("DIFFICULTY"));
                    mission.setDetails(missions.getString("DETAILS"));
                    mission.setLocation(missions.getString("LOCATION"));
                    if (missions.next()) {
                        throw new SQLException("There is more then one agent with same id");
                    }
                    return mission;
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                String msg = "Error when getting mission with id = " + id + " from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Mission> getAllMissions() {
        checkDataSource();

        try (Connection connection = dataSource.getConnection();) {
            try (PreparedStatement st = connection.prepareStatement("select ID, NAME, DIFFICULTY, DETAILS, LOCATION from MISSION");) {
                ResultSet missions = st.executeQuery();

                return getMissionsFromResultSet(missions);

            } catch (SQLException ex) {
                String msg = "Error when getting missions from database";
                logger.log(Level.SEVERE, msg, ex);
                throw new ServiceFailureException(msg, ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Mission> getMissionsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Mission> missions = new ArrayList<>();
        while (resultSet.next()) {
            Mission mission = new Mission();
            mission.setId(resultSet.getLong("ID"));
            mission.setName(resultSet.getString("NAME"));
            mission.setDifficulty(resultSet.getInt("DIFFICULTY"));
            mission.setDetails(resultSet.getString("DETAILS"));
            mission.setLocation(resultSet.getString("LOCATION"));

            missions.add(mission);
        }

        return missions;
    }
}
