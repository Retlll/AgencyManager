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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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
    private Properties config;

    /**
     * Creates new form AgencyManagerFrame
     */
    public AgencyManagerFrame() {
        initComponents();
        started = false;

        contextMenu();
        initProperties();
        //connectToDataSource();
        JTableHeader agentHeader = agentTable.getTableHeader();
        agentHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = agentTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                switch (agentTable.getColumnName(column)) {
                    case "Name":
                        if (lastSort[0] != 0) {
                            Collections.sort(agents, new Comparators.AgentComparatorByName());
                            lastSort[0] = 0;
                        } else {
                            Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByName()));
                            lastSort[0] = -1;
                        }
                        tableModel = (DefaultTableModel) agentTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Born":
                        if (lastSort[0] != 1) {
                            Collections.sort(agents, new Comparators.AgentComparatorByBorn());
                            lastSort[0] = 1;
                        } else {
                            Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByBorn()));
                            lastSort[0] = -1;
                        }
                        tableModel = (DefaultTableModel) agentTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Active":
                        if (lastSort[0] != 2) {
                            Collections.sort(agents, new Comparators.AgentComparatorByActive());
                            lastSort[0] = 2;
                        } else {
                            Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByActive()));
                            lastSort[0] = -1;
                        }
                        tableModel = (DefaultTableModel) agentTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Rank":
                        if (lastSort[0] != 3) {
                            Collections.sort(agents, new Comparators.AgentComparatorByRank());
                            lastSort[0] = 3;
                        } else {
                            Collections.sort(agents, Collections.reverseOrder(new Comparators.AgentComparatorByRank()));
                            lastSort[0] = -1;
                        }
                        tableModel = (DefaultTableModel) agentTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                }
            }
        });

        JTableHeader missionHeader = missionTable.getTableHeader();
        missionHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = missionTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                switch (missionTable.getColumnName(column)) {
                    case "Name":
                        if (lastSort[1] != 0) {
                            Collections.sort(missions, new Comparators.MissionComparatorByName());
                            lastSort[1] = 0;
                        } else {
                            Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByName()));
                            lastSort[1] = -1;
                        }
                        tableModel = (DefaultTableModel) missionTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Difficulty":
                        if (lastSort[1] != 1) {
                            Collections.sort(missions, new Comparators.MissionComparatorByDifficulty());
                            lastSort[1] = 1;
                        } else {
                            Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByDifficulty()));
                            lastSort[1] = -1;
                        }
                        tableModel = (DefaultTableModel) missionTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Location":
                        if (lastSort[1] != 2) {
                            Collections.sort(missions, new Comparators.MissionComparatorByLocation());
                            lastSort[1] = 2;
                        } else {
                            Collections.sort(missions, Collections.reverseOrder(new Comparators.MissionComparatorByLocation()));
                            lastSort[1] = -1;
                        }
                        tableModel = (DefaultTableModel) missionTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                }
            }
        });

        JTableHeader contractHeader = contractTable.getTableHeader();
        contractHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = contractTable.columnAtPoint(e.getPoint());
                DefaultTableModel tableModel = null;
                switch (contractTable.getColumnName(column)) {
                    case "Agent":
                        if (lastSort[2] != 0) {
                            Collections.sort(contracts, new Comparators.ContractComparatorByAgent());
                            lastSort[2] = 0;
                        } else {
                            Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByAgent()));
                            lastSort[2] = -1;
                        }
                        tableModel = (DefaultTableModel) contractTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Mission":
                        if (lastSort[2] != 1) {
                            Collections.sort(contracts, new Comparators.ContractComparatorByMission());
                            lastSort[2] = 1;
                        } else {
                            Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByMission()));
                            lastSort[2] = -1;
                        }
                        tableModel = (DefaultTableModel) contractTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Budget":
                        if (lastSort[2] != 2) {
                            Collections.sort(contracts, new Comparators.ContractComparatorByBudget());
                            lastSort[2] = 2;
                        } else {
                            Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByBudget()));
                            lastSort[2] = -1;
                        }
                        tableModel = (DefaultTableModel) contractTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "Start time":
                        if (lastSort[2] != 3) {
                            Collections.sort(contracts, new Comparators.ContractComparatorByStartTime());
                            lastSort[2] = 3;
                        } else {
                            Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByStartTime()));
                            lastSort[2] = -1;
                        }
                        tableModel = (DefaultTableModel) contractTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                    case "End time":
                        if (lastSort[2] != 4) {
                            Collections.sort(contracts, new Comparators.ContractComparatorByEndTime());
                            lastSort[2] = 4;
                        } else {
                            Collections.sort(contracts, Collections.reverseOrder(new Comparators.ContractComparatorByEndTime()));
                            lastSort[2] = -1;
                        }
                        tableModel = (DefaultTableModel) contractTable.getModel();
                        tableModel.fireTableDataChanged();
                        break;
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        agencyTablesTabbedPane = new javax.swing.JTabbedPane();
        agentPanel = new javax.swing.JPanel();
        agentScrollPane = new javax.swing.JScrollPane();
        agentTable = new javax.swing.JTable();
        addAgentButton = new javax.swing.JButton();
        updateAgentButton = new javax.swing.JButton();
        removeAgentButton = new javax.swing.JButton();
        viewAgentButton = new javax.swing.JButton();
        missionPanel = new javax.swing.JPanel();
        missionScrollPane = new javax.swing.JScrollPane();
        missionTable = new javax.swing.JTable();
        addMissionButton = new javax.swing.JButton();
        updateMissionButton = new javax.swing.JButton();
        removeMissionButton = new javax.swing.JButton();
        viewMissionButton = new javax.swing.JButton();
        contractPanel = new javax.swing.JPanel();
        contractScrollPane = new javax.swing.JScrollPane();
        contractTable = new javax.swing.JTable();
        addContractButton = new javax.swing.JButton();
        removeContractButton = new javax.swing.JButton();
        updateContractButton1 = new javax.swing.JButton();
        viewContractButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        statLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        startSessionMenuItem = new javax.swing.JMenuItem();
        menuSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        refreshMenuItem = new javax.swing.JMenuItem();
        properitiesMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        addAgentButton.setText("Add");
        addAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgentButtonActionPerformed(evt);
            }
        });

        updateAgentButton.setText("Update");
        updateAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAgentButtonActionPerformed(evt);
            }
        });

        removeAgentButton.setText("Remove");
        removeAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButtonActionPerformed(evt);
            }
        });

        viewAgentButton.setText("View");
        viewAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAgentButtonActionPerformed(evt);
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
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(agentScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        agentPanelLayout.setVerticalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, agentPanelLayout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAgentButton)
                    .addComponent(removeAgentButton)
                    .addComponent(updateAgentButton)
                    .addComponent(viewAgentButton))
                .addContainerGap())
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(agentPanelLayout.createSequentialGroup()
                    .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab("Agent", agentPanel);

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

        addMissionButton.setText("Add");
        addMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMissionButtonActionPerformed(evt);
            }
        });

        updateMissionButton.setText("Update");
        updateMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateMissionButtonActionPerformed(evt);
            }
        });

        removeMissionButton.setText("Remove");
        removeMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMissionButtonActionPerformed(evt);
            }
        });

        viewMissionButton.setText("View");
        viewMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewMissionButtonActionPerformed(evt);
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
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(missionScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        missionPanelLayout.setVerticalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionPanelLayout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMissionButton)
                    .addComponent(removeMissionButton)
                    .addComponent(updateMissionButton)
                    .addComponent(viewMissionButton))
                .addContainerGap())
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(missionPanelLayout.createSequentialGroup()
                    .addComponent(missionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab("Mission", missionPanel);

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

        addContractButton.setText("Add");
        addContractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContractButtonActionPerformed(evt);
            }
        });

        removeContractButton.setText("Remove");
        removeContractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeContractButtonActionPerformed(evt);
            }
        });

        updateContractButton1.setText("Update");
        updateContractButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateContractButton1ActionPerformed(evt);
            }
        });

        viewContractButton.setText("View");
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
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        contractPanelLayout.setVerticalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contractPanelLayout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addContractButton)
                    .addComponent(removeContractButton)
                    .addComponent(updateContractButton1)
                    .addComponent(viewContractButton))
                .addContainerGap())
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contractPanelLayout.createSequentialGroup()
                    .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        agencyTablesTabbedPane.addTab("Contract", contractPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agencyTablesTabbedPane)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agencyTablesTabbedPane))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        statLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statLabel.setForeground(new java.awt.Color(51, 0, 0));
        statLabel.setText(" ");
        statLabel.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(statLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        fileMenu.setText("File");

        startSessionMenuItem.setText("Start session");
        startSessionMenuItem.setToolTipText("");
        startSessionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSessionMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(startSessionMenuItem);
        fileMenu.add(menuSeparator1);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        refreshMenuItem.setText("Refresh");
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(refreshMenuItem);

        properitiesMenuItem.setText("Properities");
        properitiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                properitiesMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(properitiesMenuItem);

        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

            System.err.append("config file missing, default settings set");
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
            System.out.println("unable to create config file");
            Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex) {
            System.out.println("unable to edit config file");
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
            Logger.getLogger(AgencyManagerFrame.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        missionManager = new MissionManagerImpl(dataSource);
        agentManager = new AgentManagerImpl(dataSource);
        contractManager = new ContractManagerImpl(dataSource, missionManager, agentManager);

        refreshLists();
    }

    private void contextMenu() {
        final JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem update = new JMenuItem("Update");
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

        JMenuItem view = new JMenuItem("View");
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

        JMenuItem remove = new JMenuItem("Remove");
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
                agentManager.createAgent(dialog.getAgent());
                refreshLists();
            }
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Start session first!");
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
                agentManager.updateAgent(dialog.getAgent());
                refreshLists();
            }
            dialog.dispose();
        }
    }//GEN-LAST:event_updateAgentButtonActionPerformed

    private void removeAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButtonActionPerformed
        if (agentTable.getSelectedRow() != -1) {
            if (contractManager.findAllContracts(agents.get(agentTable.getSelectedRow())).isEmpty()) {
                agentManager.removeAgent(agents.get(agentTable.getSelectedRow()));
                agents.remove(agentTable.getSelectedRow());
            } else {
                int dialogResult = JOptionPane.showConfirmDialog(null, "This agent has assigned contract, would you like to destroy all his contract?", "Warnign", JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    contractManager.removeAllContractsForAgent(agents.get(agentTable.getSelectedRow()));
                    agentManager.removeAgent(agents.get(agentTable.getSelectedRow()));
                    agents.remove(agentTable.getSelectedRow());
                }
            }
            refreshLists();
        }
    }//GEN-LAST:event_removeAgentButtonActionPerformed

    private void addMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMissionButtonActionPerformed
        if (connected) {
            EditMissionDialog dialog = new EditMissionDialog(this, true);
            dialog.setVisible(true);
            if (dialog.getMission() != null) {
                missionManager.createMission(dialog.getMission());
                refreshLists();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Start session first!");
        }
    }//GEN-LAST:event_addMissionButtonActionPerformed

    private void updateMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            EditMissionDialog dialog = new EditMissionDialog(this, true, missions.get(missionTable.getSelectedRow()));
            dialog.setVisible(true);
            if (dialog.getMission() != null) {
                missionManager.updateMission(dialog.getMission());
                refreshLists();
            }
        }
    }//GEN-LAST:event_updateMissionButtonActionPerformed

    private void removeMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMissionButtonActionPerformed
        if (missionTable.getSelectedRow() != -1) {
            if (contractManager.findAllContracts(missions.get(missionTable.getSelectedRow())).isEmpty()) {
                missionManager.removeMission(missions.get(missionTable.getSelectedRow()));
                missions.remove(missionTable.getSelectedRow());
            } else {
                int dialogResult = JOptionPane.showConfirmDialog(null, "This mission has assigned contract, would you like to destroy all his contract?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    contractManager.removeAllContractsForMission(missions.get(missionTable.getSelectedRow()));
                    missionManager.removeMission(missions.get(missionTable.getSelectedRow()));
                    missions.remove(missionTable.getSelectedRow());
                }
            }
            refreshLists();
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
            JOptionPane.showMessageDialog(this, "Start session first!");
        }
    }//GEN-LAST:event_addContractButtonActionPerformed

    private void updateContractButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateContractButton1ActionPerformed
        if (contractTable.getSelectedRow() != -1) {
            Contract contr = contracts.get(contractTable.getSelectedRow());
            EditContractDialog dialog = new EditContractDialog(this, true, contr);

            dialog.setVisible(true);

            if (dialog.getContract() != null) {
                contractManager.updateContract(dialog.getContract());
                refreshLists();
            }
        }
    }//GEN-LAST:event_updateContractButton1ActionPerformed

    private void removeContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeContractButtonActionPerformed
        if (contractTable.getSelectedRow() != -1) {
            contractManager.removeContract(contracts.get(contractTable.getSelectedRow()));
            contracts.remove(contractTable.getSelectedRow());
            refreshLists();
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
            }
            refreshLists();
        } else {
            JOptionPane.showMessageDialog(this, "Start session first!");
        }
    }//GEN-LAST:event_refreshMenuItemActionPerformed

    private void agentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agentTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
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
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
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
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
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
                JOptionPane.showMessageDialog(this, "Unable to connect to database, change properties!");
            }
        }

        saveProperities();
    }//GEN-LAST:event_properitiesMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveProperities();
    }//GEN-LAST:event_formWindowClosing

    private void startSessionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSessionMenuItemActionPerformed
        connectToDataSource();
        if(!connected){
            JOptionPane.showMessageDialog(this, "Unable to connect to database, change properties!");
            started = false;
        } else {
        started = true;
        }
    }//GEN-LAST:event_startSessionMenuItemActionPerformed

    
    public void viewDialog(Agent agent) {
        ContractAddSwingWorker contractWorker = new ContractAddSwingWorker(this, agent);
            contractWorker.execute();
    }
    
    public void viewDialog(Mission mission) {
        ContractAddSwingWorker contractWorker = new ContractAddSwingWorker(this, mission);
            contractWorker.execute();
        
    }
    
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
    private javax.swing.JPanel contractPanel;
    private javax.swing.JScrollPane contractScrollPane;
    private javax.swing.JTable contractTable;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPopupMenu.Separator menuSeparator1;
    private javax.swing.JPanel missionPanel;
    private javax.swing.JScrollPane missionScrollPane;
    private javax.swing.JTable missionTable;
    private javax.swing.JMenuItem properitiesMenuItem;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JButton removeAgentButton;
    private javax.swing.JButton removeContractButton;
    private javax.swing.JButton removeMissionButton;
    private javax.swing.JMenuItem startSessionMenuItem;
    private javax.swing.JLabel statLabel;
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
                    return "Name";
                case 1:
                    return "Born";
                case 2:
                    return "Active";
                case 3:
                    return "Rank";
                case 4:
                    return "Notes";
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
                    return "Name";
                case 1:
                    return "Difficulty";
                case 2:
                    return "Location";
                case 3:
                    return "Details";
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
                    return "Mission";
                case 1:
                    return "Agent";
                case 2:
                    return "Budget";
                case 3:
                    return "Start time";
                case 4:
                    return "End time";
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

    private void checkDatabase(MissionManagerImpl msManager, AgentManagerImpl agManager, ContractManagerImpl cnManager)
            throws ServiceFailureException {

        Agent ag = new Agent(null, "test", new GregorianCalendar(), true, 150, "test");
        Mission ms = new Mission(null, "test", 150, "test", "test");
        Contract ct = new Contract(ms, ag, 150000000000l, new GregorianCalendar(), new GregorianCalendar());

        msManager.createMission(ms);
        agManager.createAgent(ag);
        cnManager.createContract(ct);

        msManager.updateMission(ms);
        agManager.updateAgent(ag);
        cnManager.updateContract(ct);

        cnManager.removeContract(ct);
        msManager.removeMission(ms);
        agManager.removeAgent(ag);
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
                    statLabel.setText("Loading");
                    break;
                case 1:
                    statLabel.setText("Loading" + ".");
                    break;
                case 2:
                    statLabel.setText("Loading" + "..");
                    break;
                case 3:
                    statLabel.setText("Loading" + "...");
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
            for (Agent agent : agentManager.getAllAgents()) {
                agents.add(agent);
            }
            return agents;
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
            for (Mission mission : missionManager.getAllMissions()) {
                missions.add(mission);
            }
            return missions;
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
            for (Contract contract : contractManager.findAllContracts()) {
                contracts.add(contract);
            }
            return contracts;
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
                    statLabel.setText("Loading");
                    break;
                case 1:
                    statLabel.setText("Loading" + ".");
                    break;
                case 2:
                    statLabel.setText("Loading" + "..");
                    break;
                case 3:
                    statLabel.setText("Loading" + "...");
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
            enableAll(false);

            LoadingContractSwingWorker loadingWorker = new LoadingContractSwingWorker();

            if (select == 1) {
                loadingWorker.execute();
                agents = agentManager.getAllAgents();
                missions = missionManager.getAllMissions();
            }
            if (select == 2) {
                contracts = contractManager.findAllContracts();
            }

            if (select == 3) {
                contracts = contractManager.findAllContracts(mission);
            }

            if (select == 4) {
                contracts = contractManager.findAllContracts(mission);
            }

            loadingWorker.cancel(false);
            return null;
        }

        @Override
        protected void done() {
            if (select == 1) {
                EditContractDialog dialog = new EditContractDialog(frame, true, missions, agents, contractManager, mission, agent);
                dialog.setVisible(true);

                if (dialog.getContract() != null) {
                    contractManager.createContract(dialog.getContract());
                    refreshLists();
                }
            } else if (select == 2) {
                
                viewContractsDialog dialog = new viewContractsDialog(frame, false, contracts);
                dialog.setVisible(true);
                
            } else if (select == 3) {
                
                viewContractsDialog dialog = new viewContractsDialog(frame, false, contracts, mission);
                dialog.setVisible(true);
                
            } else if (select == 4) {
                
                viewContractsDialog dialog = new viewContractsDialog(frame, false,contracts, agent);
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
        if (!enable) {
            statLabel.setText("Loading...");
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
}
