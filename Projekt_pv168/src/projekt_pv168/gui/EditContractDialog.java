/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import projekt_pv168.Agent;
import projekt_pv168.Contract;
import projekt_pv168.ContractManager;
import projekt_pv168.Mission;
import java.util.ResourceBundle;

/**
 *
 * @author Sebastián
 */
public class EditContractDialog extends javax.swing.JDialog {

    private Contract contract;
    private ContractManager contractManager;

    /**
     * Creates new form EditContractDialog
     */
    public EditContractDialog(java.awt.Frame parent, boolean modal, List<Mission> missions, List<Agent> agents, ContractManager contractManager) {
        super(parent, modal);

        if (agents == null) {
            throw new IllegalArgumentException("agents cannot be null");
        }

        if (missions == null) {
            throw new IllegalArgumentException("missions cannot be null");
        }

        if (contractManager == null) {
            throw new IllegalArgumentException("contractManager cannot be null");
        }

        if (agents.isEmpty() || missions.isEmpty()) {
            this.setVisible(false);
        }

        initComponents();
        agentList.setModel(new AgentModel((agents)));
        missionList.setModel(new MissionModel((missions)));
        this.contractManager = contractManager;
    }

    public EditContractDialog(java.awt.Frame parent, boolean modal, List<Mission> missions, List<Agent> agents, ContractManager contractManager, Mission mission, Agent agent) {
        this(parent, modal, missions, agents, contractManager);
        selectInLists(missions, mission, agents, agent);
    }

