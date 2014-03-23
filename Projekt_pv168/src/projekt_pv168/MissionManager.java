/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface MissionManager {
    
    public void createMission(Mission mission);
    
    public void updateMission(Mission mission);
    
    public void removeMission(Mission mission);
    
    public Mission getMission(long id) throws SQLException ;
    
    public List<Mission> getAllMissions();
}
