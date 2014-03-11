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
public interface AgentManager {
    public void createAgent(Agent agent);
    
    public void updateAgent(Agent agent);
    
    public void removeAgent(Agent agent);
    
    public boolean checkAgent(Agent agent);
    
    //aj ine parametre
    public Agent getAgent(long id);
    
    //dajme radsej bestRank
    public Collection<Agent> getAgentWithRank(int minRank);
    
    public Collection<Agent> getAgentWithRank(int minRank, int maxRank);
    
    public Collection<Agent> getAllAgents();
}
