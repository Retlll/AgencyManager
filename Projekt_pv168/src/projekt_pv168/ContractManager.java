/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.List;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Sebastian
 */
public interface ContractManager {
    
    public void createContract(Contract contract) throws ServiceFailureException;
    
    public void updateContract(Contract contract) throws ServiceFailureException;
    
    public void removeContract(Contract contract) throws ServiceFailureException;
    
    public Contract getContract(Mission mission, Agent agent) throws ServiceFailureException;
    
    public Contract getContract(long missionID, long agentID) throws ServiceFailureException;
    
    public List<Contract> findAllContracts() throws ServiceFailureException;
    
    public List<Contract> findAllContracts(Agent agent) throws ServiceFailureException;
    
    public List<Contract> findAllContracts(Mission mission) throws ServiceFailureException;
    
    public List<Mission> findAllMissionsForAgent(Agent agent) throws ServiceFailureException;
    
    public List<Agent> findAllAgentsForMission(Mission mission) throws ServiceFailureException;
    
    public void removeAllContractsForAgent(Agent agent) throws ServiceFailureException;
    
    public void removeAllContractsForMission(Mission mission) throws ServiceFailureException;
    
    public ContractManager duplicate();
    
    
    
}
