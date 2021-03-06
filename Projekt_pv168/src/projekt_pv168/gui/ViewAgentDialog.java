/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import projekt_pv168.Agent;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 *
 * @author Sebastián
 */
public class ViewAgentDialog extends javax.swing.JDialog {
    private static final String PROJEKT_LOCALE = "projekt_pv168/configuration/Default";
    private Agent agent;

    /**
     * Creates new form ViewAgentDialog
     */
    public ViewAgentDialog(java.awt.Frame parent, boolean modal, Agent agent) {
        super(parent, modal);
        initComponents();
        this.agent = agent;
        if (agent != null) {
            agentNameTextField.setText(str(agent.getName()));
            agentIDTextField.setText(NumberFormat.getNumberInstance().format(agent.getId()));
            agentBornTextField.setText(getLocalDate(Locale.getDefault(), TimeZone.getDefault(),agent.getBorn()));
            rankTextField.setText(NumberFormat.getNumberInstance().format(agent.getRank()));
            if (agent.isActive()) {
                ActiveTextField.setText(ResourceBundle.getBundle(PROJEKT_LOCALE).getString("YES"));
            } else {
                ActiveTextField.setText(ResourceBundle.getBundle(PROJEKT_LOCALE).getString("NO"));
            }
            notesTextPane.setText(str(agent.getNotes()));
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

        agentNotesPanel = new javax.swing.JPanel();
        notesLabel = new javax.swing.JLabel();
        notesScrollPane = new javax.swing.JScrollPane();
        notesTextPane = new javax.swing.JTextPane();
        agentInfoPanel = new javax.swing.JPanel();
        agentNameLabel = new javax.swing.JLabel();
        agentNameTextField = new javax.swing.JTextField();
        agentIDLabel = new javax.swing.JLabel();
        agentIDTextField = new javax.swing.JTextField();
        ActiveTextField = new javax.swing.JTextField();
        rankLabel = new javax.swing.JLabel();
        rankTextField = new javax.swing.JTextField();
        activeLabel = new javax.swing.JLabel();
        bornPanel = new javax.swing.JPanel();
        bornLabel = new javax.swing.JLabel();
        agentBornTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        contractButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default"); // NOI18N
        setTitle(bundle.getString("VIEW_AGENT")); // NOI18N
        setMinimumSize(new java.awt.Dimension(389, 603));

        agentNotesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        notesLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        notesLabel.setText(bundle.getString("NOTES")); // NOI18N

        notesTextPane.setEditable(false);
        notesScrollPane.setViewportView(notesTextPane);

        javax.swing.GroupLayout agentNotesPanelLayout = new javax.swing.GroupLayout(agentNotesPanel);
        agentNotesPanel.setLayout(agentNotesPanelLayout);
        agentNotesPanelLayout.setHorizontalGroup(
            agentNotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentNotesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentNotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notesScrollPane)
                    .addGroup(agentNotesPanelLayout.createSequentialGroup()
                        .addComponent(notesLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        agentNotesPanelLayout.setVerticalGroup(
            agentNotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, agentNotesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );

        agentInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agentNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentNameLabel.setText(bundle.getString("AGENT_NAME")); // NOI18N

        agentNameTextField.setEditable(false);

        agentIDLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentIDLabel.setText(bundle.getString("AGENT_ID")); // NOI18N

        agentIDTextField.setEditable(false);

        ActiveTextField.setEditable(false);

        rankLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rankLabel.setText(bundle.getString("RANK")); // NOI18N

        rankTextField.setEditable(false);

        activeLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        activeLabel.setText(bundle.getString("ACTIVE")); // NOI18N

        javax.swing.GroupLayout agentInfoPanelLayout = new javax.swing.GroupLayout(agentInfoPanel);
        agentInfoPanel.setLayout(agentInfoPanelLayout);
        agentInfoPanelLayout.setHorizontalGroup(
            agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(agentInfoPanelLayout.createSequentialGroup()
                        .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rankTextField)
                            .addComponent(rankLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(activeLabel)
                            .addComponent(ActiveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(agentIDTextField)
                    .addComponent(agentNameTextField)
                    .addGroup(agentInfoPanelLayout.createSequentialGroup()
                        .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(agentIDLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(agentNameLabel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        agentInfoPanelLayout.setVerticalGroup(
            agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agentNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(agentIDLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(activeLabel)
                    .addComponent(rankLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(agentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rankTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActiveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bornPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bornLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        bornLabel.setText(bundle.getString("BORN")); // NOI18N

        agentBornTextField.setEditable(false);

        javax.swing.GroupLayout bornPanelLayout = new javax.swing.GroupLayout(bornPanel);
        bornPanel.setLayout(bornPanelLayout);
        bornPanelLayout.setHorizontalGroup(
            bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bornPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bornLabel)
                    .addComponent(agentBornTextField))
                .addContainerGap())
        );
        bornPanelLayout.setVerticalGroup(
            bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bornPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bornLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentBornTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        cancelButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cancelButton.setText(bundle.getString("CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        contractButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        contractButton.setText(bundle.getString("CONTRACTS")); // NOI18N
        contractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(agentInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bornPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agentNotesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contractButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentNotesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bornPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(contractButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void contractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractButtonActionPerformed
        ((AgencyManagerFrame)this.getParent()).viewDialog(agent);
    }//GEN-LAST:event_contractButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ViewAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewAgentDialog dialog = new ViewAgentDialog(new javax.swing.JFrame(), false, null);
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
    
    private String getLocalDate(Locale locale, TimeZone tz, Calendar cal) {
        if (cal == null)
            return "";
        Date date = cal.getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        df.setTimeZone(tz);
        return df.format(date);
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
    private javax.swing.JTextField ActiveTextField;
    private javax.swing.JLabel activeLabel;
    private javax.swing.JTextField agentBornTextField;
    private javax.swing.JLabel agentIDLabel;
    private javax.swing.JTextField agentIDTextField;
    private javax.swing.JPanel agentInfoPanel;
    private javax.swing.JLabel agentNameLabel;
    private javax.swing.JTextField agentNameTextField;
    private javax.swing.JPanel agentNotesPanel;
    private javax.swing.JLabel bornLabel;
    private javax.swing.JPanel bornPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton contractButton;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JScrollPane notesScrollPane;
    private javax.swing.JTextPane notesTextPane;
    private javax.swing.JLabel rankLabel;
    private javax.swing.JTextField rankTextField;
    // End of variables declaration//GEN-END:variables
}
