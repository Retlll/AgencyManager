/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ContractManagerImpl implements ContractManager{
    
    public ContractManagerImpl(Connection conn) {
        this.connection = conn;
    }
    private Connection connection;

    @Override
    public void createContract(Contract contract) {
        if (contract.getAgent() == null)
            throw new IllegalArgumentException("contract's agent is null");
        
        if (contract.getAgent().getId() == null)
            throw new IllegalArgumentException("ID of contract's agent is null");
        
        if (contract.getMission() == null)
            throw new IllegalArgumentException("contract's mission is null");
        
        if (contract.getMission().getId() == null)
            throw new IllegalArgumentException("ID of contract's mission is null");
        
        if (contract.getBudget() < 0l || contract.getBudget() > 1000000000000000000l)
            throw new IllegalArgumentException("contract's abudget is out of range (0 - 1000000000000000000");
        
        if (contract.getEndTime().getTimeInMillis() <= contract.getStartTime().getTimeInMillis())
            throw new IllegalArgumentException("contract's starTime is later than contract's endTime");
        
        if (getContract(contract.getMission(), contract.getAgent()) != null) {
            throw new IllegalArgumentException("contract with this agent and mission is already in database");
        }
        
        //dorob databazu
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateContract(Contract contract) {
        if (contract.getAgent() == null)
            throw new IllegalArgumentException("contract's agent is null");
        
        if (contract.getAgent().getId() == null)
            throw new IllegalArgumentException("ID of contract's agent is null");
        
        if (contract.getMission() == null)
            throw new IllegalArgumentException("contract's mission is null");
        
        if (contract.getMission().getId() == null)
            throw new IllegalArgumentException("ID of contract's mission is null");
        
        if (contract.getBudget() < 0l || contract.getBudget() > 1000000000000000000l)
            throw new IllegalArgumentException("contract's abudget is out of range (0 - 1000000000000000000");
        
        if (contract.getEndTime().getTimeInMillis() <= contract.getStartTime().getTimeInMillis())
            throw new IllegalArgumentException("contract's starTime is later than contract's endTime");
        
        if (getContract(contract.getMission(), contract.getAgent()) == null) {
            throw new IllegalArgumentException("contract with this agent and mission is not in database");
        }
        
        //dorob databazu
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeContract(Contract contract) {
        if (contract.getAgent() == null)
            throw new IllegalArgumentException("contract's agent is null");
        
        if (contract.getAgent().getId() == null)
            throw new IllegalArgumentException("ID of contract's agent is null");
        
        if (contract.getMission() == null)
            throw new IllegalArgumentException("contract's mission is null");
        
        if (contract.getMission().getId() == null)
            throw new IllegalArgumentException("ID of contract's mission is null");
        
        //dorob databazu
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Contract getContract(Mission mission, Agent agent) {
        if (agent == null)
            throw new IllegalArgumentException("agent is null");
        
        if (agent.getId() == null)
            throw new IllegalArgumentException("ID of agent is null");
        
        if (mission == null)
            throw new IllegalArgumentException("mission is null");
        
        if (mission.getId() == null)
            throw new IllegalArgumentException("ID of mission is null");
        return getContract(mission.getId(), agent.getId());
    }
    
    @Override
    public Contract getContract(long missionID, long agentID) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contract> findAllContracts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Mission> findAllMissionsForAgent(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Agent> findAllAgentsForMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
