/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.util.Calendar;
import projekt_pv168.Contract;
import java.util.ResourceBundle;

/**
 *
 * @author Sebastián
 */
public class ViewContractDialog extends javax.swing.JDialog {

    /**
     * Creates new form ViewContractDialog
     */
    public ViewContractDialog(java.awt.Frame parent, boolean modal, Contract contract) {
        super(parent, modal);
        initComponents();
        if (contract != null) {
            missionNameTextField.setText(str(contract.getMission().getName()));
            missionIDTextField.setText(str(contract.getMission().getId()));
            agentNameTextField.setText(str(contract.getAgent().getName()));
            agentIDTextField.setText(str(contract.getAgent().getId()));
            budgetTextField.setText(str(contract.getBudget()));
            startTextField.setText(str(contract.getStartTime()));
            endTextField.setText(str(contract.getEndTime()));
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

        contracTimePanel = new javax.swing.JPanel();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        startTextField = new javax.swing.JTextField();
        endTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        contractInfoPanel = new javax.swing.JPanel();
        missionNameLabel = new javax.swing.JLabel();
        agentNameLabel = new javax.swing.JLabel();
        missionNameTextField = new javax.swing.JTextField();
        agentNameTextField = new javax.swing.JTextField();
        missionIDLabel = new javax.swing.JLabel();
        missionIDTextField = new javax.swing.JTextField();
        agentIDTextField = new javax.swing.JTextField();
        agentIDLabel = new javax.swing.JLabel();
        contracBudgetPanel = new javax.swing.JPanel();
        budgetLabel = new javax.swing.JLabel();
        budgetTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Locale"); // NOI18N
        setTitle(bundle.getString("VIEW_CONTRACT")); // NOI18N
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(1007, 586));
        setMinimumSize(new java.awt.Dimension(352, 586));
        setModal(true);
        setResizable(false);

        contracTimePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contracTimePanel.setInheritsPopupMenu(true);

        startLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        startLabel.setText(bundle.getString("START_TIME")); // NOI18N

        endLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        endLabel.setText(bundle.getString("END_TIME")); // NOI18N

        startTextField.setEditable(false);

        endTextField.setEditable(false);

        javax.swing.GroupLayout contracTimePanelLayout = new javax.swing.GroupLayout(contracTimePanel);
        contracTimePanel.setLayout(contracTimePanelLayout);
        contracTimePanelLayout.setHorizontalGroup(
            contracTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contracTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contracTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startLabel)
                    .addComponent(endLabel)
                    .addComponent(startTextField)
                    .addComponent(endTextField))
                .addGap(13, 13, 13))
        );
        contracTimePanelLayout.setVerticalGroup(
            contracTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contracTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(endLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cancelButton.setText(bundle.getString("CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        contractInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionNameLabel.setText(bundle.getString("MISSION")); // NOI18N

        agentNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentNameLabel.setText(bundle.getString("AGENT ")); // NOI18N

        missionNameTextField.setEditable(false);

        agentNameTextField.setEditable(false);

        missionIDLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionIDLabel.setText(bundle.getString("MISSION_ID")); // NOI18N

        missionIDTextField.setEditable(false);

        agentIDTextField.setEditable(false);

        agentIDLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentIDLabel.setText(bundle.getString("AGENT_ID")); // NOI18N

        javax.swing.GroupLayout contractInfoPanelLayout = new javax.swing.GroupLayout(contractInfoPanel);
        contractInfoPanel.setLayout(contractInfoPanelLayout);
        contractInfoPanelLayout.setHorizontalGroup(
            contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(missionNameLabel)
                    .addComponent(missionNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(missionIDLabel)
                    .addComponent(missionIDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(agentNameLabel)
                    .addComponent(agentNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(agentIDLabel)
                    .addComponent(agentIDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        contractInfoPanelLayout.setVerticalGroup(
            contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(missionNameLabel)
                .addGap(9, 9, 9)
                .addComponent(missionNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(missionIDLabel)
                .addGap(9, 9, 9)
                .addComponent(missionIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(agentNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(agentIDLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contracBudgetPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        budgetLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        budgetLabel.setText(bundle.getString("CONTRACT_BUDGET")); // NOI18N

        budgetTextField.setEditable(false);

        javax.swing.GroupLayout contracBudgetPanelLayout = new javax.swing.GroupLayout(contracBudgetPanel);
        contracBudgetPanel.setLayout(contracBudgetPanelLayout);
        contracBudgetPanelLayout.setHorizontalGroup(
            contracBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contracBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contracBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(budgetLabel)
                    .addComponent(budgetTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        contracBudgetPanelLayout.setVerticalGroup(
            contracBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contracBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(budgetLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(budgetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contracTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contracBudgetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(contractInfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contractInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contracBudgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(contracTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ViewContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewContractDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewContractDialog dialog = new ViewContractDialog(new javax.swing.JFrame(), true, null);
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
    
    private String str(Calendar cal) {
        if (cal == null) return "";
        return cal.getTime().toString();
    }
    
    private String str(int val) {
        return String.valueOf(val);
    }
    
    private String str(long val) {
        return String.valueOf(val);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel agentIDLabel;
    private javax.swing.JTextField agentIDTextField;
    private javax.swing.JLabel agentNameLabel;
    private javax.swing.JTextField agentNameTextField;
    private javax.swing.JLabel budgetLabel;
    private javax.swing.JTextField budgetTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel contracBudgetPanel;
    private javax.swing.JPanel contracTimePanel;
    private javax.swing.JPanel contractInfoPanel;
    private javax.swing.JLabel endLabel;
    private javax.swing.JTextField endTextField;
    private javax.swing.JLabel missionIDLabel;
    private javax.swing.JTextField missionIDTextField;
    private javax.swing.JLabel missionNameLabel;
    private javax.swing.JTextField missionNameTextField;
    private javax.swing.JLabel startLabel;
    private javax.swing.JTextField startTextField;
    // End of variables declaration//GEN-END:variables
}
