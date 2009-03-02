/*
 * WizardFirstUse.java
 *
 * Created on 1 mars 2009, 01:14:54
 */

package rofage.wizards;

import java.awt.CardLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.toolkits.PropertiesToolkit;

/**
 *
 * @author Pierre Chastagner
 */
public class WizardFirstUse extends javax.swing.JDialog {
    private final static Log logger = LogFactory.getLog(WizardFirstUse.class);
    private final static int LAST_PAGE = 4;
    private final static String SUMMARY_START   = "Please review this settings before clicking on Finish.\n\nYou will always be able to change those settings in the main configuration window, but you will may have to manually move your files (images, icones, etc.) accordingly.\n\n";
    private final static String SUMMARY_FOLDER  = "# Folders configuration\n";
    private final static String SUMMARY_DAT     = "# Import of the first dat\n";
    private static String [] tabDatNames;
    private static List<String> listErrors = new ArrayList<String>();

    private int currentPage = 1;
    private String[] tabSummary = new String[LAST_PAGE-1];

    static {
        // We populate the listDatNames from the properties file
        String rawDatNames = PropertiesToolkit.getProperty("saved_dats_names");
        tabDatNames = rawDatNames.split(";:;");
    }

    /** Creates new form WizardFirstUse */
    public WizardFirstUse(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void showErrors () {
        StringBuffer strBuff = new StringBuffer();
        for (int i=0; i<listErrors.size(); i++) {
            strBuff.append("- ")
                    .append(listErrors.get(i))
                    .append("\n");
        }
        txPaneErrors.setText(strBuff.toString());
        txPaneErrors.setVisible(true);
    }
    
    private void hideErrors () {
        txPaneErrors.setVisible(false);
    }

    /**
     * Validates the current page
     * @return boolean : true if the form is valid, false otherwise
     */
    private boolean validateForm() {
        logger.debug("Validating page "+currentPage);
        listErrors.clear();
        switch (currentPage) {
            case 2 :
                File file = new File(fldImageFolder.getText());
                if (!file.isDirectory()) {
                    logger.debug("The path provided for the image folder is not valid.");
                    listErrors.add("The path provided for the image folder is not valid.");
                }
                file = new File(fldIconsFolder.getText());
                if (!file.isDirectory()) {
                    logger.debug("The path provided for the icons folder is not valid.");
                    listErrors.add("The path provided for the icons folder is not valid.");
                }
                file = new File(fldNfoFolder.getText());
                if (!file.isDirectory()) {
                    logger.debug("The path provided for the nfo folder is not valid.");
                    listErrors.add("The path provided for the nfo folder is not valid.");
                }
                break;
            case 3 :
                if (rBtnLoadDat.isSelected()) {
                    File datFile = new File (fldDatPath.getText());
                    if (!datFile.isFile()) {
                        logger.debug("The path provided for the dat file is not valid.");
                        listErrors.add("The path provided for the dat file is not valid");
                    }
                }
        }
        return listErrors.isEmpty();
    }

    /**
     * Updates the summary with the correct information.
     * The page which has just been validated is needed to update the summary correctly.
     * The summaryTable is a String[LAST_PAGE] which means if the user has performed a back
     * action, the new summary text will override the previous one
     */
    private void updateSummary () {
        StringBuffer strBuffStep = new StringBuffer();
        switch (currentPage) {
            case 1 : strBuffStep.append(SUMMARY_START);
                break;
            case 2 : strBuffStep.append(SUMMARY_FOLDER);
                for (int i=0; i<SUMMARY_FOLDER.length(); i++) {
                    strBuffStep.append("-");
                }
                strBuffStep.append("\n");

                strBuffStep.append("Images Folder : ")
                        .append(fldImageFolder.getText())
                        .append("\n");
                strBuffStep.append("Icons Folder : ")
                        .append(fldIconsFolder.getText())
                        .append("\n");
                strBuffStep.append("Nfo Folder : ")
                        .append(fldNfoFolder.getText())
                        .append("\n");
                strBuffStep.append("\n");
                break;
            case 3 : strBuffStep.append(SUMMARY_DAT);
                for (int i=0; i<SUMMARY_DAT.length(); i++) {
                    strBuffStep.append("-");
                }
                strBuffStep.append("\n");

                if (rBtnLoadDat.isSelected()) {
                    strBuffStep.append("This dat will be loaded : ")
                            .append(fldDatPath.getText());
                } else {
                    // It means we download the dat
                    strBuffStep.append("This dat will be downloaded : ")
                            .append(cmbDats.getSelectedItem());
                }
                strBuffStep.append("\n");
                break;
        }
        tabSummary[currentPage-1] = strBuffStep.toString();

        // We now update the summary text area if needed
        if (currentPage==LAST_PAGE-1) {
            StringBuffer strBuff = new StringBuffer();
            for (int i=0; i<tabSummary.length; i++) {
                strBuff.append(tabSummary[i]);
            }
            txPaneSummary.setText(strBuff.toString());
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSeparator1 = new javax.swing.JSeparator();
        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlMain = new javax.swing.JPanel();
        pnlWelcome = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        pnlSelectDat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rBtnLoadDat = new javax.swing.JRadioButton();
        rBtnDlDat = new javax.swing.JRadioButton();
        fldDatPath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cmbDats = new javax.swing.JComboBox();
        pnlFoldersConf = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fldImageFolder = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        fldIconsFolder = new javax.swing.JTextField();
        fldNfoFolder = new javax.swing.JTextField();
        pnlSummary = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txPaneSummary = new javax.swing.JTextPane();
        pnlErrors = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txPaneErrors = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("First Use Wizard");
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(400, 300));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        pnlMain.setLayout(new java.awt.CardLayout());

        jScrollPane1.setBorder(null);

        jTextArea1.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Welcome to RoFage !\n\nRoFage stands for ROm manager For Any Good Environment. And you're just a few steps away from using it !\n\nRoFage will help you manage your rom collections with the help of dat files available on the internet. Dat files (OL compatible) exist for a lot of system, so you should be able to manage all your collections with RoFage.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setText("Welcome !");

        javax.swing.GroupLayout pnlWelcomeLayout = new javax.swing.GroupLayout(pnlWelcome);
        pnlWelcome.setLayout(pnlWelcomeLayout);
        pnlWelcomeLayout.setHorizontalGroup(
            pnlWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlWelcomeLayout.setVerticalGroup(
            pnlWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMain.add(pnlWelcome, "1");

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 14));
        jLabel2.setText("Loading your first dat");

        buttonGroup1.add(rBtnLoadDat);
        rBtnLoadDat.setSelected(true);
        rBtnLoadDat.setText("Load a dat file (.dat) from the file system");
        rBtnLoadDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnLoadDatActionPerformed(evt);
            }
        });

        buttonGroup1.add(rBtnDlDat);
        rBtnDlDat.setText("Automatically download this dat");

        jButton1.setText("Browse...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cmbDats.setModel(new javax.swing.DefaultComboBoxModel(tabDatNames));

        javax.swing.GroupLayout pnlSelectDatLayout = new javax.swing.GroupLayout(pnlSelectDat);
        pnlSelectDat.setLayout(pnlSelectDatLayout);
        pnlSelectDatLayout.setHorizontalGroup(
            pnlSelectDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectDatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSelectDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSelectDatLayout.createSequentialGroup()
                        .addComponent(rBtnLoadDat)
                        .addGap(18, 18, 18)
                        .addComponent(fldDatPath, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel2)
                    .addGroup(pnlSelectDatLayout.createSequentialGroup()
                        .addComponent(rBtnDlDat)
                        .addGap(18, 18, 18)
                        .addComponent(cmbDats, 0, 279, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnlSelectDatLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rBtnDlDat, rBtnLoadDat});

        pnlSelectDatLayout.setVerticalGroup(
            pnlSelectDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectDatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(pnlSelectDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBtnLoadDat)
                    .addComponent(jButton1)
                    .addComponent(fldDatPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSelectDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rBtnDlDat)
                    .addComponent(cmbDats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(195, Short.MAX_VALUE))
        );

        pnlMain.add(pnlSelectDat, "3");

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 14));
        jLabel3.setText("Folders configuration");

        jLabel5.setText("Images main folder");

        jLabel6.setText("Icons main folder");

        jLabel7.setText("Nfo main folder");

        jButton2.setText("Browse...");

        jButton3.setText("Browse...");

        jButton4.setText("Browse...");

        javax.swing.GroupLayout pnlFoldersConfLayout = new javax.swing.GroupLayout(pnlFoldersConf);
        pnlFoldersConf.setLayout(pnlFoldersConfLayout);
        pnlFoldersConfLayout.setHorizontalGroup(
            pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoldersConfLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(pnlFoldersConfLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(fldImageFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(pnlFoldersConfLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(fldIconsFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(pnlFoldersConfLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(fldNfoFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );

        pnlFoldersConfLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6, jLabel7});

        pnlFoldersConfLayout.setVerticalGroup(
            pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoldersConfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fldImageFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton3)
                    .addComponent(fldIconsFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFoldersConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton4)
                    .addComponent(fldNfoFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        pnlMain.add(pnlFoldersConf, "2");

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 14));
        jLabel4.setText("Summary");

        jScrollPane2.setBorder(null);

        txPaneSummary.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        txPaneSummary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txPaneSummary.setEditable(false);
        txPaneSummary.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jScrollPane2.setViewportView(txPaneSummary);

        javax.swing.GroupLayout pnlSummaryLayout = new javax.swing.GroupLayout(pnlSummary);
        pnlSummary.setLayout(pnlSummaryLayout);
        pnlSummaryLayout.setHorizontalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(452, Short.MAX_VALUE))
            .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlSummaryLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlSummaryLayout.setVerticalGroup(
            pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(269, Short.MAX_VALUE))
            .addGroup(pnlSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSummaryLayout.createSequentialGroup()
                    .addContainerGap(37, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlMain.add(pnlSummary, "4");

        pnlMain.setBounds(0, 0, 540, 301);
        jLayeredPane1.add(pnlMain, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane3.setBorder(null);

        txPaneErrors.setBackground(javax.swing.UIManager.getDefaults().getColor("ComboBox.disabledBackground"));
        txPaneErrors.setBorder(new javax.swing.border.LineBorder(java.awt.Color.red, 1, true));
        txPaneErrors.setEditable(false);
        txPaneErrors.setForeground(java.awt.Color.red);
        jScrollPane3.setViewportView(txPaneErrors);

        javax.swing.GroupLayout pnlErrorsLayout = new javax.swing.GroupLayout(pnlErrors);
        pnlErrors.setLayout(pnlErrorsLayout);
        pnlErrorsLayout.setHorizontalGroup(
            pnlErrorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(pnlErrorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlErrorsLayout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlErrorsLayout.setVerticalGroup(
            pnlErrorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
            .addGroup(pnlErrorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlErrorsLayout.createSequentialGroup()
                    .addContainerGap(162, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlErrors.setBounds(10, 40, 530, 260);
        jLayeredPane1.add(pnlErrors, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNext)
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBack, btnCancel, btnNext});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(307, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnBack)
                    .addComponent(btnCancel))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(55, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        hideErrors();
        currentPage--;
        if (currentPage==1) {
            // If it's the first page we disable the back button
            btnBack.setEnabled(false);
        }
        btnNext.setText("Next");

        CardLayout cl = (CardLayout) pnlMain.getLayout();
        cl.show(pnlMain, String.valueOf(currentPage));

}//GEN-LAST:event_btnBackActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(-1);
}//GEN-LAST:event_btnCancelActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (validateForm()) {
            updateSummary();
            currentPage++;
            btnBack.setEnabled(true);

            if (currentPage==LAST_PAGE) {
                // If it's the last page we show "Finish" instead of "Next"
                btnNext.setText("Finish");
            }

            CardLayout cl = (CardLayout) pnlMain.getLayout();
            cl.show(pnlMain, String.valueOf(currentPage));
        } else {
            // The form is not valid, we display the error message
            showErrors();
        }

    }//GEN-LAST:event_btnNextActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.exit(-1);
    }//GEN-LAST:event_formWindowClosed

    private void rBtnLoadDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBtnLoadDatActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_rBtnLoadDatActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WizardFirstUse dialog = new WizardFirstUse(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNext;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbDats;
    private javax.swing.JTextField fldDatPath;
    private javax.swing.JTextField fldIconsFolder;
    private javax.swing.JTextField fldImageFolder;
    private javax.swing.JTextField fldNfoFolder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pnlErrors;
    private javax.swing.JPanel pnlFoldersConf;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSelectDat;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JPanel pnlWelcome;
    private javax.swing.JRadioButton rBtnDlDat;
    private javax.swing.JRadioButton rBtnLoadDat;
    private javax.swing.JTextPane txPaneErrors;
    private javax.swing.JTextPane txPaneSummary;
    // End of variables declaration//GEN-END:variables

}