    public EditContractDialog(java.awt.Frame parent, boolean modal, Contract contract) {
        super(parent, modal);

        if (contract.getAgent() == null) {
            throw new IllegalArgumentException("contract.getAgent cannot be null");
        }

        if (contract.getMission() == null) {
            throw new IllegalArgumentException("contract.getMission cannot be null");
        }

        initComponents();
        if (contract != null) {

            missionList.setModel(new MissionModel(contract.getMission()));
            agentList.setModel(new AgentModel(contract.getAgent()));
            missionList.setSelectedIndex(0);
            agentList.setSelectedIndex(0);

            budgetSplinner.setValue(contract.getBudget());
            startTimeBox.setSelected(contract.getStartTime() != null);
            if (startTimeBox.isSelected()) {
                startTimeCalendar.setEnabled(true);
                startTimeCalendar.setCalendar(contract.getStartTime());
            }
            endTimeBox.setSelected(contract.getEndTime() != null);
            if (endTimeBox.isSelected()) {
                endTimeCalendar.setEnabled(true);
                endTimeCalendar.setCalendar(contract.getEndTime());
            }
            this.setTitle(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("UPDATING_CONTRACT"));
            addButton.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("UPDATE"));
        }
    }

    public Contract getContract() {
        return contract;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        agentPanel = new javax.swing.JPanel();
        agentLabel = new javax.swing.JLabel();
        agentScrollPane = new javax.swing.JScrollPane();
        agentList = new javax.swing.JList();
        missingAgentValueLabel = new javax.swing.JLabel();
        startTimePanel = new javax.swing.JPanel();
        startTimeCalendar = new com.toedter.calendar.JCalendar();
        startTimeBox = new javax.swing.JCheckBox();
        addButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        missionPanel = new javax.swing.JPanel();
        missionLabel = new javax.swing.JLabel();
        missionScrollPanel = new javax.swing.JScrollPane();
        missionList = new javax.swing.JList();
        missingMissionValueLabel = new javax.swing.JLabel();
        endTimePanel = new javax.swing.JPanel();
        endTimeCalendar = new com.toedter.calendar.JCalendar();
        endTimeBox = new javax.swing.JCheckBox();
        budgetPanel = new javax.swing.JPanel();
        budgetLabel = new javax.swing.JLabel();
        budgetSplinner = new javax.swing.JSpinner();
        warningLabel = new javax.swing.JLabel();
        contractExistLabel = new javax.swing.JLabel();
        calenderProblemLabel = new javax.swing.JLabel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Locale"); // NOI18N
        setTitle(bundle.getString("ADDING_CONTRACT")); // NOI18N
        setMinimumSize(new java.awt.Dimension(735, 697));

        agentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agentLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentLabel.setText(bundle.getString("AGENT_F")); // NOI18N

        agentList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        agentList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                agentListFocusLost(evt);
            }
        });
        agentScrollPane.setViewportView(agentList);

        missingAgentValueLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missingAgentValueLabel.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout agentPanelLayout = new javax.swing.GroupLayout(agentPanel);
        agentPanel.setLayout(agentPanelLayout);
        agentPanelLayout.setHorizontalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(agentPanelLayout.createSequentialGroup()
                        .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(agentPanelLayout.createSequentialGroup()
                        .addComponent(agentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(missingAgentValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        agentPanelLayout.setVerticalGroup(
            agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(agentLabel)
                    .addComponent(missingAgentValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        startTimePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        startTimeCalendar.setEnabled(false);

        startTimeBox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        startTimeBox.setText(bundle.getString("START_TIME")); // NOI18N
        startTimeBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        startTimeBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                startTimeBoxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout startTimePanelLayout = new javax.swing.GroupLayout(startTimePanel);
        startTimePanel.setLayout(startTimePanelLayout);
        startTimePanelLayout.setHorizontalGroup(
            startTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(startTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(startTimePanelLayout.createSequentialGroup()
                        .addComponent(startTimeBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(startTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        startTimePanelLayout.setVerticalGroup(
            startTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startTimeBox)
                .addGap(5, 5, 5)
                .addComponent(startTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        addButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        addButton.setText(bundle.getString("ADD")); // NOI18N
        addButton.setToolTipText(bundle.getString("ADDING_CONTRACT")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cancelButton.setText(bundle.getString("CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        missionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionLabel.setText(bundle.getString("MISSION_F")); // NOI18N

        missionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        missionList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                missionListFocusLost(evt);
            }
        });
        missionScrollPanel.setViewportView(missionList);

        missingMissionValueLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missingMissionValueLabel.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout missionPanelLayout = new javax.swing.GroupLayout(missionPanel);
        missionPanel.setLayout(missionPanelLayout);
        missionPanelLayout.setHorizontalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(missionPanelLayout.createSequentialGroup()
                        .addComponent(missionScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(missionPanelLayout.createSequentialGroup()
                        .addComponent(missionLabel)
                        .addGap(4, 4, 4)
                        .addComponent(missingMissionValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        missionPanelLayout.setVerticalGroup(
            missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(missionLabel)
                    .addComponent(missingMissionValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionScrollPanel)
                .addContainerGap())
        );

        endTimePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        endTimeCalendar.setEnabled(false);

        endTimeBox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        endTimeBox.setText(bundle.getString("END_TIME")); // NOI18N
        endTimeBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        endTimeBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                endTimeBoxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout endTimePanelLayout = new javax.swing.GroupLayout(endTimePanel);
        endTimePanel.setLayout(endTimePanelLayout);
        endTimePanelLayout.setHorizontalGroup(
            endTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(endTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(endTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(endTimePanelLayout.createSequentialGroup()
                        .addComponent(endTimeBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(endTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );
        endTimePanelLayout.setVerticalGroup(
            endTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, endTimePanelLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(endTimeBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        budgetPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        budgetLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        budgetLabel.setText(bundle.getString("CONTRACT_BUDGET_F")); // NOI18N

        budgetSplinner.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(0L), Long.valueOf(0L), Long.valueOf(1000000000000000000L), Long.valueOf(1L)));
        budgetSplinner.setName(""); // NOI18N

        javax.swing.GroupLayout budgetPanelLayout = new javax.swing.GroupLayout(budgetPanel);
        budgetPanel.setLayout(budgetPanelLayout);
        budgetPanelLayout.setHorizontalGroup(
            budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(budgetLabel)
                    .addComponent(budgetSplinner, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        budgetPanelLayout.setVerticalGroup(
            budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(budgetLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(budgetSplinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        warningLabel.setForeground(new java.awt.Color(102, 102, 102));
        warningLabel.setText(bundle.getString("VALUES_ERROR")); // NOI18N

        contractExistLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        contractExistLabel.setForeground(new java.awt.Color(204, 0, 0));

        calenderProblemLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        calenderProblemLabel.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(warningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contractExistLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(budgetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(missionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(agentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(endTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calenderProblemLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(agentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contractExistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(budgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calenderProblemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(endTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGap(8, 8, 8)
                .addComponent(warningLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (missionList.getSelectedIndex() == -1) {
            missingMissionValueLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("VALUES_ERROR"));
            missingAgentValueLabel.setText("");
            calenderProblemLabel.setText("");
            contractExistLabel.setText("");
            missionList.requestFocus();
            return;
        }
        if (agentList.getSelectedIndex() == -1) {
            missingMissionValueLabel.setText("");
            missingAgentValueLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("VALUES_ERROR"));
            calenderProblemLabel.setText("");
            contractExistLabel.setText("");
            agentList.requestFocus();
            return;
        }

        Mission ms = (Mission) ((MissionModel) missionList.getModel()).getMissionAt(missionList.getSelectedIndex());
        Agent ag = (Agent) ((AgentModel) agentList.getModel()).getAgentAt(agentList.getSelectedIndex());

        if (contractManager != null && contractManager.getContract(ms, ag) != null) {
            missingMissionValueLabel.setText("");
            missingAgentValueLabel.setText("");
            calenderProblemLabel.setText("");
            contractExistLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("CONTRACT_EXIST"));
            missionList.requestFocus();
            return;
        }
        contract = new Contract();
        contract.setMission(ms);
        contract.setAgent(ag);
        if (startTimeBox.isSelected() && endTimeBox.isSelected()) {
            if (startTimeCalendar.getCalendar().compareTo(endTimeCalendar.getCalendar()) > 0) {
                missingMissionValueLabel.setText("");
                missingAgentValueLabel.setText("");
                calenderProblemLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Locale").getString("CALENDAR_ERROR"));
                contractExistLabel.setText("");
                startTimeCalendar.requestFocus();
                return;
            }
        }
        if (startTimeBox.isSelected()) {
            contract.setStartTime(startTimeCalendar.getCalendar());
        }
        if (endTimeBox.isSelected()) {
            contract.setEndTime(endTimeCalendar.getCalendar());
        }
        this.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void startTimeBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_startTimeBoxStateChanged
        startTimeCalendar.setEnabled(startTimeBox.isSelected());
    }//GEN-LAST:event_startTimeBoxStateChanged

    private void endTimeBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_endTimeBoxStateChanged
        endTimeCalendar.setEnabled(endTimeBox.isSelected());
    }//GEN-LAST:event_endTimeBoxStateChanged

    private void agentListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_agentListFocusLost
        if (contractManager == null) {
            agentList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_agentListFocusLost

    private void missionListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_missionListFocusLost
        if (contractManager == null) {
            missionList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_missionListFocusLost

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
            java.util.logging.Logger.getLogger(EditContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditContractDialog dialog = new EditContractDialog(new javax.swing.JFrame(), true, null, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void selectInLists(List<Mission> missions, Mission mission, List<Agent> agents, Agent agent) {
        if (mission != null) {
            for (int i = 0; i < missions.size(); i++) {
                if (missions.get(i).equals(mission)) {
                    missionList.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (agent != null) {
            for (int i = 0; i < agents.size(); i++) {
                if (agents.get(i).equals(agent)) {
                    agentList.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private class AgentModel extends AbstractListModel {

        private List<Agent> agents = new ArrayList<>();

        public AgentModel(List agents) {
            this.agents = agents;
        }

        public AgentModel(Agent agent) {
            this.agents = new ArrayList<>();
            this.agents.add(agent);
        }

        @Override
        public int getSize() {
            return agents.size();
        }

        @Override
        public Object getElementAt(int index) {
            Agent ag = agents.get(index);
            return ag.getName() + " (id: " + ag.getId() + " )";
        }

        public Agent getAgentAt(int index) {
            return agents.get(index);
        }
    }

    private class MissionModel extends AbstractListModel {

        private List<Mission> missions = new ArrayList<>();

        public MissionModel(List missions) {
            this.missions = missions;
        }

        public MissionModel(Mission mission) {
            this.missions = new ArrayList<>();
            this.missions.add(mission);
        }

        @Override
        public int getSize() {
            return missions.size();
        }

        @Override
        public Object getElementAt(int index) {
            Mission ms = missions.get(index);
            return ms.getName() + " (id: " + ms.getId() + " )";
        }

        public Mission getMissionAt(int index) {
            return missions.get(index);
        }
    }

    private String str(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    private String str(int val) {
        return String.valueOf(val);
    }

    private String str(long val) {
        return String.valueOf(val);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel agentLabel;
    private javax.swing.JList agentList;
    private javax.swing.JPanel agentPanel;
    private javax.swing.JScrollPane agentScrollPane;
    private javax.swing.JLabel budgetLabel;
    private javax.swing.JPanel budgetPanel;
    private javax.swing.JSpinner budgetSplinner;
    private javax.swing.JLabel calenderProblemLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel contractExistLabel;
    private javax.swing.JCheckBox endTimeBox;
    private com.toedter.calendar.JCalendar endTimeCalendar;
    private javax.swing.JPanel endTimePanel;
    private javax.swing.JLabel missingAgentValueLabel;
    private javax.swing.JLabel missingMissionValueLabel;
    private javax.swing.JLabel missionLabel;
    private javax.swing.JList missionList;
    private javax.swing.JPanel missionPanel;
    private javax.swing.JScrollPane missionScrollPanel;
    private javax.swing.JCheckBox startTimeBox;
    private com.toedter.calendar.JCalendar startTimeCalendar;
    private javax.swing.JPanel startTimePanel;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
