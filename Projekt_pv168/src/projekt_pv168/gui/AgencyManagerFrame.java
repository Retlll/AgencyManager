/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp.BasicDataSource;
import projekt_pv168.Agent;
import projekt_pv168.AgentManagerImpl;
import projekt_pv168.Contract;
import projekt_pv168.ContractManagerImpl;
import projekt_pv168.Mission;
import projekt_pv168.MissionManagerImpl;

/**
 *
 * @author Lenovo
 */
public class AgencyManagerFrame extends javax.swing.JFrame {

    private DataSource dataSource;
    private static List<Agent> agents = new ArrayList<>();
    private static List<Mission> missions = new ArrayList<>();
    private static List<Contract> contracts = new ArrayList<>();
    private MissionManagerImpl missionManager;
    private AgentManagerImpl agentManager;
    private ContractManagerImpl contractManager;

    /**
     * Creates new form AgencyManagerFrame
     */
    public AgencyManagerFrame() {
        initComponents();

        final JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem update = new JMenuItem("Update");
        update.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (agentTable.hasFocus() && agentTable.getSelectedRowCount() != 0) {
                    updateAgentButtonActionPerformed(null);
                }
            }
        });

        contextMenu.add(update);
        JMenuItem view = new JMenuItem("View");
        contextMenu.add(view);
        JMenuItem remove = new JMenuItem("Remove");
        contextMenu.add(remove);

        agentTable.setComponentPopupMenu(contextMenu);
        missionTable.setComponentPopupMenu(contextMenu);
        contractTable.setComponentPopupMenu(contextMenu);

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby://localhost:1527/AgencyManager;create=true");
        ds.setUsername("xmalych");
        ds.setPassword("123456");
        DataSource dataSource = ds;

        missionManager = new MissionManagerImpl(dataSource);
        agentManager = new AgentManagerImpl(dataSource);
        contractManager = new ContractManagerImpl(dataSource, missionManager, agentManager);

        refreshLists();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
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

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

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
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(agentScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
        );
        agentPanelLayout.setVerticalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, agentPanelLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAgentButton)
                    .addComponent(removeAgentButton)
                    .addComponent(updateAgentButton)
                    .addComponent(viewAgentButton))
                .addContainerGap())
            .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(agentPanelLayout.createSequentialGroup()
                    .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        jTabbedPane1.addTab("Agent", agentPanel);

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
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(missionScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
        );
        missionPanelLayout.setVerticalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionPanelLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMissionButton)
                    .addComponent(removeMissionButton)
                    .addComponent(updateMissionButton)
                    .addComponent(viewMissionButton))
                .addContainerGap())
            .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(missionPanelLayout.createSequentialGroup()
                    .addComponent(missionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        jTabbedPane1.addTab("Mission", missionPanel);

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
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
        );
        contractPanelLayout.setVerticalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contractPanelLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addContractButton)
                    .addComponent(removeContractButton)
                    .addComponent(updateContractButton1)
                    .addComponent(viewContractButton))
                .addContainerGap())
            .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contractPanelLayout.createSequentialGroup()
                    .addComponent(contractScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addGap(38, 38, 38)))
        );

        jTabbedPane1.addTab("Contract", contractPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        fileMenu.setText("File");

        startSessionMenuItem.setText("Start session");
        startSessionMenuItem.setToolTipText("");
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
        editMenu.add(properitiesMenuItem);

        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAgentButtonActionPerformed
        EditAgentDialog dialog = new EditAgentDialog(this, true);
        dialog.setVisible(true);
        if (dialog.getAgent() != null) {
            agentManager.createAgent(dialog.getAgent());
            refreshLists();
        }
        dialog.dispose();
    }//GEN-LAST:event_addAgentButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
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
        EditMissionDialog dialog = new EditMissionDialog(this, true);
        dialog.setVisible(true);
        if (dialog.getMission() != null) {
            missionManager.createMission(dialog.getMission());
            refreshLists();
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
        Agent agent = null;
        Mission mission = null;
        if (agentTable.getSelectedRow() != -1) {
            agent = agents.get(agentTable.getSelectedRow());
        }
        if (missionTable.getSelectedRow() != -1) {
            mission = missions.get(missionTable.getSelectedRow());
        }
        EditContractDialog dialog = new EditContractDialog(this, true, missionManager.getAllMissions(), agentManager.getAllAgents(), contractManager, mission, agent);

        dialog.setVisible(true);

        if (dialog.getContract() != null) {
            contractManager.createContract(dialog.getContract());
            refreshLists();
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
        refreshLists();
    }//GEN-LAST:event_refreshMenuItemActionPerformed

    private void agentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agentTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if (agentTable.getSelectedColumnCount() != 0) {
                viewAgentButtonActionPerformed(null);
            }
        }
    }//GEN-LAST:event_agentTableMouseClicked

    private void missionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_missionTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if (missionTable.getSelectedColumnCount() != 0) {
                viewMissionButtonActionPerformed(null);
            }
        }
    }//GEN-LAST:event_missionTableMouseClicked

    private void contractTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if (contractTable.getSelectedColumnCount() != 0) {
                viewContractButtonActionPerformed(null);
            }
        }
    }//GEN-LAST:event_contractTableMouseClicked

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
    private javax.swing.JTabbedPane jTabbedPane1;
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
    private javax.swing.JButton updateAgentButton;
    private javax.swing.JButton updateContractButton1;
    private javax.swing.JButton updateMissionButton;
    private javax.swing.JButton viewAgentButton;
    private javax.swing.JButton viewContractButton;
    private javax.swing.JButton viewMissionButton;
    // End of variables declaration//GEN-END:variables

    private static class agentTableModel extends AbstractTableModel {

        public agentTableModel() {
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
                    return agents.get(rowIndex).getBorn().getTime().toString();
                case 2:
                    return agents.get(rowIndex).isActive();
                case 3:
                    //return agents.get(rowIndex).getRank();
                    return agents.get(rowIndex).getId();
                case 4:
                    return agents.get(rowIndex).getNotes();
                default:
                    throw new IllegalArgumentException("undefined collum");
            }
        }
    }

    private static class missionTableModel extends AbstractTableModel {

        public missionTableModel() {
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Name";
                case 1:
                    return "Difficulty";
                case 2:
                    return "Details";
                case 3:
                    return "Location";
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

    private static class contractTableModel extends AbstractTableModel {

        public contractTableModel() {
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
                    if (contracts.get(rowIndex).getStartTime() != null) {
                        return contracts.get(rowIndex).getStartTime().getTime().toString();
                    } else {
                        return "";
                    }
                case 4:
                    if (contracts.get(rowIndex).getEndTime() != null) {
                        return contracts.get(rowIndex).getEndTime().getTime().toString();
                    } else {
                        return "";
                    }
                default:
                    throw new IllegalArgumentException("undefined collum");
            }
        }
    }

    private void refreshLists() {
        agents.clear();
        for (Agent agent : agentManager.getAllAgents()) {
            agents.add(agent);
        }
        agentTable.repaint();

        missions.clear();
        for (Mission mission : missionManager.getAllMissions()) {
            missions.add(mission);
        }
        missionTable.repaint();

        contracts.clear();
        for (Contract contract : contractManager.findAllContracts()) {
            contracts.add(contract);
        }
        contractTable.repaint();
    }
}