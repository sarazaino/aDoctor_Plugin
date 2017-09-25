package dialog;

import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import org.apache.batik.apps.svgbrowser.StatusBar;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.management.Notification;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class DialogUI extends DialogWrapper {

    private DialogUI myDialog;
    private javax.swing.JCheckBox DRCheck;
    private javax.swing.JCheckBox DTWCCheck;
    private javax.swing.JCheckBox DWCheck;
    private javax.swing.JCheckBox IDFPCheck;
    private javax.swing.JCheckBox IDSCheck;
    private javax.swing.JCheckBox IGSCheck;
    private javax.swing.JCheckBox ISQLQCheck;
    private javax.swing.JCheckBox LICCheck;
    private javax.swing.JCheckBox LTCheck;
    private javax.swing.JCheckBox MIMCheck;
    private javax.swing.JCheckBox NLMRCheck;
    private javax.swing.JCheckBox PDCheck;
    private javax.swing.JCheckBox RAMCheck;
    private javax.swing.JCheckBox SLCheck;
    private javax.swing.JCheckBox UCCheck;
    private javax.swing.JButton inputFolderButton;
    private javax.swing.JTextField inputFolderField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField outputFileField;
    private javax.swing.JButton outputFolderButton;
    private javax.swing.JButton startProcessButton;
    @NotNull private Project project;
    private JPanel panel;


    public DialogUI(Project project) {
        super(false);

        init();
        setTitle("aDoctor");
        this.project = project;
        myDialog = this;
    }

    @Nullable
    public JComponent createCenterPanel() {

        this.setResizable(false);

        jLabel1 = new javax.swing.JLabel();
        inputFolderField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        outputFileField = new javax.swing.JTextField();
        startProcessButton = new javax.swing.JButton();
        inputFolderButton = new javax.swing.JButton();
        outputFolderButton = new javax.swing.JButton();
        DTWCCheck = new javax.swing.JCheckBox();
        DRCheck = new javax.swing.JCheckBox();
        DWCheck = new javax.swing.JCheckBox();
        IDFPCheck = new javax.swing.JCheckBox();
        IDSCheck = new javax.swing.JCheckBox();
        ISQLQCheck = new javax.swing.JCheckBox();
        IGSCheck = new javax.swing.JCheckBox();
        LICCheck = new javax.swing.JCheckBox();
        LTCheck = new javax.swing.JCheckBox();
        MIMCheck = new javax.swing.JCheckBox();
        NLMRCheck = new javax.swing.JCheckBox();
        PDCheck = new javax.swing.JCheckBox();
        RAMCheck = new javax.swing.JCheckBox();
        SLCheck = new javax.swing.JCheckBox();
        UCCheck = new javax.swing.JCheckBox();
        panel = new JPanel();

        jLabel1.setText("Input Folder");

        inputFolderField.setEditable(false);
        inputFolderField.setToolTipText("Path of the Android SDK Platform Tools folder.");

        jLabel2.setText("Output File");

        outputFileField.setEditable(false);
        outputFileField.setToolTipText("Device power profile (see https://source.android.com/devices/tech/power/).");

        startProcessButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/play-button.png"))); // NOI18N
        startProcessButton.setText("Start Process");
        startProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startProcessButtonAction();
                ProgressManager.getInstance().run(new Task.Backgroundable(project, "Title"){
                    public void run(@NotNull ProgressIndicator progressIndicator) {

                        // start process

                        // Set the progress bar percentage and text
                        progressIndicator.setFraction(0.10);
                        progressIndicator.setText("90% to finish");

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 50% done
                        progressIndicator.setFraction(0.50);
                        progressIndicator.setText("50% to finish");

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Finished
                        progressIndicator.setFraction(1.0);
                        progressIndicator.setText("finished");

                        //Notifica nella status bar

                        Notifications.Bus.notify(new com.intellij.notification.Notification("event", "Success", "Successfully data analyzed by ''" + "aDoctor" + "'' plugin.", NotificationType.INFORMATION));
                        Notifications.Bus.notify(new com.intellij.notification.Notification("event", "Error", "Error in data analyzed by ''" + "aDoctor" + "'' plugin.", NotificationType.ERROR));
                        ApplicationManager.getApplication().invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                final AdoctorToolWindow toolWindow = AdoctorToolWindow.getInstance(project);
                                toolWindow.show();
                            }
                        });
                    }});

                myDialog.close(0);
            }

        });

        inputFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        inputFolderButton.setText("Open");
        inputFolderButton.setSize(new Dimension(50, 30));
        inputFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFolderButtonActionPerformed(evt);
            }
        });

        outputFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        outputFolderButton.setText("Open");
        outputFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputFolderButtonActionPerformed(evt);
            }
        });


        DTWCCheck.setSelected(true);
        DTWCCheck.setText("DTWC");
        DTWCCheck.setToolTipText("Data Transmission Without Compression");

        DRCheck.setSelected(true);
        DRCheck.setText("DR");
        DRCheck.setToolTipText("Debuggable Release");

        DWCheck.setSelected(true);
        DWCheck.setText("DW");
        DWCheck.setToolTipText("Durable Wakelock");

        IDFPCheck.setSelected(true);
        IDFPCheck.setText("IDFP");
        IDFPCheck.setToolTipText("Inefficient Data Format and Parser");

        IDSCheck.setSelected(true);
        IDSCheck.setText("IDS");
        IDSCheck.setToolTipText("Inefficient Data Structure");

        ISQLQCheck.setSelected(true);
        ISQLQCheck.setText("ISQLQ");
        ISQLQCheck.setToolTipText("Inefficient SQL Query");

        IGSCheck.setSelected(true);
        IGSCheck.setText("IGS");
        IGSCheck.setToolTipText("Internal Getter and Setter");

        LICCheck.setSelected(true);
        LICCheck.setText("LIC");
        LICCheck.setToolTipText("Leaking Inner Class");

        LTCheck.setSelected(true);
        LTCheck.setText("LT");
        LTCheck.setToolTipText("Leaking Thread");

        MIMCheck.setSelected(true);
        MIMCheck.setText("MIM");
        MIMCheck.setToolTipText("Member Ignoring Method");

        NLMRCheck.setSelected(true);
        NLMRCheck.setText("NLMR");
        NLMRCheck.setToolTipText("No Low Memory Resolver");

        PDCheck.setSelected(true);
        PDCheck.setText("PD");
        PDCheck.setToolTipText("Public Data");

        RAMCheck.setSelected(true);
        RAMCheck.setText("RAM");
        RAMCheck.setToolTipText("Rigid Alarm Manager");

        SLCheck.setSelected(true);
        SLCheck.setText("SL");
        SLCheck.setToolTipText("Slow Loop");

        UCCheck.setSelected(true);
        UCCheck.setText("UC");
        UCCheck.setToolTipText("Unclosed Closable");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(outputFileField)
                                                        .addComponent(inputFolderField))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(inputFolderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(outputFolderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(DTWCCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(DRCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(DWCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(ISQLQCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(IGSCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(LICCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(IDFPCheck, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(LTCheck, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(IDSCheck, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MIMCheck, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(NLMRCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(PDCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(RAMCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27)
                                                .addComponent(SLCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(UCCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(startProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(inputFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputFolderButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(outputFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(outputFolderButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(IDSCheck)
                                        .addComponent(IDFPCheck)
                                        .addComponent(DWCheck)
                                        .addComponent(DRCheck)
                                        .addComponent(DTWCCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(LTCheck)
                                                .addComponent(MIMCheck))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(ISQLQCheck)
                                                .addComponent(IGSCheck)
                                                .addComponent(LICCheck)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(SLCheck)
                                        .addComponent(UCCheck)
                                        .addComponent(RAMCheck)
                                        .addComponent(PDCheck)
                                        .addComponent(NLMRCheck))
                                .addGap(18, 18, 18)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        startProcessButton.getAccessibleContext().setAccessibleName("");

        getContentPane().setLayout(layout);


        return panel;
    }

    private void startProcessButtonAction() {
        String inputPath = this.inputFolderField.getText();
        String outputPath = this.outputFileField.getText();
        Integer[] smellTypesNeeded = new Integer[15];
        int numOfSmells = 0;
        if (DTWCCheck.isSelected()) {
            smellTypesNeeded[0] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[0] = 0;
        }
        if (DRCheck.isSelected()) {
            smellTypesNeeded[1] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[1] = 0;
        }
        if (DWCheck.isSelected()) {
            smellTypesNeeded[2] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[2] = 0;
        }
        if (IDFPCheck.isSelected()) {
            smellTypesNeeded[3] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[3] = 0;
        }
        if (IDSCheck.isSelected()) {
            smellTypesNeeded[4] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[4] = 0;
        }
        if (ISQLQCheck.isSelected()) {
            smellTypesNeeded[5] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[5] = 0;
        }
        if (IGSCheck.isSelected()) {
            smellTypesNeeded[6] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[6] = 0;
        }
        if (LICCheck.isSelected()) {
            smellTypesNeeded[7] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[7] = 0;
        }
        if (LTCheck.isSelected()) {
            smellTypesNeeded[8] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[8] = 0;
        }
        if (MIMCheck.isSelected()) {
            smellTypesNeeded[9] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[9] = 0;
        }
        if (NLMRCheck.isSelected()) {
            smellTypesNeeded[10] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[10] = 0;
        }
        if (PDCheck.isSelected()) {
            smellTypesNeeded[11] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[11] = 0;
        }
        if (RAMCheck.isSelected()) {
            smellTypesNeeded[12] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[12] = 0;
        }
        if (SLCheck.isSelected()) {
            smellTypesNeeded[13] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[13] = 0;
        }
        if (UCCheck.isSelected()) {
            smellTypesNeeded[14] = 1;
            numOfSmells++;
        } else {
            smellTypesNeeded[14] = 0;
        }
        String smellTypesString = StringUtils.join(smellTypesNeeded);
        boolean valid = true;
        if (inputPath.isEmpty()) {
            System.out.println("Input folder not selected.");
            valid = false;
        }
        if (outputPath.isEmpty()) {
            System.out.println("Output file not selected.");
            valid = false;
        }
        if (numOfSmells == 0) {
            System.out.println("None of the smells has been selected.");
            valid = false;
        }
        if (valid == false) {
            return;
        }

        String[] args = {inputPath, outputPath, smellTypesString};

/*        try {
            RunAndroidSmellDetection.main(args);
            viewResults.setEnabled(true);
        } catch (IOException | CoreException ex) {
            System.out.println("Errore!");
        }*/

    }

    private void inputFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFolderButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            inputFolderField.setText(filename);
        }
    }

    private void outputFolderButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            outputFileField.setText(filename);
        }
    }
}