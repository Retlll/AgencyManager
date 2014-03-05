/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

/**
 *
 * @author Lenovo
 */
public interface AgentManager {
    public void createAgent(Agent agent);
    
    public void editAgent(Agent agent);
    
    public void removeAgent(Agent agent);
    
    //aj ine parametre
    public Agent getAgent(Long id);
}
