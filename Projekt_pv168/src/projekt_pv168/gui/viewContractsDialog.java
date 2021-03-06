/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import projekt_pv168.Agent;
import projekt_pv168.Contract;
import projekt_pv168.Mission;

/**
 *
 * @author Sebastián
 */
public class viewContractsDialog extends javax.swing.JDialog {

    private boolean isMission = true;
    private Mission ms;
    private Agent ag;

    /**
     * Creates new form viewContractsDialog
     */
    public viewContractsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public viewContractsDialog(java.awt.Frame parent, boolean modal, List<Contract> contracts) {
        this(parent, modal);
        if (contracts != null) {
            contractList.setModel(new ContractModel((contracts)));
        }
    }

    public viewContractsDialog(java.awt.Frame parent, boolean modal, List<Contract> contracts, Mission mission) {
        this(parent, modal,contracts);
        isMission = true;
        ms = mission;
        if (mission != null) {
            this.setTitle(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VIEW_CONTRACTS_MISSION"));
            itemNameLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("MISSION"));
            itemIDLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("MISSION_ID"));
            itemNameTextField.setText(mission.getName());
            itemIDTextField.setText(str(mission.getId()));
        }
    }

    public viewContractsDialog(java.awt.Frame parent, boolean modal, List<Contract> contracts, Agent agent) {
        this(parent, modal,contracts);
        isMission = false;
        ag = agent;
        if (agent != null) {
            this.setTitle(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VIEW_CONTRACTS_AGENT"));
            itemNameLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGENT"));
            itemIDLabel.setText(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGENT_ID"));
            itemNameTextField.setText(agent.getName());
            itemIDTextField.setText(str(agent.getId()));
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

        contractPanel = new javax.swing.JPanel();
        contractLabel = new javax.swing.JLabel();
        contracScrollPanel = new javax.swing.JScrollPane();
        contractList = new javax.swing.JList();
        viewContractButton = new javax.swing.JButton();
        contractInfoPanel = new javax.swing.JPanel();
        itemNameLabel = new javax.swing.JLabel();
        itemNameTextField = new javax.swing.JTextField();
        itemIDLabel = new javax.swing.JLabel();
        itemIDTextField = new javax.swing.JTextField();
        viewitemButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default"); // NOI18N
        setTitle(bundle.getString("VIEW_CONTRACTS")); // NOI18N

        contractPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        contractLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        contractLabel.setText(bundle.getString("CONTRACTS")); // NOI18N

        contractList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contractList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contractListMouseClicked(evt);
            }
        });
        contracScrollPanel.setViewportView(contractList);

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
                .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contracScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addGroup(contractPanelLayout.createSequentialGroup()
                        .addComponent(contractLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(viewContractButton)))
                .addContainerGap())
        );
        contractPanelLayout.setVerticalGroup(
            contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contractPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contractLabel)
                    .addComponent(viewContractButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contracScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        contractInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        itemNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        itemNameLabel.setText(bundle.getString("ITEM")); // NOI18N

        itemNameTextField.setEditable(false);

        itemIDLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        itemIDLabel.setText(bundle.getString("ITEM_ID")); // NOI18N

        itemIDTextField.setEditable(false);

        viewitemButton.setText(bundle.getString("VIEW")); // NOI18N
        viewitemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewitemButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contractInfoPanelLayout = new javax.swing.GroupLayout(contractInfoPanel);
        contractInfoPanel.setLayout(contractInfoPanelLayout);
        contractInfoPanelLayout.setHorizontalGroup(
            contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contractInfoPanelLayout.createSequentialGroup()
                        .addComponent(itemNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(viewitemButton))
                    .addComponent(itemNameTextField)
                    .addComponent(itemIDTextField)
                    .addGroup(contractInfoPanelLayout.createSequentialGroup()
                        .addComponent(itemIDLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        contractInfoPanelLayout.setVerticalGroup(
            contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contractInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contractInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemNameLabel)
                    .addComponent(viewitemButton))
                .addGap(5, 5, 5)
                .addComponent(itemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(itemIDLabel)
                .addGap(9, 9, 9)
                .addComponent(itemIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cancelButton.setText(bundle.getString("CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contractInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contractPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contractInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(contractPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void contractListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contractListMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            viewContractButtonActionPerformed(null);
        }
    }//GEN-LAST:event_contractListMouseClicked

    private void viewitemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewitemButtonActionPerformed
            if (isMission) {
                ViewMissionDialog dialog = new ViewMissionDialog((Frame) this.getParent(), false, ms);
                dialog.setVisible(true);
            } else {
                ViewAgentDialog dialog = new ViewAgentDialog((Frame) this.getParent(), false, ag);
                dialog.setVisible(true);
            }
    }//GEN-LAST:event_viewitemButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void viewContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewContractButtonActionPerformed
        if (contractList.getSelectedIndex() != -1) {
            Contract cn = ((ContractModel) contractList.getModel()).getContractAt(contractList.getSelectedIndex());
            ViewContractDialog dialog = new ViewContractDialog((Frame)this.getParent(), false, cn);
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_viewContractButtonActionPerformed

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
            java.util.logging.Logger.getLogger(viewContractsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewContractsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewContractsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewContractsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                viewContractsDialog dialog = new viewContractsDialog(new javax.swing.JFrame(), true);
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

    private class ContractModel extends AbstractListModel<String> {

        private List<Contract> contracts = new ArrayList<>();

        public ContractModel(List<Contract> contracts) {
            this.contracts = contracts;
        }

        @Override
        public int getSize() {
            return contracts.size();
        }

        @Override
        public String getElementAt(int index) {
            Contract cn = contracts.get(index);
            return java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ID_MISSION"), new Object[] {cn.getMission().getName(), cn.getMission().getId()})                    + java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("ID_AGENT"), new Object[] {cn.getAgent().getName(), cn.getAgent().getName()});
        }

        public Contract getContractAt(int index) {
            return contracts.get(index);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane contracScrollPanel;
    private javax.swing.JPanel contractInfoPanel;
    private javax.swing.JLabel contractLabel;
    private javax.swing.JList contractList;
    private javax.swing.JPanel contractPanel;
    private javax.swing.JLabel itemIDLabel;
    private javax.swing.JTextField itemIDTextField;
    private javax.swing.JLabel itemNameLabel;
    private javax.swing.JTextField itemNameTextField;
    private javax.swing.JButton viewContractButton;
    private javax.swing.JButton viewitemButton;
    // End of variables declaration//GEN-END:variables
}
