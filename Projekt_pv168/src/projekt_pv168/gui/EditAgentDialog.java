/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import java.util.GregorianCalendar;
import projekt_pv168.Agent;
import java.util.ResourceBundle;
import java.text.MessageFormat;
import java.util.Calendar;

/**
 *
 * @author Sebastián
 */
public class EditAgentDialog extends javax.swing.JDialog {

    private Agent agent;
    private Long id;

    /**
     * Creates new form EditAgentDialog
     */
    public EditAgentDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public EditAgentDialog(java.awt.Frame parent, boolean modal, Agent agent) {
        this(parent, modal);
        if (agent != null) {
            agentNameTextField.setText(str(agent.getName()));
            bornCalendar.setCalendar(agent.getBorn());
            activeCheckBox.setSelected(agent.isActive());
            rankSpinner.setValue(agent.getRank());
            notesTextPane.setText(str(agent.getNotes()));
            id = agent.getId();
            this.setTitle(MessageFormat.format(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UPDATING_AGENT"), new Object[] {id}));
            addButton.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UPDATE"));
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

        notesPane = new javax.swing.JPanel();
        notesLabel = new javax.swing.JLabel();
        notesScrollPane = new javax.swing.JScrollPane();
        notesTextPane = new javax.swing.JTextPane();
        agentInfoPane = new javax.swing.JPanel();
        agentNameLabel = new javax.swing.JLabel();
        agentNameTextField = new javax.swing.JTextField();
        rankLabel = new javax.swing.JLabel();
        rankSpinner = new javax.swing.JSpinner();
        activeCheckBox = new javax.swing.JCheckBox();
        missingValueLabel = new javax.swing.JLabel();
        bornPanel = new javax.swing.JPanel();
        bornCalendar = new com.toedter.calendar.JCalendar();
        bornLabel = new javax.swing.JLabel();
        missingValueLabel1 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default"); // NOI18N
        setTitle(bundle.getString("ADDING_AGENT")); // NOI18N
        setMinimumSize(new java.awt.Dimension(397, 683));

        notesPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        notesLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        notesLabel.setText(bundle.getString("NOTES")); // NOI18N

        notesScrollPane.setViewportView(notesTextPane);

        javax.swing.GroupLayout notesPaneLayout = new javax.swing.GroupLayout(notesPane);
        notesPane.setLayout(notesPaneLayout);
        notesPaneLayout.setHorizontalGroup(
            notesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notesPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notesScrollPane)
                    .addGroup(notesPaneLayout.createSequentialGroup()
                        .addComponent(notesLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        notesPaneLayout.setVerticalGroup(
            notesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notesPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        agentInfoPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agentNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        agentNameLabel.setText(bundle.getString("AGENT_NAME_F")); // NOI18N

        rankLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rankLabel.setText(bundle.getString("RANK_F")); // NOI18N

        rankSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        rankSpinner.setName(""); // NOI18N

        activeCheckBox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        activeCheckBox.setText(bundle.getString("ACTIVE_F")); // NOI18N
        activeCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        missingValueLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        this.setVisible(false);
        missingValueLabel.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout agentInfoPaneLayout = new javax.swing.GroupLayout(agentInfoPane);
        agentInfoPane.setLayout(agentInfoPaneLayout);
        agentInfoPaneLayout.setHorizontalGroup(
            agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentInfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(agentInfoPaneLayout.createSequentialGroup()
                        .addGroup(agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(agentNameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rankLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, agentInfoPaneLayout.createSequentialGroup()
                                .addComponent(rankSpinner)
                                .addGap(34, 34, 34)
                                .addComponent(activeCheckBox)
                                .addGap(27, 27, 27)))
                        .addContainerGap())
                    .addGroup(agentInfoPaneLayout.createSequentialGroup()
                        .addComponent(agentNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(missingValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        agentInfoPaneLayout.setVerticalGroup(
            agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agentInfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(agentNameLabel)
                    .addComponent(missingValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(rankLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(agentInfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rankSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(activeCheckBox))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        bornPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bornLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        bornLabel.setText(bundle.getString("BORN_F")); // NOI18N

        missingValueLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missingValueLabel1.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout bornPanelLayout = new javax.swing.GroupLayout(bornPanel);
        bornPanel.setLayout(bornPanelLayout);
        bornPanelLayout.setHorizontalGroup(
            bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bornPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bornPanelLayout.createSequentialGroup()
                        .addComponent(bornCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bornPanelLayout.createSequentialGroup()
                        .addComponent(bornLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(missingValueLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        bornPanelLayout.setVerticalGroup(
            bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bornPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(bornPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bornLabel)
                    .addComponent(missingValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bornCalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        addButton.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        addButton.setText(bundle.getString("ADD")); // NOI18N
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

        warningLabel.setForeground(new java.awt.Color(102, 102, 102));
        warningLabel.setText(bundle.getString("VALUES_ERROR_F")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bornPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agentInfoPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notesPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(warningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(agentInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(notesPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bornPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (agentNameTextField.getText().equals("")) {
            missingValueLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VALUES_ERROR"));
            missingValueLabel1.setText("");
            agentNameTextField.requestFocus();
            return;
        }
        Calendar actualDate = Calendar.getInstance();
        int age = actualDate.get(Calendar.YEAR) - bornCalendar.getCalendar().get(Calendar.YEAR);
        if (actualDate.get(Calendar.DAY_OF_YEAR) <= bornCalendar.getCalendar().get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        if (new GregorianCalendar().getTimeInMillis() - bornCalendar.getCalendar().getTimeInMillis() <= 18 * 60 * 60 * 100) {
            missingValueLabel1.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("AGE_ERROR"));
            missingValueLabel.setText("");
            agentNameTextField.requestFocus();
            return;
        }
        agent = new Agent(id, agentNameTextField.getText(), bornCalendar.getCalendar(), activeCheckBox.isSelected(), (int) rankSpinner.getValue(), notesTextPane.getText());
        this.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    public Agent getAgent() {
        return agent;
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
            java.util.logging.Logger.getLogger(EditAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditAgentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditAgentDialog dialog = new EditAgentDialog(new javax.swing.JFrame(), true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JButton addButton;
    private javax.swing.JPanel agentInfoPane;
    private javax.swing.JLabel agentNameLabel;
    private javax.swing.JTextField agentNameTextField;
    private com.toedter.calendar.JCalendar bornCalendar;
    private javax.swing.JLabel bornLabel;
    private javax.swing.JPanel bornPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel missingValueLabel;
    private javax.swing.JLabel missingValueLabel1;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JPanel notesPane;
    private javax.swing.JScrollPane notesScrollPane;
    private javax.swing.JTextPane notesTextPane;
    private javax.swing.JLabel rankLabel;
    private javax.swing.JSpinner rankSpinner;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
