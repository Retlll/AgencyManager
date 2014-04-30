/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.util.List;
import projekt_pv168.Agent;
import projekt_pv168.Contract;
import projekt_pv168.Mission;

/**
 *
 * @author Sebastián
 */
public class EditContractDialog extends javax.swing.JDialog {
    
    private Contract contract;
    private Long missionID;
    private Long agentID;

    /**
     * Creates new form EditContractDialog
     */
    public EditContractDialog(java.awt.Frame parent, boolean modal, List<Mission> missions, List<Agent> agents) {
        super(parent, modal);
        initComponents();
    }
    
    public EditContractDialog(java.awt.Frame parent, boolean modal, Contract contract, List<Mission> missions, List<Agent> agents) {
        super(parent, modal);
        initComponents();
        if (contract != null) {
            budgetSplinner.setValue(contract.getBudget());
            starTimeBox.setSelected(contract.getStartTime() != null);
            if (starTimeBox.isSelected()) {
                startTimeCalendar.setEnabled(true);
                startTimeCalendar.setCalendar(contract.getStartTime());
            }
            endTimeBox.setSelected(contract.getEndTime() != null);
            if (endTimeBox.isSelected()) {
                endTimeCalendar.setEnabled(true);
                endTimeCalendar.setCalendar(contract.getEndTime());
            }
            addButton.setText("Update");
        }
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
        starTimeBox = new javax.swing.JCheckBox();
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

        setTitle("Adding a new Contract");
        setMinimumSize(new java.awt.Dimension(735, 697));

        agentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agentLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentLabel.setText("Agent*");

        agentList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
                .addComponent(agentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );

        startTimePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        startTimeCalendar.setEnabled(false);

        starTimeBox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        starTimeBox.setText("Start Time");
        starTimeBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        starTimeBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                starTimeBoxStateChanged(evt);
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
                        .addComponent(starTimeBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(startTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        startTimePanelLayout.setVerticalGroup(
            startTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(starTimeBox)
                .addGap(5, 5, 5)
                .addComponent(startTimeCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        addButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        addButton.setText("Add");
        addButton.setToolTipText("Adding a new Contract");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        missionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionLabel.setText("Mission*");

        missionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        endTimeBox.setText("End Time");
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
        budgetLabel.setText("Contract Budget*");

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
        warningLabel.setText("* - tieto hodnuty musia byť vyplnené");

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
                                .addComponent(endTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(agentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(budgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
        this.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void starTimeBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_starTimeBoxStateChanged
        startTimeCalendar.setEnabled(starTimeBox.isSelected());
    }//GEN-LAST:event_starTimeBoxStateChanged

    private void endTimeBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_endTimeBoxStateChanged
        endTimeCalendar.setEnabled(endTimeBox.isSelected());
    }//GEN-LAST:event_endTimeBoxStateChanged

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
                EditContractDialog dialog = new EditContractDialog(new javax.swing.JFrame(), true, null, null);
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
    
    private String str(Object object) {
        if (object == null) return "";
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox endTimeBox;
    private com.toedter.calendar.JCalendar endTimeCalendar;
    private javax.swing.JPanel endTimePanel;
    private javax.swing.JLabel missingAgentValueLabel;
    private javax.swing.JLabel missingMissionValueLabel;
    private javax.swing.JLabel missionLabel;
    private javax.swing.JList missionList;
    private javax.swing.JPanel missionPanel;
    private javax.swing.JScrollPane missionScrollPanel;
    private javax.swing.JCheckBox starTimeBox;
    private com.toedter.calendar.JCalendar startTimeCalendar;
    private javax.swing.JPanel startTimePanel;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
