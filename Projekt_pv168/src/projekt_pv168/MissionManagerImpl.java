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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class MissionManagerImpl implements MissionManager {
    
    private Connection connection;
    
    public MissionManagerImpl(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public void createMission(Mission mission) {
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
        
        try (PreparedStatement st = connection.prepareStatement("insert into MISSION (NAME, DIFFICULTY, DETAILS, LOCATION) "
                + "values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);) {
            st.setString(1, mission.getName());
            st.setInt(2, mission.getDifficulty());
            st.setString(3, mission.getDetails());
            st.setString(4, mission.getLocation());

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
                mission.setId(tempKey);
            } else {
                throw new SQLException("No row in key table");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgentManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateMission(Mission mission) {
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
        
        try (PreparedStatement st = connection.prepareStatement(
                "update MISSION set NAME = ?, DIFFICULTY = ?, DETAILS = ?, LOCATION = ? where id = ?");) {
            st.setString(1, mission.getName());
            st.setInt(2, mission.getDifficulty());
            st.setString(3, mission.getDetails());
            st.setString(4, mission.getLocation());
            st.setLong(5, mission.getId());
            st.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AgentManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeMission(Mission mission){
        if (mission.getId() == null) {
            throw new IllegalArgumentException("Id is null");
        }
        if (mission.getName() == null) {
            throw new IllegalArgumentException("Name is null");
        }
        
        try (PreparedStatement st = connection.prepareStatement(
                "delete from MISSION where ID = ?");) {
            st.setLong(1, mission.getId());
            st.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AgentManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Mission getMission(long id) throws SQLException {
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
            throw new SQLException("Error in creatign prepared statement");
        }
    }

    @Override
    public List<Mission> getAllMissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
