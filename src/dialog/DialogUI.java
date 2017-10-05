package dialog;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import process.RunAndroidSmellDetection;
import javax.annotation.Nullable;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton startProcessButton;
    private javax.swing.JTextArea statusLabel;
    @NotNull private Project project;
    private JPanel panel;
    private boolean valid;


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
        jLabel2 = new javax.swing.JLabel();
        startProcessButton = new javax.swing.JButton();
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
        statusLabel = new javax.swing.JTextArea();
        valid = true;

        startProcessButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/play-button.png"))); // NOI18N
        startProcessButton.setText("Start Process");
        startProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                    ProgressManager.getInstance().run(new Task.Backgroundable(project, "Title") {
                        public void run(@NotNull ProgressIndicator progressIndicator) {

                            // start process
                            startProcessButtonAction(progressIndicator);

                            // Set the progress bar percentage and text
                            if (valid) {
                                ApplicationManager.getApplication().invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final AdoctorToolWindow toolWindow = AdoctorToolWindow.getInstance(project, statusLabel);
                                        toolWindow.show();
                                        Notifications.Bus.notify(new Notification("Valid", "Success", "aDoctor execution ended successfully!", NotificationType.INFORMATION));
                                    }
                                });
                            }
                            else
                            {
                                Notifications.Bus.notify(new Notification("Valid", "Error", "Select at least one smell.", NotificationType.ERROR));
                            }
                        }
                    });

                myDialog.close(0);
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
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(DTWCCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(IDFPCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(IGSCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(MIMCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(RAMCheck)
                                        .addGap(90, 90, 90))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(DRCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(IDSCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(LICCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(NLMRCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(SLCheck)
                                        .addGap(90, 90, 90))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(DWCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(ISQLQCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(LTCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(PDCheck)
                                        .addGap(90, 90, 90)
                                        .addComponent(UCCheck)
                                        .addGap(90, 90, 90)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(startProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(DTWCCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(DRCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(DWCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(IDFPCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(IDSCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(ISQLQCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(IGSCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(LICCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(LTCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(MIMCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(NLMRCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(PDCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(RAMCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(SLCheck)
                                        .addGap(41, 41, 41)
                                        .addComponent(UCCheck))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(startProcessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addContainerGap())
        );


        startProcessButton.getAccessibleContext().setAccessibleName("");

        PrintStream out = new PrintStream(new TextAreaOutputStream(statusLabel));
        System.setOut(out);
        getContentPane().setLayout(layout);

        return panel;
    }

    private void startProcessButtonAction(ProgressIndicator progressIndicator) {
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

        progressIndicator.setFraction(0.60);
        progressIndicator.setText("aDoctor is processing...");

        String smellTypesString = StringUtils.join(smellTypesNeeded);

        if (numOfSmells == 0) {
            System.out.println("None of the smells has been selected.");
            valid = false;
        }

        if (valid) {
            String[] args = {smellTypesString};

            try {
                RunAndroidSmellDetection runAndroidSmellDetection = new RunAndroidSmellDetection(project);
                runAndroidSmellDetection.main(args);

            } catch (IOException ex) {
                System.out.println("Errore!");
            }
        }

    }

}