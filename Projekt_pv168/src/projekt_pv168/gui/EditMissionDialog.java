/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168.gui;

import projekt_pv168.Mission;
import java.util.ResourceBundle;

/**
 *
 * @author Sebastián
 */
public class EditMissionDialog extends javax.swing.JDialog {
    
    private Mission mission;
    private Long id;

    /**
     * Creates new form EditMissionDialog
     */
    public EditMissionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public EditMissionDialog(java.awt.Frame parent, boolean modal, Mission mission) {
        this(parent, modal);
        if (mission != null) {
            missionNameTextField.setText(str(mission.getName()));
            locationTextField.setText(str(mission.getLocation()));
            difficultySpinner.setValue(mission.getDifficulty());
            detailsTextPane.setText(str(mission.getDetails()));
            addButton.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UPDATE"));
            id = mission.getId();
            this.setTitle(java.text.MessageFormat.format(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("UPDATING_MISSION"), new Object[] {id}));
        }
    }

    public Mission getMission() {
        return mission;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        missionsDetailPanel = new javax.swing.JPanel();
        detailsLabel = new javax.swing.JLabel();
        detailsScrollPane = new javax.swing.JScrollPane();
        detailsTextPane = new javax.swing.JTextPane();
        missionDataPanel = new javax.swing.JPanel();
        missionNameLabel = new javax.swing.JLabel();
        missionNameTextField = new javax.swing.JTextField();
        diffcultyLabel = new javax.swing.JLabel();
        difficultySpinner = new javax.swing.JSpinner();
        locationLabel = new javax.swing.JLabel();
        locationTextField = new javax.swing.JTextField();
        missingValueLabel = new javax.swing.JLabel();
        missingValueLabel1 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Default"); // NOI18N
        setTitle(bundle.getString("ADDING_MISSION")); // NOI18N
        setMinimumSize(new java.awt.Dimension(408, 540));

        missionsDetailPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        detailsLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        detailsLabel.setText(bundle.getString("DETAILS")); // NOI18N

        detailsScrollPane.setViewportView(detailsTextPane);

        javax.swing.GroupLayout missionsDetailPanelLayout = new javax.swing.GroupLayout(missionsDetailPanel);
        missionsDetailPanel.setLayout(missionsDetailPanelLayout);
        missionsDetailPanelLayout.setHorizontalGroup(
            missionsDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionsDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionsDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailsScrollPane)
                    .addGroup(missionsDetailPanelLayout.createSequentialGroup()
                        .addComponent(detailsLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        missionsDetailPanelLayout.setVerticalGroup(
            missionsDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionsDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detailsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        missionDataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionNameLabel.setText(bundle.getString("MISSION_NAME_F")); // NOI18N

        diffcultyLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        diffcultyLabel.setText(bundle.getString("DIFFICULTY_F")); // NOI18N

        difficultySpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        difficultySpinner.setName(""); // NOI18N

        locationLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        locationLabel.setText(bundle.getString("LOCATION_F")); // NOI18N

        missingValueLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missingValueLabel.setForeground(new java.awt.Color(204, 0, 0));

        missingValueLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missingValueLabel1.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout missionDataPanelLayout = new javax.swing.GroupLayout(missionDataPanel);
        missionDataPanel.setLayout(missionDataPanelLayout);
        missionDataPanelLayout.setHorizontalGroup(
            missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(missionDataPanelLayout.createSequentialGroup()
                        .addComponent(locationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(missingValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(missionDataPanelLayout.createSequentialGroup()
                        .addGroup(missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(missionNameTextField)
                            .addComponent(locationTextField))
                        .addContainerGap())
                    .addGroup(missionDataPanelLayout.createSequentialGroup()
                        .addGroup(missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(diffcultyLabel)
                            .addComponent(difficultySpinner))
                        .addGap(160, 160, 160))
                    .addGroup(missionDataPanelLayout.createSequentialGroup()
                        .addComponent(missionNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(missingValueLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        missionDataPanelLayout.setVerticalGroup(
            missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(missionNameLabel)
                    .addComponent(missingValueLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(missionDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locationLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(missingValueLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(diffcultyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(difficultySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(warningLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(missionsDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionDataPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(145, 145, 145)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(146, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(missionDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(missionsDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(261, 261, 261)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(262, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (missionNameTextField.getText().equals("")) {
            missingValueLabel1.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VALUES_ERROR"));
            missingValueLabel.setText("");
            missionNameTextField.requestFocus();
            return;
        }
        if (locationTextField.getText().equals("")) {
            missingValueLabel.setText(ResourceBundle.getBundle("projekt_pv168/configuration/Default").getString("VALUES_ERROR"));
            missingValueLabel1.setText("");
            locationTextField.requestFocus();
            return;
        }
        mission = new Mission(id, missionNameTextField.getText(), (int) difficultySpinner.getValue(), detailsTextPane.getText(), locationTextField.getText());       
        this.setVisible(false);
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
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
            java.util.logging.Logger.getLogger(EditMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditMissionDialog dialog = new EditMissionDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JTextPane detailsTextPane;
    private javax.swing.JLabel diffcultyLabel;
    private javax.swing.JSpinner difficultySpinner;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField locationTextField;
    private javax.swing.JLabel missingValueLabel;
    private javax.swing.JLabel missingValueLabel1;
    private javax.swing.JPanel missionDataPanel;
    private javax.swing.JLabel missionNameLabel;
    private javax.swing.JTextField missionNameTextField;
    private javax.swing.JPanel missionsDetailPanel;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
