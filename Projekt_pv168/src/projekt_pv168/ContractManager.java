/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface ContractManager {
    
    public void createContract(Contract contract);
    
    public void updateContract(Contract contract);
    
    public void removeContract(Contract contract);
    
    public Contract getContract(Mission mission, Agent agent);
    
    public List<Contract> findAllContracts();
    
    public List<Mission> findAllMissionsForAgent(Agent agent);
    
    public List<Agent> findAllAgentsForMission(Mission mission);
    
}
