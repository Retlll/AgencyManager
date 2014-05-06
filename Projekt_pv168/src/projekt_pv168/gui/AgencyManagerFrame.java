/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.commons.dbcp.BasicDataSource;
import projekt_pv168.Agent;
import projekt_pv168.AgentManagerImpl;
import projekt_pv168.Comparators;
import projekt_pv168.Contract;
import projekt_pv168.ContractManagerImpl;
import projekt_pv168.DatabaseManager;
import projekt_pv168.Mission;
import projekt_pv168.MissionManagerImpl;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Lenovo
 */
public class AgencyManagerFrame extends javax.swing.JFrame {

    private DataSource dataSource;
    private static List<Agent> agents = new ArrayList<>();
    private static List<Mission> missions = new ArrayList<>();
    private static List<Contract> contracts = new ArrayList<>();
    private boolean started;
    private boolean connected;
    private static boolean[] workerDone;
    private int[] lastSort = new int[]{0, 0, 0};
    private MissionManagerImpl missionManager;
    private AgentManagerImpl agentManager;
    private ContractManagerImpl contractManager;
    private DatabaseManager databaseManager;
    private Properties config;
    private static final Logger logger = Logger.getLogger(ContractManagerImpl.class.getName());
    private static final String SQL_AGENT = "AGENT (\n"
            + "    id bigint not null generated  always as identity,\n"
            + "    name varchar(50),\n"
            + "    born date,\n"
            + "    active boolean,\n"
            + "    rank int,\n"
            + "    notes varchar(50),\n"
            + "    primary key (id)\n"
            + ")";
    private static final String SQL_MISSION = "MISSION (\n"
            + "    id bigint not null generated  always as identity,\n"
            + "    name varchar(50),\n"
            + "    difficulty int,\n"
            + "    details varchar(50),\n"
            + "    location varchar(50),\n"
            + "    primary key (id)\n"
            + ")";
    private static final String SQL_CONTRACT = "CONTRACT (\n"
            + "    missionID bigint,\n"
            + "    agentID bigint,\n"
            + "    budget bigint,\n"
            + "    startTime date,\n"
            + "    endTime date,\n"
            + "    primary key (missionID, AgentID),\n"
            + "    foreign key (missionID) references Mission(id),\n"
            + "    foreign key (agentID) references Agent(id)\n"
            + ")";

