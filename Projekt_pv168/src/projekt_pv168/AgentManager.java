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
public interface AgentManager {
    public void createAgent(Agent agent);
    
    public void updateAgent(Agent agent);
    
    public void removeAgent(Agent agent);
    
    public boolean checkAgent(Agent agent);
    
    //aj ine parametre
    public Agent getAgent(long id) throws SQLException;
    
    //dajme radsej bestRank
    public List<Agent> getAgentWithRank(int minRank);
    
    public List<Agent> getAgentWithRank(int minRank, int maxRank);
    
    public List<Agent> getAllAgents();
}
