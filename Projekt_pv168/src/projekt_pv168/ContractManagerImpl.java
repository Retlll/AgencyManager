/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ContractManagerImpl implements ContractManager {

    private Connection connection;
    private AgentManager agManager;
    private MissionManager msManager;
    private static final Logger LOGGER = Logger.getLogger(ContractManagerImpl.class.getName());

    public ContractManagerImpl(Connection conn) {
        this.connection = conn;
        agManager = new AgentManagerImpl(connection);
        msManager = new MissionManagerImpl(connection);
    }

    public ContractManagerImpl(Connection conn, MissionManager msManager, AgentManager agManager) {
        this.connection = conn;
        this.agManager = agManager;
        this.msManager = msManager;
    }

    @Override
    public void createContract(Contract contract) {
        if (contract.getAgent() == null) {
            throw new IllegalArgumentException("contract's agent is null");
        }

        if (contract.getAgent().getId() == null) {
            throw new IllegalArgumentException("ID of contract's agent is null");
        }

        if (contract.getMission() == null) {
            throw new IllegalArgumentException("contract's mission is null");
        }

        if (contract.getMission().getId() == null) {
            throw new IllegalArgumentException("ID of contract's mission is null");
        }

        if (contract.getBudget() < 0l || contract.getBudget() > 1000000000000000000l) {
            throw new IllegalArgumentException("contract's abudget is out of range (0 - 1000000000000000000");
        }
        if (contract.getEndTime() != null && contract.getStartTime() != null) {
            if (contract.getEndTime().getTimeInMillis() < contract.getStartTime().getTimeInMillis()) {
                throw new IllegalArgumentException("contract's starTime is later than contract's endTime");
            }
        }

        if (getContract(contract.getMission(), contract.getAgent()) != null) {
            throw new IllegalArgumentException("contract with this agent and mission is already in database");
        }

        try (PreparedStatement st = connection.prepareStatement(
                "insert into CONTRACT ( MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME ) values (?, ?, ?, ?, ?)");) {
            st.setLong(3, contract.getBudget());
            if (contract.getStartTime() != null) {
                st.setDate(4, new java.sql.Date(contract.getStartTime().getTimeInMillis()));
            } else { 
                st.setNull(4, java.sql.Types.DATE);
            }
            if (contract.getEndTime() != null) {
                st.setDate(5, new java.sql.Date(contract.getEndTime().getTimeInMillis()));
            } else { 
                st.setNull(5, java.sql.Types.DATE);
            }
            st.setLong(1, contract.getMission().getId());
            st.setLong(2, contract.getAgent().getId());
            st.executeUpdate();
            if (LOGGER.isLoggable(Level.FINE)) {
                Contract tmp = getContract(contract.getMission(), contract.getAgent());
                LOGGER.log(Level.FINE, "Contract: " + tmp + " was added to the database to :");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateContract(Contract contract) {
        if (contract.getAgent() == null) {
            throw new IllegalArgumentException("contract's agent is null");
        }

        if (contract.getAgent().getId() == null) {
            throw new IllegalArgumentException("ID of contract's agent is null");
        }

        if (contract.getMission() == null) {
            throw new IllegalArgumentException("contract's mission is null");
        }

        if (contract.getMission().getId() == null) {
            throw new IllegalArgumentException("ID of contract's mission is null");
        }

        if (contract.getBudget() < 0l || contract.getBudget() > 1000000000000000000l) {
            throw new IllegalArgumentException("contract's abudget is out of range (0 - 1000000000000000000");
        }

        if (contract.getEndTime() != null && contract.getStartTime() != null) {
            if (contract.getEndTime().getTimeInMillis() < contract.getStartTime().getTimeInMillis()) {
                throw new IllegalArgumentException("contract's starTime is later than contract's endTime");
            }
        }

        if (getContract(contract.getMission(), contract.getAgent()) == null) {
            throw new IllegalArgumentException("contract with this agent and mission is not in database");
        }

        Contract tmp = null;
        if (LOGGER.isLoggable(Level.FINE)) {
            tmp = getContract(contract.getMission(), contract.getAgent());
            if (tmp == null) {
                return;
            }
        }

        try (PreparedStatement st = connection.prepareStatement(
                "update CONTRACT set BUDGET = ?, STARTTIME = ?, ENDTIME = ? where MISSIONID = ? and AGENTID = ?");) {
            st.setLong(1, contract.getBudget());
            if (contract.getStartTime() != null) {
                st.setDate(2, new java.sql.Date(contract.getStartTime().getTimeInMillis()));
            } else { 
                st.setNull(2, java.sql.Types.DATE);
            }
            if (contract.getEndTime() != null) {
                st.setDate(3, new java.sql.Date(contract.getEndTime().getTimeInMillis()));
            } else { 
                st.setNull(3, java.sql.Types.DATE);
            }
            st.setLong(4, contract.getMission().getId());
            st.setLong(5, contract.getAgent().getId());
            st.executeUpdate();
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Contract: " + tmp + " was updated inside the database to :" + contract);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeContract(Contract contract) {
        if (contract.getAgent() == null) {
            throw new IllegalArgumentException("contract's agent is null");
        }

        if (contract.getAgent().getId() == null) {
            throw new IllegalArgumentException("ID of contract's agent is null");
        }

        if (contract.getMission() == null) {
            throw new IllegalArgumentException("contract's mission is null");
        }

        if (contract.getMission().getId() == null) {
            throw new IllegalArgumentException("ID of contract's mission is null");
        }

        if (getContract(contract.getMission(), contract.getAgent()) == null) {
            throw new IllegalArgumentException("contract with this agent and mission is not in database");
        }

        Contract tmp = null;
        if (LOGGER.isLoggable(Level.FINE)) {
            tmp = getContract(contract.getMission(), contract.getAgent());
            if (tmp == null) {
                return;
            }
        }

        try (PreparedStatement st = connection.prepareStatement(
                "delete from CONTRACT where MISSIONID = ? and AGENTID = ?");) {
            st.setLong(1, contract.getMission().getId());
            st.setLong(2, contract.getAgent().getId());
            st.executeUpdate();
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Contract: " + tmp + " removed from the database");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public Contract getContract(Mission mission, Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("ID of agent is null");
        }

        if (mission == null) {
            throw new IllegalArgumentException("mission is null");
        }

        if (mission.getId() == null) {
            throw new IllegalArgumentException("ID of mission is null");
        }
        return getContract(mission.getId(), agent.getId());
    }

    @Override
    public Contract getContract(long missionID, long agentID) {
        try (PreparedStatement st = connection.prepareStatement(
                "select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where MISSIONID = ? and AGENTID = ?");) {
            st.setLong(1, missionID);
            st.setLong(2, agentID);
            ResultSet contractResult = st.executeQuery();

            if (contractResult.next()) {
                return createContractFromTableRow(contractResult);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Contract> findAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet contractResult = st.executeQuery("select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT");
            while (contractResult.next()) {
                contracts.add(createContractFromTableRow(contractResult));
            }
            return contracts;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Mission> findAllMissionsForAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("ID of agent is null");
        }

        List<Mission> missions = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(
                "select MISSIONID from CONTRACT where AGENTID = ?");) {
            st.setLong(1, agent.getId());
            ResultSet contractResult = st.executeQuery();

            while (contractResult.next()) {
                missions.add(msManager.getMission(contractResult.getInt("MISSIONID")));
            }
            return missions;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Agent> findAllAgentsForMission(Mission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("mission is null");
        }

        if (mission.getId() == null) {
            throw new IllegalArgumentException("ID of mission is null");
        }

        List<Agent> agents = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(
                "select AGENTID from CONTRACT where MISSIONID = ?");) {
            st.setLong(1, mission.getId());
            ResultSet contractResult = st.executeQuery();

            while (contractResult.next()) {
                agents.add(agManager.getAgent(contractResult.getInt("AGENTID")));
            }
            return agents;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Contract createContractFromTableRow(ResultSet contractResult) throws SQLException {
        Contract contract = new Contract();
        contract.setMission(msManager.getMission(contractResult.getLong("MISSIONID")));
        contract.setAgent(agManager.getAgent(contractResult.getLong("AGENTID")));
        contract.setBudget(contractResult.getLong("BUDGET"));

        Date date = contractResult.getDate("STARTTIME");
        if (date != null) {
            Calendar startTime = new GregorianCalendar();
            startTime.clear();
            startTime.setTimeInMillis(date.getTime());
            contract.setStartTime(startTime);
        }
        date = contractResult.getDate("ENDTIME");
        if (date != null) {
            Calendar endTime = new GregorianCalendar();
            endTime.clear();
            endTime.setTimeInMillis(date.getTime());
            contract.setEndTime(endTime);
        }
        return contract;
    }
}