    /**
     * Creates new form AgencyManagerFrame
     */
    public AgencyManagerFrame() {
        initComponents();
        started = false;

        contextMenu();
        initProperties();
        enableAll(false);
        statLabel.setText(" ");
        try {
            loggerOutput();
        } catch (IOException ex) {
            loggerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            loggerLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOG_ERROR"));
            //JOptionPane.showMessageDialog(null,"Cannot create log file in given directory", "logger error", JOptionPane.ERROR);
        }
        //connectToDataSource();
        JTableHeader agentHeader = agentTable.getTableHeader();
        agentHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = agentTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                String columnName = agentTable.getColumnName(column);
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("NAME"))) {
                    if (lastSort[0] != 0) {
                        Collections.sort(agents, new Comparators.AgentComparatorByName());
                        lastSort[0] = 0;
                    } else {
                        Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByName()));
                        lastSort[0] = -1;
                    }
                    tableModel = (DefaultTableModel) agentTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("BORN"))) {
                    if (lastSort[0] != 1) {
                        Collections.sort(agents, new Comparators.AgentComparatorByBorn());
                        lastSort[0] = 1;
                    } else {
                        Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByBorn()));
                        lastSort[0] = -1;
                    }
                    tableModel = (DefaultTableModel) agentTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ACTIVE"))) {
                    if (lastSort[0] != 2) {
                        Collections.sort(agents, new Comparators.AgentComparatorByActive());
                        lastSort[0] = 2;
                    } else {
                        Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByActive()));
                        lastSort[0] = -1;
                    }
                    tableModel = (DefaultTableModel) agentTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("RANK"))) {
                    if (lastSort[0] != 3) {
                        Collections.sort(agents, new Comparators.AgentComparatorByRank());
                        lastSort[0] = 3;
                    } else {
                        Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByRank()));
                        lastSort[0] = -1;
                    }
                    tableModel = (DefaultTableModel) agentTable.getModel();
                    tableModel.fireTableDataChanged();
                }
            }
        });

        JTableHeader missionHeader = missionTable.getTableHeader();
        missionHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = missionTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                String columnName = missionTable.getColumnName(column);
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("NAME"))) {
                    if (lastSort[1] != 0) {
                        Collections.sort(missions, new Comparators.MissionComparatorByName());
                        lastSort[1] = 0;
                    } else {
                        Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByName()));
                        lastSort[1] = -1;
                    }
                    tableModel = (DefaultTableModel) missionTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("DIFFICULTY"))) {
                    if (lastSort[1] != 1) {
                        Collections.sort(missions, new Comparators.MissionComparatorByDifficulty());
                        lastSort[1] = 1;
                    } else {
                        Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByDifficulty()));
                        lastSort[1] = -1;
                    }
                    tableModel = (DefaultTableModel) missionTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOCATION"))) {
                    if (lastSort[1] != 2) {
                        Collections.sort(missions, new Comparators.MissionComparatorByLocation());
                        lastSort[1] = 2;
                    } else {
                        Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByLocation()));
                        lastSort[1] = -1;
                    }
                    tableModel = (DefaultTableModel) missionTable.getModel();
                    tableModel.fireTableDataChanged();
                }
            }
        });

        JTableHeader contractHeader = contractTable.getTableHeader();
        contractHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = contractTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                String columnName = contractTable.getColumnName(column);
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGENT"))) {
                    if (lastSort[2] != 0) {
                        Collections.sort(contracts, new Comparators.ContractComparatorByAgent());
                        lastSort[2] = 0;
                    } else {
                        Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByAgent()));
                        lastSort[2] = -1;
                    }
                    tableModel = (DefaultTableModel) contractTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("MISSION"))) {
                    if (lastSort[2] != 1) {
                        Collections.sort(contracts, new Comparators.ContractComparatorByMission());
                        lastSort[2] = 1;
                    } else {
                        Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByMission()));
                        lastSort[2] = -1;
                    }
                    tableModel = (DefaultTableModel) contractTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("BUDGET"))) {
                    if (lastSort[2] != 2) {
                        Collections.sort(contracts, new Comparators.ContractComparatorByBudget());
                        lastSort[2] = 2;
                    } else {
                        Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByBudget()));
                        lastSort[2] = -1;
                    }
                    tableModel = (DefaultTableModel) contractTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_TIME"))) {
                    if (lastSort[2] != 3) {
                        Collections.sort(contracts, new Comparators.ContractComparatorByStartTime());
                        lastSort[2] = 3;
                    } else {
                        Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByStartTime()));
                        lastSort[2] = -1;
                    }
                    tableModel = (DefaultTableModel) contractTable.getModel();
                    tableModel.fireTableDataChanged();
                }
                if (columnName.equals(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("END_TIME"))) {
                    if (lastSort[2] != 4) {
                        Collections.sort(contracts, new Comparators.ContractComparatorByEndTime());
                        lastSort[2] = 4;
                    } else {
                        Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByEndTime()));
                        lastSort[2] = -1;
                    }
                    tableModel = (DefaultTableModel) contractTable.getModel();
                    tableModel.fireTableDataChanged();
                }
            }
        });
    }

    public void viewDialog(Agent agent) {
        ContractAddSwingWorker contractWorker = new ContractAddSwingWorker(this, agent);
        contractWorker.execute();
    }

    public void viewDialog(Mission mission) {
        ContractAddSwingWorker contractWorker = new ContractAddSwingWorker(this, mission);
        contractWorker.execute();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        agencyTablesTabbedPane = new javax.swing.JTabbedPane();
        agentPanel = new javax.swing.JPanel();
        agentScrollPane = new javax.swing.JScrollPane();
        agentTable = new javax.swing.JTable();
        addAgentButton = new javax.swing.JButton();
        updateAgentButton = new javax.swing.JButton();
        removeAgentButton = new javax.swing.JButton();
        viewAgentButton = new javax.swing.JButton();
        contractAgentButton = new javax.swing.JButton();
        missionPanel = new javax.swing.JPanel();
        missionScrollPane = new javax.swing.JScrollPane();
        missionTable = new javax.swing.JTable();
        addMissionButton = new javax.swing.JButton();
        updateMissionButton = new javax.swing.JButton();
        removeMissionButton = new javax.swing.JButton();
        viewMissionButton = new javax.swing.JButton();
        contractMissionButton = new javax.swing.JButton();
        contractPanel = new javax.swing.JPanel();
        contractScrollPane = new javax.swing.JScrollPane();
        contractTable = new javax.swing.JTable();
        addContractButton = new javax.swing.JButton();
        removeContractButton = new javax.swing.JButton();
        updateContractButton1 = new javax.swing.JButton();
        viewContractButton = new javax.swing.JButton();
        statPanel = new javax.swing.JPanel();
        statLabel = new javax.swing.JLabel();
        loggerLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        startSessionMenuItem = new javax.swing.JMenuItem();
        menuSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        refreshMenuItem = new javax.swing.JMenuItem();
        properitiesMenuItem = new javax.swing.JMenuItem();
        tablesMenu = new javax.swing.JMenu();
        clearMenu = new javax.swing.JMenu();
        clearAgentMenuItem = new javax.swing.JMenuItem();
        clearMissionMenuItem = new javax.swing.JMenuItem();
        clearContractMenuItem = new javax.swing.JMenuItem();
        clearAllMenuItem = new javax.swing.JMenuItem();
        rebuildMenu = new javax.swing.JMenu();
        rebuildAgentMenuItem = new javax.swing.JMenuItem();
        rebuildMissionMenuItem = new javax.swing.JMenuItem();
        rebuildContractMenuItem = new javax.swing.JMenuItem();
        rebuildAllMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default"); // NOI18N
        setTitle(bundle.getString("AGENCY_MANAGER")); // NOI18N
        setMinimumSize(new java.awt.Dimension(400, 330));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        agencyTablesTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        agentPanel.setOpaque(false);

        agentScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agentTable.setModel(new agentTableModel());
        agentTable.setFillsViewportHeight(true);
        agentTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        agentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agentTableMouseClicked(evt);
            }
        });
        agentScrollPane.setViewportView(agentTable);

        addAgentButton.setText(bundle.getString("ADD")); // NOI18N
        addAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgentButtonActionPerformed(evt);
            }
        });

        updateAgentButton.setText(bundle.getString("UPDATE")); // NOI18N
        updateAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAgentButtonActionPerformed(evt);
            }
        });

        removeAgentButton.setText(bundle.getString("REMOVE")); // NOI18N
        removeAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButtonActionPerformed(evt);
            }
        });

        viewAgentButton.setText(bundle.getString("VIEW")); // NOI18N
        viewAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAgentButtonActionPerformed(evt);
            }
        });

        contractAgentButton.setText(bundle.getString("CONTRACTS")); // NOI18N
        contractAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractAgentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout agentPanelLayout = new javax.swing.GroupLayout(agentPanel);
        agentPanel.setLayout(agentPanelLayout);
        agentPanelLayout.setHorizontalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addAgentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateAgentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeAgentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewAgentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contractAgentButton)
                .addContainerGap(163, Short.MAX_VALUE))
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(agentScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );
        agentPanelLayout.setVerticalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, agentPanelLayout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAgentButton)
                    .addComponent(removeAgentButton)
                    .addComponent(updateAgentButton)
                    .addComponent(viewAgentButton)
                    .addComponent(contractAgentButton))
                .addContainerGap())
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(agentPanelLayout.createSequentialGroup()
                    .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab(bundle.getString("AGENT"), agentPanel); // NOI18N

        missionPanel.setOpaque(false);

        missionScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionTable.setModel(new missionTableModel());
        missionTable.setFillsViewportHeight(true);
        missionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        missionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                missionTableMouseClicked(evt);
            }
        });
        missionScrollPane.setViewportView(missionTable);

        addMissionButton.setText(bundle.getString("ADD")); // NOI18N
        addMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMissionButtonActionPerformed(evt);
            }
        });

        updateMissionButton.setText(bundle.getString("UPDATE")); // NOI18N
        updateMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateMissionButtonActionPerformed(evt);
            }
        });

        removeMissionButton.setText(bundle.getString("REMOVE")); // NOI18N
        removeMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMissionButtonActionPerformed(evt);
            }
        });

        viewMissionButton.setText(bundle.getString("VIEW")); // NOI18N
        viewMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewMissionButtonActionPerformed(evt);
            }
        });

        contractMissionButton.setText(bundle.getString("CONTRACTS")); // NOI18N
        contractMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractMissionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout missionPanelLayout = new javax.swing.GroupLayout(missionPanel);
        missionPanel.setLayout(missionPanelLayout);
        missionPanelLayout.setHorizontalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addMissionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateMissionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeMissionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewMissionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contractMissionButton)
                .addContainerGap(163, Short.MAX_VALUE))
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(missionScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );
        missionPanelLayout.setVerticalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionPanelLayout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMissionButton)
                    .addComponent(removeMissionButton)
                    .addComponent(updateMissionButton)
                    .addComponent(viewMissionButton)
                    .addComponent(contractMissionButton))
                .addContainerGap())
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(missionPanelLayout.createSequentialGroup()
                    .addComponent(missionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab(bundle.getString("MISSION"), missionPanel); // NOI18N

        contractPanel.setOpaque(false);

        contractScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        contractTable.setModel(new contractTableModel());
        contractTable.setFillsViewportHeight(true);
        contractTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contractTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contractTableMouseClicked(evt);
            }
        });
        contractScrollPane.setViewportView(contractTable);

        addContractButton.setText(bundle.getString("ADD")); // NOI18N
        addContractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContractButtonActionPerformed(evt);
            }
        });

        removeContractButton.setText(bundle.getString("REMOVE")); // NOI18N
        removeContractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeContractButtonActionPerformed(evt);
            }
        });

        updateContractButton1.setText(bundle.getString("UPDATE")); // NOI18N
        updateContractButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateContractButton1ActionPerformed(evt);
            }
        });

        viewContractButton.setText(bundle.getString("VIEW")); // NOI18N
        viewContractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewContractButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contractPanelLayout = new javax.swing.GroupLayout(contractPanel);
        contractPanel.setLayout(contractPanelLayout);
        contractPanelLayout.setHorizontalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addContractButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateContractButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeContractButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewContractButton)
                .addContainerGap(257, Short.MAX_VALUE))
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );
        contractPanelLayout.setVerticalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contractPanelLayout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addContractButton)
                    .addComponent(removeContractButton)
                    .addComponent(updateContractButton1)
                    .addComponent(viewContractButton))
                .addContainerGap())
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contractPanelLayout.createSequentialGroup()
                    .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab(bundle.getString("CONTRACT"), contractPanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agencyTablesTabbedPane)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agencyTablesTabbedPane))
        );

        statPanel.setBackground(new java.awt.Color(204, 204, 204));
        statPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        statLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statLabel.setForeground(new java.awt.Color(51, 0, 0));
        statLabel.setText(" ");
        statLabel.setToolTipText("");

        loggerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loggerLabel.setForeground(new java.awt.Color(51, 0, 0));
        loggerLabel.setToolTipText("");
        loggerLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout statPanelLayout = new javax.swing.GroupLayout(statPanel);
        statPanel.setLayout(statPanelLayout);
        statPanelLayout.setHorizontalGroup(
            statPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(statPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statPanelLayout.createSequentialGroup()
                    .addContainerGap(250, Short.MAX_VALUE)
                    .addComponent(loggerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        statPanelLayout.setVerticalGroup(
            statPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statPanelLayout.createSequentialGroup()
                .addComponent(statLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(statPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(statPanelLayout.createSequentialGroup()
                    .addComponent(loggerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        fileMenu.setText(bundle.getString("FILE")); // NOI18N

        startSessionMenuItem.setText(bundle.getString("START_SESSION")); // NOI18N
        startSessionMenuItem.setToolTipText("");
        startSessionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSessionMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(startSessionMenuItem);
        fileMenu.add(menuSeparator1);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        exitMenuItem.setText(bundle.getString("EXIT")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(bundle.getString("EDIT")); // NOI18N

        refreshMenuItem.setText(bundle.getString("REFRESH")); // NOI18N
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(refreshMenuItem);

        properitiesMenuItem.setText(bundle.getString("PROPERTIES")); // NOI18N
        properitiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                properitiesMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(properitiesMenuItem);

        menuBar.add(editMenu);

        tablesMenu.setText(bundle.getString("TABLES")); // NOI18N

        clearMenu.setText(bundle.getString("CLEAR")); // NOI18N

        clearAgentMenuItem.setText(bundle.getString("AGENTS")); // NOI18N
        clearAgentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAgentMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(clearAgentMenuItem);

        clearMissionMenuItem.setText(bundle.getString("MISSIONS")); // NOI18N
        clearMissionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMissionMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(clearMissionMenuItem);

        clearContractMenuItem.setText(bundle.getString("CONTRACTS")); // NOI18N
        clearContractMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearContractMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(clearContractMenuItem);

        clearAllMenuItem.setText(bundle.getString("ALL")); // NOI18N
        clearAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(clearAllMenuItem);

        tablesMenu.add(clearMenu);

        rebuildMenu.setText(bundle.getString("REBUILD")); // NOI18N

        rebuildAgentMenuItem.setText(bundle.getString("AGENTS")); // NOI18N
        rebuildAgentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildAgentMenuItemActionPerformed(evt);
            }
        });
        rebuildMenu.add(rebuildAgentMenuItem);

        rebuildMissionMenuItem.setText(bundle.getString("MISSIONS")); // NOI18N
        rebuildMissionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildMissionMenuItemActionPerformed(evt);
            }
        });
        rebuildMenu.add(rebuildMissionMenuItem);

        rebuildContractMenuItem.setText(bundle.getString("CONTRACTS")); // NOI18N
        rebuildContractMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildContractMenuItemActionPerformed(evt);
            }
        });
        rebuildMenu.add(rebuildContractMenuItem);

        rebuildAllMenuItem.setText(bundle.getString("ALL")); // NOI18N
        rebuildAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildAllMenuItemActionPerformed(evt);
            }
        });
        rebuildMenu.add(rebuildAllMenuItem);

        tablesMenu.add(rebuildMenu);

        menuBar.add(tablesMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initProperties() {
        config = new Properties();

        try {
            FileInputStream fileInput = new FileInputStream("Configuration.properties");
            config.load(fileInput);
            if (!config.containsKey("SERVER_URL")) {
                config.put("SERVER_URL", "");
            }
            if (!config.containsKey("SERVER_NAME")) {
                config.put("SERVER_NAME", "");
            }
            if (!config.containsKey("SERVER_PASSWORD")) {
                config.put("SERVER_PASSWORD", "");
            }
            if (!config.containsKey("DOUBLE_CLICK")) {
                config.put("DOUBLE_CLICK", "0");
            }
        } catch (FileNotFoundException ex) {
            FileOutputStream fileOutput = null;
            try {
                fileOutput = new FileOutputStream("Configuration.properties");
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex1);
            }
            config.put("SERVER_URL", "");
            config.put("SERVER_NAME", "");
            config.put("SERVER_PASSWORD", "");
            config.put("DOUBLE_CLICK", "0");

            System.err.append(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CONFIG_MISSING"));
            //Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveProperities() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Configuration.properties");
            config.store(fileOut, null);
        } catch (FileNotFoundException ex1) {
            System.out.println(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CONFIG_ERROR"));
            Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex) {
            System.out.println(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CONFIG_EDIT_ERROR"));
            Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToDataSource() {
        BasicDataSource ds = new BasicDataSource();

        ds.setUrl(config.getProperty("SERVER_URL", "").toString());
        ds.setUsername(config.getProperty("SERVER_NAME", "").toString());
        ds.setPassword(config.getProperty("SERVER_PASSWORD", "").toString());

        DataSource dataSource = ds;
        try {
            ds.getConnection();
            connected = true;
        } catch (SQLException ex) {
            connected = false;
            logger.log(Level.SEVERE, null, ex);
            return;
        }

        missionManager = new MissionManagerImpl(dataSource);
        agentManager = new AgentManagerImpl(dataSource);
        contractManager = new ContractManagerImpl(dataSource, missionManager, agentManager);
        databaseManager = new DatabaseManager(dataSource);

        try {
            refreshLists();
        } catch (ServiceFailureException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void contextMenu() {
        final JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem update = new JMenuItem(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UPDATE"));
        update.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (agencyTablesTabbedPane.getSelectedIndex() == 0 && agentTable.getSelectedRowCount() != 0) {
                    updateAgentButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 1 && missionTable.getSelectedRowCount() != 0) {
                    updateMissionButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 2 && contractTable.getSelectedRowCount() != 0) {
                    updateContractButton1ActionPerformed(null);
                }
            }
        });
        contextMenu.add(update);

        JMenuItem view = new JMenuItem(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VIEW"));
        view.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (agencyTablesTabbedPane.getSelectedIndex() == 0 && agentTable.getSelectedRowCount() != 0) {
                    viewAgentButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 1 && missionTable.getSelectedRowCount() != 0) {
                    viewMissionButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 2 && contractTable.getSelectedRowCount() != 0) {
                    viewContractButtonActionPerformed(null);
                }
            }
        });
        contextMenu.add(view);

        JMenuItem remove = new JMenuItem(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REMOVE"));
        remove.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (agencyTablesTabbedPane.getSelectedIndex() == 0 && agentTable.getSelectedRowCount() != 0) {
                    removeAgentButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 1 && missionTable.getSelectedRowCount() != 0) {
                    removeMissionButtonActionPerformed(null);
                }
                if (agencyTablesTabbedPane.getSelectedIndex() == 2 && contractTable.getSelectedRowCount() != 0) {
                    removeContractButtonActionPerformed(null);
                }
            }
        });
        contextMenu.add(remove);

        agentTable.setComponentPopupMenu(contextMenu);
        missionTable.setComponentPopupMenu(contextMenu);
        contractTable.setComponentPopupMenu(contextMenu);
    }

    private void addAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAgentButtonActionPerformed
        if (connected) {
            EditAgentDialog dialog = new EditAgentDialog(this, true);
            dialog.setVisible(true);
            if (dialog.getAgent() != null) {
                try {
                    agentManager.createAgent(dialog.getAgent());
                    refreshLists();
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
            dialog.dispose();
        } else {
            if (!started) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_SESSION_FIRST"));
            } else {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
            }
        }
    }//GEN-LAST:event_addAgentButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveProperities();
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void viewAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAgentButtonActionPerformed
        if (agentTable.getSelectedRow() != -1) {
            ViewAgentDialog dialog = new ViewAgentDialog(this, false, agents.get(agentTable.getSelectedRow()));
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewAgentButtonActionPerformed

    private void updateAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAgentButtonActionPerformed
        if (agentTable.getSelectedRow() != -1) {
            EditAgentDialog dialog = new EditAgentDialog(this, true, agents.get(agentTable.getSelectedRow()));
            dialog.setVisible(true);
            if (dialog.getAgent() != null) {
                try {
                    agentManager.updateAgent(dialog.getAgent());
                    refreshLists();
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
            dialog.dispose();
        }
    }//GEN-LAST:event_updateAgentButtonActionPerformed

    private void removeAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButtonActionPerformed
        if (agentTable.getSelectedRow() != -1) {
            try {
                if (contractManager.findAllContracts(agents.get(agentTable.getSelectedRow())).isEmpty()) {
                    agentManager.removeAgent(agents.get(agentTable.getSelectedRow()));
                    agents.remove(agentTable.getSelectedRow());
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGENT_DESTROY_CONTACTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("WARNING"), JOptionPane.YES_NO_CANCEL_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        contractManager.removeAllContractsForAgent(agents.get(agentTable.getSelectedRow()));
                        agentManager.removeAgent(agents.get(agentTable.getSelectedRow()));
                        agents.remove(agentTable.getSelectedRow());
                    }
                }
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_removeAgentButtonActionPerformed

    private void addMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMissionButtonActionPerformed
        if (connected) {
            EditMissionDialog dialog = new EditMissionDialog(this, true);
            dialog.setVisible(true);
            if (dialog.getMission() != null) {
                try {
                    missionManager.createMission(dialog.getMission());
                    refreshLists();
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            if (!started) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_SESSION_FIRST"));
            } else {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
            }
        }
    }//GEN-LAST:event_addMissionButtonActionPerformed

    private void updateMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            EditMissionDialog dialog = new EditMissionDialog(this, true, missions.get(missionTable.getSelectedRow()));
            dialog.setVisible(true);
            if (dialog.getMission() != null) {
                try {
                    missionManager.updateMission(dialog.getMission());
                    refreshLists();
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_updateMissionButtonActionPerformed

    private void removeMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            try {
                if (contractManager.findAllContracts(missions.get(missionTable.getSelectedRow())).isEmpty()) {
                    missionManager.removeMission(missions.get(missionTable.getSelectedRow()));
                    missions.remove(missionTable.getSelectedRow());
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("MISSION_DESTROY_CONTACTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("WARNING"), JOptionPane.YES_NO_CANCEL_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        contractManager.removeAllContractsForMission(missions.get(missionTable.getSelectedRow()));
                        missionManager.removeMission(missions.get(missionTable.getSelectedRow()));
                        missions.remove(missionTable.getSelectedRow());
                    }
                }
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_removeMissionButtonActionPerformed

    private void viewMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            ViewMissionDialog dialog = new ViewMissionDialog(this, false, missions.get(missionTable.getSelectedRow()));
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewMissionButtonActionPerformed

    private void addContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContractButtonActionPerformed
        if (connected) {
            Agent agent = null;
            Mission mission = null;
            if (agentTable.getSelectedRow() != -1) {
                agent = agents.get(agentTable.getSelectedRow());
            }
            if (missionTable.getSelectedRow() != -1) {
                mission = missions.get(missionTable.getSelectedRow());
            }
            ContractAddSwingWorker contractWorker = new ContractAddSwingWorker(this, mission, agent);
            contractWorker.execute();
        } else {
            if (!started) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_SESSION_FIRST"));
            } else {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
            }
        }
    }//GEN-LAST:event_addContractButtonActionPerformed

    private void updateContractButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateContractButton1ActionPerformed
        if (contractTable.getSelectedRow() != -1) {
            Contract contr = contracts.get(contractTable.getSelectedRow());
            EditContractDialog dialog = new EditContractDialog(this, true, contr);

            dialog.setVisible(true);

            if (dialog.getContract() != null) {
                try {
                    contractManager.updateContract(dialog.getContract());
                    refreshLists();
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_updateContractButton1ActionPerformed

    private void removeContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeContractButtonActionPerformed
        if (contractTable.getSelectedRow() != -1) {
            try {
                contractManager.removeContract(contracts.get(contractTable.getSelectedRow()));
                contracts.remove(contractTable.getSelectedRow());
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_removeContractButtonActionPerformed

    private void viewContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewContractButtonActionPerformed
        if (contractTable.getSelectedRow() != -1) {
            ViewContractDialog dialog = new ViewContractDialog(this, false, contracts.get(contractTable.getSelectedRow()));
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewContractButtonActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        if (started) {
            if (!connected) {
                connectToDataSource();
                if (!connected) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
                }
            }
            try {
                refreshLists();
                enableAll(true);
            } catch (ServiceFailureException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_SESSION_FIRST"));
        }
    }//GEN-LAST:event_refreshMenuItemActionPerformed

    private void agentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agentTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && started) {
            if (agentTable.getSelectedColumnCount() != 0) {
                if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 0) {
                    viewAgentButtonActionPerformed(null);
                } else {
                    if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 1) {
                        updateAgentButtonActionPerformed(null);
                    }
                }
            }
        }
    }//GEN-LAST:event_agentTableMouseClicked

    private void missionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_missionTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && started) {
            if (missionTable.getSelectedColumnCount() != 0) {
                if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 0) {
                    viewMissionButtonActionPerformed(null);
                } else {
                    if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 1) {
                        updateMissionButtonActionPerformed(null);
                    }
                }
            }
        }
    }//GEN-LAST:event_missionTableMouseClicked

    private void contractTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && started) {
            if (contractTable.getSelectedColumnCount() != 0) {
                if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 0) {
                    viewContractButtonActionPerformed(null);
                } else {
                    if (Integer.valueOf(config.getProperty("DOUBLE_CLICK", "0")) == 1) {
                        updateContractButton1ActionPerformed(null);
                    }
                }
            }
        }
    }//GEN-LAST:event_contractTableMouseClicked

    private void properitiesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_properitiesMenuItemActionPerformed
        PropertiesDialog dialog = new PropertiesDialog(this, true, config);
        dialog.setVisible(true);

        if (started && dialog.serverChange) {
            connectToDataSource();
            if (!connected) {
                refreshLists();
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
            }
            enableAll(true);
        }

        saveProperities();
    }//GEN-LAST:event_properitiesMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveProperities();
    }//GEN-LAST:event_formWindowClosing

    private void startSessionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSessionMenuItemActionPerformed
        connectToDataSource();
        if (!connected) {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UNABLE_CONNECT"));
            //started = false;
        } else {
            if (checkDatabase()) {
                started = true;
                try {
                    refreshLists();
                } catch (ServiceFailureException ex) {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("DATABASE_COMPATIBILTY_ERROR"));
                enableAll(false);
                tablesMenu.setEnabled(true);
                statLabel.setText(" ");
            }
        }
    }//GEN-LAST:event_startSessionMenuItemActionPerformed

    private void contractAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractAgentButtonActionPerformed
        if (agentTable.getSelectedRow() != -1) {
            this.viewDialog(agents.get(agentTable.getSelectedRow()));
        }
    }//GEN-LAST:event_contractAgentButtonActionPerformed

    private void contractMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            this.viewDialog(missions.get(missionTable.getSelectedRow()));
        }
    }//GEN-LAST:event_contractMissionButtonActionPerformed

    private void clearAgentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAgentMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_AGENTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_AGENTS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.clearTable("CONTRACT");
                databaseManager.clearTable("AGENT");
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_clearAgentMenuItemActionPerformed

    private void clearMissionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMissionMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_MISSIONS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_MISSIONS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.clearTable("CONTRACT");
                databaseManager.clearTable("MISSION");
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_clearMissionMenuItemActionPerformed

    private void clearContractMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearContractMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_CONTRACTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_CONTRACTS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.clearTable("CONTRACT");
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_clearContractMenuItemActionPerformed

    private void clearAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_ALL"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("CLEAR_ALL_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.clearTable("CONTRACT");
                databaseManager.clearTable("AGENT");
                databaseManager.clearTable("MISSION");
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_clearAllMenuItemActionPerformed

    private void rebuildContractMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rebuildContractMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_CONTRACTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_CONTRACTS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.dropTable("CONTRACT");
            } catch (ServiceFailureException ex) {
            }
            try {
                databaseManager.createTable(SQL_CONTRACT);
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_rebuildContractMenuItemActionPerformed

    private void rebuildMissionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rebuildMissionMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_MISSIONS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_MISSIONS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.dropTable("CONTRACT");
                databaseManager.dropTable("MISSION");
            } catch (ServiceFailureException ex) {
            }
            try {
                databaseManager.createTable(SQL_MISSION);
                databaseManager.createTable(SQL_CONTRACT);
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_rebuildMissionMenuItemActionPerformed

    private void rebuildAgentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rebuildAgentMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_AGENTS"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_AGENTS_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.dropTable("CONTRACT");
                databaseManager.dropTable("AGENT");
            } catch (ServiceFailureException ex) {
            }
            try {
                databaseManager.createTable(SQL_AGENT);
                databaseManager.createTable(SQL_CONTRACT);
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_rebuildAgentMenuItemActionPerformed

    private void rebuildAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rebuildAllMenuItemActionPerformed


        int result = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_ALL"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("REBUILD_ALL_H"), JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                databaseManager.dropTable("CONTRACT");
                databaseManager.dropTable("AGENT");
                databaseManager.dropTable("MISSION");
            } catch (ServiceFailureException ex) {
            }
            try {
                databaseManager.createTable(SQL_MISSION);
                databaseManager.createTable(SQL_AGENT);
                databaseManager.createTable(SQL_CONTRACT);
                refreshLists();
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_rebuildAllMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgencyManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgencyManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgencyManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgencyManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AgencyManagerFrame().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAgentButton;
    private javax.swing.JButton addContractButton;
    private javax.swing.JButton addMissionButton;
    private javax.swing.JTabbedPane agencyTablesTabbedPane;
    private javax.swing.JPanel agentPanel;
    private javax.swing.JScrollPane agentScrollPane;
    private javax.swing.JTable agentTable;
    private javax.swing.JMenuItem clearAgentMenuItem;
    private javax.swing.JMenuItem clearAllMenuItem;
    private javax.swing.JMenuItem clearContractMenuItem;
    private javax.swing.JMenu clearMenu;
    private javax.swing.JMenuItem clearMissionMenuItem;
    private javax.swing.JButton contractAgentButton;
    private javax.swing.JButton contractMissionButton;
    private javax.swing.JPanel contractPanel;
    private javax.swing.JScrollPane contractScrollPane;
    private javax.swing.JTable contractTable;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel loggerLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPopupMenu.Separator menuSeparator1;
    private javax.swing.JPanel missionPanel;
    private javax.swing.JScrollPane missionScrollPane;
    private javax.swing.JTable missionTable;
    private javax.swing.JMenuItem properitiesMenuItem;
    private javax.swing.JMenuItem rebuildAgentMenuItem;
    private javax.swing.JMenuItem rebuildAllMenuItem;
    private javax.swing.JMenuItem rebuildContractMenuItem;
    private javax.swing.JMenu rebuildMenu;
    private javax.swing.JMenuItem rebuildMissionMenuItem;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JButton removeAgentButton;
    private javax.swing.JButton removeContractButton;
    private javax.swing.JButton removeMissionButton;
    private javax.swing.JMenuItem startSessionMenuItem;
    private javax.swing.JLabel statLabel;
    private javax.swing.JPanel statPanel;
    private javax.swing.JMenu tablesMenu;
    private javax.swing.JButton updateAgentButton;
    private javax.swing.JButton updateContractButton1;
    private javax.swing.JButton updateMissionButton;
    private javax.swing.JButton viewAgentButton;
    private javax.swing.JButton viewContractButton;
    private javax.swing.JButton viewMissionButton;
    // End of variables declaration//GEN-END:variables

    private static class agentTableModel extends DefaultTableModel {

        public agentTableModel() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("NAME");
                case 1:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("BORN");
                case 2:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ACTIVE");
                case 3:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("RANK");
                case 4:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("NOTES");
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Calendar.class;
                case 2:
                    return Boolean.class;
                case 3:
                    return Long.class;
                case 4:
                    return String.class;
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public int getRowCount() {
            return agents.size();
        }

        @Override
        public int getColumnCount() {
            return 5; //without id attribute
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return agents.get(rowIndex).getName();
                case 1:
                    //return agents.get(rowIndex).getBorn().getTime().toString();
                    return getLocalDate(Locale.getDefault(), TimeZone.getDefault(), agents.get(rowIndex).getBorn());
                case 2:
                    return agents.get(rowIndex).isActive();
                case 3:
                    return agents.get(rowIndex).getRank();
                //return agents.get(rowIndex).getId();
                case 4:
                    return agents.get(rowIndex).getNotes();
                default:
                    throw new IllegalArgumentException("undefined collum");
            }
        }
    }

    private static class missionTableModel extends DefaultTableModel {

        public missionTableModel() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("NAME");
                case 1:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("DIFFICULTY");
                case 2:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOCATION");
                case 3:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("DETAILS");
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Long.class;
                case 2:
                    return String.class;
                case 3:
                    return String.class;
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public int getRowCount() {
            return missions.size();
        }

        @Override
        public int getColumnCount() {
            return 4; //without id attribute
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return missions.get(rowIndex).getName();
                case 1:
                    return missions.get(rowIndex).getDifficulty();
                case 2:
                    return missions.get(rowIndex).getLocation();
                case 3:
                    return missions.get(rowIndex).getDetails();
                default:
                    throw new IllegalArgumentException("undefined collum");
            }
        }
    }

    private static class contractTableModel extends DefaultTableModel {

        public contractTableModel() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("MISSION");
                case 1:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGENT");
                case 2:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("BUDGET");
                case 3:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("START_TIME");
                case 4:
                    return java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("END_TIME");
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return Long.class;
                case 3:
                    return Calendar.class;
                case 4:
                    return Calendar.class;
                default:
                    throw new IllegalArgumentException("more column than excepted");
            }
        }

        @Override
        public int getRowCount() {
            return contracts.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return contracts.get(rowIndex).getMission().getName();
                case 1:
                    return contracts.get(rowIndex).getAgent().getName();
                case 2:
                    return contracts.get(rowIndex).getBudget();
                case 3:
                    return getLocalDate(Locale.getDefault(), TimeZone.getDefault(), contracts.get(rowIndex).getStartTime());
                case 4:
                    return getLocalDate(Locale.getDefault(), TimeZone.getDefault(), contracts.get(rowIndex).getEndTime());
                default:
                    throw new IllegalArgumentException("undefined collum");
            }

        }
    }

    private boolean checkDatabase()
            throws ServiceFailureException {


        Calendar born = Calendar.getInstance();
        int age = born.get(Calendar.YEAR) - 21;
        born.set(Calendar.YEAR, age);

        Agent ag = new Agent(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("TEST"), born, true, 150, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("TEST"));
        Mission ms = new Mission(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("TEST"), 150, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("TEST"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("TEST"));
        Contract ct = new Contract(ms, ag, 150000000000l, new GregorianCalendar(), new GregorianCalendar());


        logger.log(Level.FINE, "Testing Datase Tables");
        try {
            missionManager.createMission(ms);
            agentManager.createAgent(ag);
            contractManager.createContract(ct);

            missionManager.updateMission(ms);
            agentManager.updateAgent(ag);
            contractManager.updateContract(ct);

            contractManager.removeContract(ct);
            missionManager.removeMission(ms);
            agentManager.removeAgent(ag);
        } catch (IllegalArgumentException | ServiceFailureException ex) {
            try {
                contractManager.removeContract(ct);
            } catch (ServiceFailureException | IllegalArgumentException e) {
            }
            try {
                missionManager.removeMission(ms);
            } catch (ServiceFailureException | IllegalArgumentException e) {
            }
            try {
                agentManager.removeAgent(ag);
            } catch (ServiceFailureException | IllegalArgumentException e) {
            }

            logger.log(Level.FINE, "Testing Failed", ex);
            return false;
        }

        logger.log(Level.FINE, "Testing Succesfully Finished");
        return true;
    }

    private class LoadingSwingWorker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            int i = 0;
            while (true) {
                for (long l = 0; l < 100000000l; l++) {
                    if (workerDone[0] && workerDone[1] && workerDone[2]) {
                        return null;
                    }
                }
                publish(i);
                i++;
                if (i > 3) {
                    i = 0;
                }
            }
        }

        @Override
        protected void process(List<Integer> chunks) {
            switch (chunks.get(0)) {
                case 0:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING"));
                    break;
                case 1:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + ".");
                    break;
                case 2:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + "..");
                    break;
                case 3:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + "...");
                    break;
            }
        }

        @Override
        protected void done() {
            statLabel.setText(" ");
            enableAll(true);

        }
    }

    private class AgentRefreshSwingWorker extends SwingWorker<List<Agent>, Void> {

        @Override
        protected List<Agent> doInBackground() throws Exception {
            try {
                for (Agent agent : agentManager.getAllAgents()) {
                    agents.add(agent);
                }
                return agents;
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                return agents;
            }
        }

        @Override
        protected void done() {
            workerDone[0] = true;
            DefaultTableModel tableModel = (DefaultTableModel) agentTable.getModel();
            tableModel.fireTableDataChanged();
            //agentTable.repaint();
        }
    }

    private class MissionRefreshSwingWorker extends SwingWorker<List<Mission>, Void> {

        @Override
        protected List<Mission> doInBackground() throws Exception {
            try {
                for (Mission mission : missionManager.getAllMissions()) {
                    missions.add(mission);
                }
                return missions;
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                return missions;
            }
        }

        @Override
        protected void done() {
            workerDone[1] = true;
            DefaultTableModel tableModel = (DefaultTableModel) missionTable.getModel();
            tableModel.fireTableDataChanged();
            //missionTable.repaint();
        }
    }

    private class ContractRefreshSwingWorker extends SwingWorker<List<Contract>, Void> {

        @Override
        protected List<Contract> doInBackground() throws Exception {
            try {
                for (Contract contract : contractManager.findAllContracts()) {
                    contracts.add(contract);
                }
                return contracts;
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                return contracts;
            }
        }

        @Override
        protected void done() {
            workerDone[2] = true;
            DefaultTableModel tableModel = (DefaultTableModel) contractTable.getModel();
            tableModel.fireTableDataChanged();
            //contractTable.repaint();
        }
    }

    private class LoadingContractSwingWorker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            int i = 0;
            while (true) {
                for (long l = 0; l < 100000000l; l++) {
                    if (isCancelled()) {
                        return null;
                    }
                }
                publish(i);
                i++;
                if (i > 3) {
                    i = 0;
                }
            }
        }

        @Override
        protected void process(List<Integer> chunks) {
            switch (chunks.get(0)) {
                case 0:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING"));
                    break;
                case 1:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + ".");
                    break;
                case 2:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + "..");
                    break;
                case 3:
                    statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING") + "...");
                    break;
            }
        }

        @Override
        protected void done() {
            statLabel.setText(" ");
            enableAll(true);

        }
    }

    private class ContractAddSwingWorker extends SwingWorker<Void, Void> {

        private List<Mission> missions;
        private List<Agent> agents;
        private List<Contract> contracts;
        private Mission mission;
        private Agent agent;
        private JFrame frame;
        private int select;

        public ContractAddSwingWorker(JFrame parent, Mission mission, Agent agent) {
            this.mission = mission;
            this.agent = agent;
            frame = parent;
            select = 1;
        }

        public ContractAddSwingWorker(JFrame parent) {
            frame = parent;
            select = 2;
        }

        public ContractAddSwingWorker(JFrame parent, Agent agent) {
            this.agent = agent;
            frame = parent;
            select = 4;
        }

        public ContractAddSwingWorker(JFrame parent, Mission mission) {
            this.mission = mission;
            frame = parent;
            select = 3;
        }

        @Override
        protected Void doInBackground() throws Exception {

            LoadingContractSwingWorker loadingWorker = new LoadingContractSwingWorker();

            try {
                if (select == 1) {
                    enableAll(false);
                    loadingWorker.execute();
                    agents = agentManager.getAllAgents();
                    missions = missionManager.getAllMissions();
                    contracts = contractManager.findAllContracts();
                }
                if (select == 2) {
                    contracts = contractManager.findAllContracts();
                }

                if (select == 3) {
                    contracts = contractManager.findAllContracts(mission);
                }

                if (select == 4) {
                    contracts = contractManager.findAllContracts(agent);
                }
            } catch (ServiceFailureException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                select = 0;
            }

            loadingWorker.cancel(false);
            return null;
        }

        @Override
        protected void done() {
            if (select == 1) {
                EditContractDialog dialog = new EditContractDialog(frame, true, missions, agents, contracts, mission, agent);
                dialog.setVisible(true);

                try {
                    if (dialog.getContract() != null) {
                        contractManager.createContract(dialog.getContract());
                        refreshLists();
                    }
                } catch (ServiceFailureException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR_OCCURED"), java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            } else if (select == 2) {

                viewContractsDialog dialog = new viewContractsDialog(frame, false, contracts);
                dialog.setVisible(true);

            } else if (select == 3) {

                viewContractsDialog dialog = new viewContractsDialog(frame, false, contracts, mission);
                dialog.setVisible(true);

            } else if (select == 4) {

                viewContractsDialog dialog = new viewContractsDialog(frame, false, contracts, agent);
                dialog.setVisible(true);

            }

        }
    }

    private void refreshLists() {
        agents.clear();
        agentTable.repaint();
        missions.clear();
        missionTable.repaint();
        contracts.clear();
        contractTable.repaint();

        if (!started) {
            return;
        }

        workerDone = new boolean[]{false, false, false};

        enableAll(false);

        if (connected) {
            LoadingSwingWorker loadingWorker = new LoadingSwingWorker();
            loadingWorker.execute();

            AgentRefreshSwingWorker agentWorker = new AgentRefreshSwingWorker();
            agentWorker.execute();

            MissionRefreshSwingWorker missionWorker = new MissionRefreshSwingWorker();
            missionWorker.execute();

            ContractRefreshSwingWorker contractWorker = new ContractRefreshSwingWorker();
            contractWorker.execute();
        }
    }

    private void enableAll(boolean enable) {
        tablesMenu.setEnabled(enable);
        started = enable;
        addAgentButton.setEnabled(enable);
        addContractButton.setEnabled(enable);
        addMissionButton.setEnabled(enable);
        updateAgentButton.setEnabled(enable);
        updateContractButton1.setEnabled(enable);
        updateMissionButton.setEnabled(enable);
        viewAgentButton.setEnabled(enable);
        viewMissionButton.setEnabled(enable);
        viewContractButton.setEnabled(enable);
        removeAgentButton.setEnabled(enable);
        removeContractButton.setEnabled(enable);
        removeMissionButton.setEnabled(enable);
        contractAgentButton.setEnabled(enable);
        contractMissionButton.setEnabled(enable);
        if (!enable) {
            statLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("LOADING"));
        } else {
            statLabel.setText(" ");
        }
    }

    private static String getLocalDate(Locale locale, TimeZone tz, Calendar cal) {
        if (cal == null) {
            return "";
        }
        Date date = cal.getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        df.setTimeZone(tz);
        return df.format(date);
    }

    private void loggerOutput() throws IOException {
        FileHandler fh = null;
        try {
            fh = new FileHandler("MyLogFile.log");
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw ex;
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.ALL);
    }
}
