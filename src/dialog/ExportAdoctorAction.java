package dialog;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.io.FileUtils;
import process.RunAndroidSmellDetection;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

public class ExportAdoctorAction extends AnAction {

    public ExportAdoctorAction(ImageIcon icon)
    {
        super("Export CSV", "Download del CSV",icon);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileFilter() {

            public String getDescription() {
                return "CSV Documents (*.csv)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".csv");
                }
            }
        });
        chooser.showOpenDialog(null);
        File selectedFile = chooser.getSelectedFile();
        String fileName = selectedFile.getAbsolutePath();
        final String extension = ".csv";
        if (!fileName.endsWith(extension)) {
            fileName += extension;
        }
        File source = RunAndroidSmellDetection.getFileOutput();
        File dest = new File(fileName);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}