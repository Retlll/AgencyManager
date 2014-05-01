/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sql.DataSource;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Lenovo
 */
public class ContractManagerImpl implements ContractManager {

    private DataSource dataSource;
    private AgentManager agManager;
    private MissionManager msManager;
    private static final Logger logger = Logger.getLogger(ContractManagerImpl.class.getName());

    public ContractManagerImpl(DataSource dataSource, MissionManager msManager, AgentManager agManager) {
        this.dataSource = dataSource;
        this.agManager = agManager;
        this.msManager = msManager;
        loggerOutput();
    }

    public ContractManagerImpl() {
    }

    public ContractManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        agManager = new AgentManagerImpl(dataSource);
        msManager = new MissionManagerImpl(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        if (agManager == null) {
            agManager = new AgentManagerImpl(dataSource);
        }
        if (msManager == null) {
            msManager = new MissionManagerImpl(dataSource);
        }
    }

    @Override
    public ContractManager duplicate() {
        return new ContractManagerImpl(this.dataSource, this.msManager, this.agManager);
    }
    

    private void loggerOutput() {
        FileHandler fh = null;
        try {
            fh = new FileHandler("C:/log/MyLogFile.log");
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.ALL);
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    public void setAgManager(AgentManager agManager) {
        this.agManager = agManager;
    }

    public void setMsManager(MissionManager msManager) {
        this.msManager = msManager;
    }

    @Override
    public void createContract(Contract contract) throws ServiceFailureException {

        checkDataSource();
        validate(contract);

        /*
         if (getContract(contract.getMission(), contract.getAgent()) != null) {
         throw new IllegalArgumentException("contract with this agent and mission is already in database");
         }
         */

        Contract tmp = null;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "insert into CONTRACT ( MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME ) values (?, ?, ?, ?, ?)");) {
                connection.setAutoCommit(false);
                if (getContractWithConn(contract.getMission().getId(), contract.getAgent().getId(), connection) != null) {
                    throw new IllegalArgumentException("contract with this agent and mission is already in database");
                }
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
                int result = st.executeUpdate();
                if (result != 1) {
                    connection.rollback();
                    throw new ServiceFailureException("more/less (" + result + ") than one same contract was inserted in the database - rollback aplied");
                }
                if (logger.isLoggable(Level.FINE)) {
                    tmp = getContractWithConn(contract.getMission().getId(), contract.getAgent().getId(), connection);
                }
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Contract: {0} was added to the database", tmp);
        }
    }

    @Override
    public void updateContract(Contract contract) throws ServiceFailureException {
        checkDataSource();
        validate(contract);

        /*
         Contract tmp = getContract(contract.getMission(), contract.getAgent());
         if (tmp == null) {
         throw new IllegalArgumentException("contract with this agent and mission is not in the database");
         }
         */

        Contract tmp2;
        Contract tmp;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "update CONTRACT set BUDGET = ?, STARTTIME = ?, ENDTIME = ? where MISSIONID = ? and AGENTID = ?");) {
                connection.setAutoCommit(false);
                tmp = getContractWithConn(contract.getMission().getId(), contract.getAgent().getId(), connection);
                if (tmp == null) {
                    throw new IllegalArgumentException("contract with this agent and mission is not in the database");
                }
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
                int results = st.executeUpdate();
                if (results != 1) {
                    connection.rollback();
                    throw new ServiceFailureException("more/less (" + results + ") than one same contract was found in the database");
                }
                tmp2 = getContractWithConn(contract.getMission().getId(), contract.getAgent().getId(), connection);
                if (tmp2 == null) {
                    throw new IllegalArgumentException("contract was not in  the database (after update)");
                }
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Contract: {0} was updated inside the database to :{1}", new Object[]{tmp, tmp2});
        }
    }

    @Override
    public void removeContract(Contract contract) throws ServiceFailureException {
        checkDataSource();
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

        /*
         Contract tmp = getContract(contract.getMission(), contract.getAgent());
         if (tmp == null) {
         throw new IllegalArgumentException("contract with this agent and mission is not in the database");
         }
         */

        Contract tmp;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "delete from CONTRACT where MISSIONID = ? and AGENTID = ?");) {
                connection.setAutoCommit(true);
                tmp = getContractWithConn(contract.getMission().getId(), contract.getAgent().getId(), connection);
                if (tmp == null) {
                    throw new IllegalArgumentException("contract with this agent and mission is not in the database");
                }
                st.setLong(1, contract.getMission().getId());
                st.setLong(2, contract.getAgent().getId());
                int results = st.executeUpdate();
                if (results != 1) {
                    connection.rollback();
                    throw new ServiceFailureException("more/less (" + results + ")than one instance for given contract was found in the database");
                }
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Contract: {0} removed from the database", tmp);
        }
    }

    @Override
    public Contract getContract(Mission mission, Agent agent) throws ServiceFailureException {
        checkDataSource();
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
    public Contract getContract(long missionID, long agentID) throws ServiceFailureException {
        checkDataSource();
        try (Connection connection = dataSource.getConnection()) {
            return getContractWithConn(missionID, agentID, connection);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
    }

    @Override
    public List<Contract> findAllContracts() throws ServiceFailureException {
        checkDataSource();
        String sql = "select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT";
        return findContracts(sql, null);

    }

    @Override
    public List<Contract> findAllContracts(Agent agent) throws ServiceFailureException {
        checkDataSource();
        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("ID of agent is null");
        }

        String sql = "select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where AGENTID = ?";
        return findContracts(sql, agent.getId());

    }

    @Override
    public List<Contract> findAllContracts(Mission mission) throws ServiceFailureException {
        checkDataSource();
        if (mission == null) {
            throw new IllegalArgumentException("mission is null");
        }

        if (mission.getId() == null) {
            throw new IllegalArgumentException("ID of mission is null");
        }

        String sql = "select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where MISSIONID = ?";
        return findContracts(sql, mission.getId());

    }

    @Override
    public List<Mission> findAllMissionsForAgent(Agent agent) throws ServiceFailureException {
        checkDataSource();
        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("ID of agent is null");
        }

        List<Mission> missions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "select MISSIONID from CONTRACT where AGENTID = ?");) {
                st.setLong(1, agent.getId());
                ResultSet contractResult = st.executeQuery();

                while (contractResult.next()) {
                    missions.add(msManager.getMission(contractResult.getInt("MISSIONID")));
                }
                return missions;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
    }

    @Override
    public List<Agent> findAllAgentsForMission(Mission mission) throws ServiceFailureException {
        checkDataSource();
        if (mission == null) {
            throw new IllegalArgumentException("mission is null");
        }

        if (mission.getId() == null) {
            throw new IllegalArgumentException("ID of mission is null");
        }

        List<Agent> agents = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "select AGENTID from CONTRACT where MISSIONID = ?");) {
                st.setLong(1, mission.getId());
                ResultSet contractResult = st.executeQuery();

                while (contractResult.next()) {
                    agents.add(agManager.getAgent(contractResult.getInt("AGENTID")));
                }
                return agents;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
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

    private List<Contract> findContracts(String sql, Long id) throws ServiceFailureException {
        try (Connection connection = dataSource.getConnection()) {
            return findContracts(sql, id, connection);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }
    }

    private List<Contract> findContracts(String sql, Long id, Connection connection) throws ServiceFailureException {
        List<Contract> contracts = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            if (id != null) {
                st.setLong(1, id);
            }
            ResultSet contractResult = st.executeQuery();
            while (contractResult.next()) {
                contracts.add(createContractFromTableRow(contractResult));
            }
            return contracts;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
        }
    }

    @Override
    public void removeAllContractsForAgent(Agent agent) throws ServiceFailureException {
        checkDataSource();
        if (agent == null) {
            throw new IllegalArgumentException("agent is null");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("ID of agent is null");
        }

        List<Contract> contracts;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "delete from CONTRACT where AGENTID = ?");) {
                connection.setAutoCommit(true);
                contracts = findContracts("select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where AGENTID = ?", agent.getId(), connection);
                if (contracts.isEmpty()) {
                    throw new IllegalArgumentException("no contracts for given agent were present in the database");
                }
                st.setLong(1, agent.getId());
                int results = st.executeUpdate();
                /*
                 if (results != contracts.size()) {
                 connection.rollback();
                 throw new ServiceFailureException("more/less (" + results + ")than " + contracts.size()
                 + " instances for given agent was found in the database");
                 }
                 */

            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Contracts: {0} removed from the database", contracts);
        }
    }

    @Override
    public void removeAllContractsForMission(Mission mission) throws ServiceFailureException {
        checkDataSource();
        if (mission == null) {
            throw new IllegalArgumentException("mission is null");
        }

        if (mission.getId() == null) {
            throw new IllegalArgumentException("ID of mission is null");
        }

        List<Contract> contracts;

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(
                    "delete from CONTRACT where MISSIONID = ?");) {
                connection.setAutoCommit(true);
                contracts = findContracts("select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where MISSIONID = ?", mission.getId(), connection);
                if (contracts.isEmpty()) {
                    throw new IllegalArgumentException("no contracts for given mission were present in the database");
                }
                st.setLong(1, mission.getId());

                int results = st.executeUpdate();
                /*
                 if (results != contracts.size()) {
                 connection.rollback();
                 throw new ServiceFailureException("more/less (" + results + ")than " + contracts.size()
                 + " instances for given mission was found in the database");
                 }
                 */

            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }

        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Contracts: {0} removed from the database", contracts);
        }
    }

    private void validate(Contract contract) throws IllegalArgumentException {
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

        try {
            if (msManager.getMission(contract.getMission().getId()) == null) {
                throw new IllegalArgumentException("no mission with given id was found by msManager");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error of msManager", ex);
        }

        try {
            if (agManager.getAgent(contract.getAgent().getId()) == null) {
                throw new IllegalArgumentException("no mission with given id was found by agManager");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error of agManager", ex);
        }
    }
    // Zbytocne zlozite - ak su tranzakcie uzavrete.
/*
     @Override
     public void removeAllMissionsForAgent(Agent agent) throws ServiceFailureException {
     if (agent == null) {
     throw new IllegalArgumentException("agent is null");
     }

     if (agent.getId() == null) {
     throw new IllegalArgumentException("ID of agent is null");
     }
     try (Connection connection = dataSource.getConnection()) {
     try (PreparedStatement st = connection.prepareStatement(
     "delete from CONTRACT where AGENTID = ? and MISSIONID = ?");) {
     connection.setAutoCommit(true);
     List<Contract> contracts = findContracts("select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where AGENTID = ?", agent.getId(),connection);
     if (contracts.isEmpty()) {
     throw new IllegalArgumentException("no contracts for given agent were present in the database");
     }
     st.setLong(1, agent.getId());
     for (int i = 0; i < contracts.size(); i++) {
     st.setLong(2, contracts.get(i).getMission().getId());
     int results = st.executeUpdate();
     if (results != 1) {
     connection.rollback();
     throw new ServiceFailureException("more/less (" + results + ")than one instance for given contract ( msID ="
     + contracts.get(i).getMission().getId() + ", agID=" + agent.getId() + " was found in the database");
     }
     }
     if (logger.isLoggable(Level.FINE)) {
     logger.log(Level.FINE, "Contracts: {0} removed from the database", contracts);
     }
     } catch (SQLException ex) {
     connection.rollback();
     logger.log(Level.SEVERE, null, ex);
     throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
     } finally {
     connection.setAutoCommit(true);
     }
     } catch (SQLException ex) {
     logger.log(Level.SEVERE, null, ex);
     throw new ServiceFailureException("Internal error with databese - DataSource", ex);
     }
     }

     @Override
     if (mission == null) {
     throw new IllegalArgumentException("mission is null");
     }

     if (mission.getId() == null) {
     throw new IllegalArgumentException("ID of mission is null");
     }
     
     public void removeAllAgentsForMission(Mission mission) throws ServiceFailureException {
     try (Connection connection = dataSource.getConnection()) {
     try (PreparedStatement st = connection.prepareStatement(
     "delete from CONTRACT where AGENTID = ? and MISSIONID = ?");) {
     connection.setAutoCommit(true);
     List<Contract> contracts = findContracts("select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where MISSIONID = ?", mission.getId(),connection);
     if (contracts.isEmpty()) {
     throw new IllegalArgumentException("no contracts for given mission were present in the database");
     }
     st.setLong(1, mission.getId());
     for (int i = 0; i < contracts.size(); i++) {
     st.setLong(2, contracts.get(i).getAgent().getId());
     int results = st.executeUpdate();
     if (results != 1) {
     connection.rollback();
     throw new ServiceFailureException("more/less (" + results + ")than one instance for given contract ( agID ="
     + contracts.get(i).getAgent().getId() + ", msID=" + mission.getId() + " was found in the database");
     }
     }
     if (logger.isLoggable(Level.FINE)) {
     logger.log(Level.FINE, "Contracts: {0} removed from the database", contracts);
     }
     } catch (SQLException ex) {
     connection.rollback();
     logger.log(Level.SEVERE, null, ex);
     throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
     } finally {
     connection.setAutoCommit(true);
     }
     } catch (SQLException ex) {
     logger.log(Level.SEVERE, null, ex);
     throw new ServiceFailureException("Internal error with databese - DataSource", ex);
     }
     }
     */

    private Contract getContractWithConn(long missionID, long agentID, Connection connection) throws ServiceFailureException {
        try (PreparedStatement st = connection.prepareStatement(
                "select MISSIONID, AGENTID, BUDGET, STARTTIME, ENDTIME from CONTRACT where MISSIONID = ? and AGENTID = ?");) {
            st.setLong(1, missionID);
            st.setLong(2, agentID);
            Contract contract;
            ResultSet contractResult = st.executeQuery();
            if (contractResult.next()) {
                contract = createContractFromTableRow(contractResult);
                if (contractResult.next()) {
                    throw new ServiceFailureException("more contracts were found for given mission and agent");
                }
                return contract;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
        }
    }
}
