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
public class ViewMissionDialog extends javax.swing.JDialog {

    /**
     * Creates new form ViewMissionDialog
     */
    public ViewMissionDialog(java.awt.Frame parent, boolean modal, Mission mission) {
        super(parent, modal);
        initComponents();
        if (mission != null) {
            missionNameTextField.setText(str(mission.getName()));
            missionIDTextField.setText(str(mission.getId()));
            locationTextField.setText(str(mission.getLocation()));
            difficultyTextField.setText(str(mission.getDifficulty()));
            detailsTextPane.setText(str(mission.getDetails()));
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

        missionDetailsPanel = new javax.swing.JPanel();
        detailsLabel = new javax.swing.JLabel();
        detailsScrollPane = new javax.swing.JScrollPane();
        detailsTextPane = new javax.swing.JTextPane();
        missionInfoPanel = new javax.swing.JPanel();
        missionNameLabel = new javax.swing.JLabel();
        missionNameTextField = new javax.swing.JTextField();
        difficultyLabel = new javax.swing.JLabel();
        locationLabel = new javax.swing.JLabel();
        locationTextField = new javax.swing.JTextField();
        difficultyTextField = new javax.swing.JTextField();
        missionIDTextField = new javax.swing.JTextField();
        missionIDLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("projekt_pv168/configuration/Locale"); // NOI18N
        setTitle(bundle.getString("VIEW_MISSION")); // NOI18N
        setMinimumSize(new java.awt.Dimension(389, 572));
        setModal(true);

        missionDetailsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        detailsLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        detailsLabel.setText(bundle.getString("DETAILS")); // NOI18N

        detailsTextPane.setEditable(false);
        detailsScrollPane.setViewportView(detailsTextPane);

        javax.swing.GroupLayout missionDetailsPanelLayout = new javax.swing.GroupLayout(missionDetailsPanel);
        missionDetailsPanel.setLayout(missionDetailsPanelLayout);
        missionDetailsPanelLayout.setHorizontalGroup(
            missionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailsScrollPane)
                    .addGroup(missionDetailsPanelLayout.createSequentialGroup()
                        .addComponent(detailsLabel)
                        .addGap(0, 277, Short.MAX_VALUE)))
                .addContainerGap())
        );
        missionDetailsPanelLayout.setVerticalGroup(
            missionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detailsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        missionInfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        missionNameLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionNameLabel.setText(bundle.getString("MISSION_NAME")); // NOI18N

        missionNameTextField.setEditable(false);

        difficultyLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        difficultyLabel.setText(bundle.getString("DIFFICULTY")); // NOI18N

        locationLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        locationLabel.setText(bundle.getString("LOCATION")); // NOI18N

        locationTextField.setEditable(false);

        difficultyTextField.setEditable(false);

        missionIDTextField.setEditable(false);

        missionIDLabel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        missionIDLabel.setText(bundle.getString("MISSION_ID")); // NOI18N

        javax.swing.GroupLayout missionInfoPanelLayout = new javax.swing.GroupLayout(missionInfoPanel);
        missionInfoPanel.setLayout(missionInfoPanelLayout);
        missionInfoPanelLayout.setHorizontalGroup(
            missionInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(missionNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(missionNameLabel)
                    .addComponent(difficultyLabel)
                    .addComponent(locationTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(locationLabel)
                    .addComponent(difficultyTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(missionIDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(missionIDLabel))
                .addContainerGap())
        );
        missionInfoPanelLayout.setVerticalGroup(
            missionInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(missionNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(missionIDLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(locationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(difficultyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(difficultyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(missionInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionDetailsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(missionInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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
            java.util.logging.Logger.getLogger(ViewMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMissionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewMissionDialog dialog = new ViewMissionDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JTextPane detailsTextPane;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JTextField difficultyTextField;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField locationTextField;
    private javax.swing.JPanel missionDetailsPanel;
    private javax.swing.JLabel missionIDLabel;
    private javax.swing.JTextField missionIDTextField;
    private javax.swing.JPanel missionInfoPanel;
    private javax.swing.JLabel missionNameLabel;
    private javax.swing.JTextField missionNameTextField;
    // End of variables declaration//GEN-END:variables
}
