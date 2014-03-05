/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Collection;

/**
 *
 * @author Sebastian
 */
public interface ContractManager {
    
    public void createContract(Contract contract);
    
    public void updateContract(Contract contract);
    
    public void removeContract(Contract contract);
    
    public Collection<Contract> findAllContracts();
    
    public Collection<Mission> findAllMissionsForAgent(Agent agent);
    
    public Collection<Agent> findAllAgentsForMission(Mission mission);
    
}
