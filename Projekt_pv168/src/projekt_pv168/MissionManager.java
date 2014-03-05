/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Collection;

/**
 *
 * @author Lenovo
 */
public interface MissionManager {
    
    public void createMission(Mission mission);
    
    public void updateMission(Mission mission);
    
    public void removeMission(Mission mission);
    
    public Mission getMission(long id);
    
    public Collection<Mission> findAllMissions();
}
