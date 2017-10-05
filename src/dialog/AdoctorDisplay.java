package dialog;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.table.JBTable;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import process.RunAndroidSmellDetection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AdoctorDisplay {

    private final Map<AdoctorCategory, JTable> tables = new EnumMap<AdoctorCategory, JTable>(AdoctorCategory.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private Project project;
    private javax.swing.JTable smellTable;
    private String csvFile;
    private JTextArea textArea;
    private JButton clean;
    private JPanel panel_first;
    private JTextField search = new JTextField();
    private JButton button_search;

    private Class[] TABLE_TYPES = {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
    };

    public AdoctorDisplay(@NotNull Project project, JTextArea area) {
        this.project = project;
        this.textArea = area;

        getTable();

        panel_first = new JPanel(new GridLayoutManager(3,4));
        button_search = new JButton("Filter Results", new ImageIcon(getClass().getResource("/filter.png")));
        button_search.setToolTipText("Filter Results.");
        button_search.addActionListener(evt -> filterResultsActionPerformed());
        clean = new JButton("Clean Results", new ImageIcon(getClass().getResource("/clean.png")));
        clean.setToolTipText("Clean.");
        clean.addActionListener(evt -> cleanResultsActionPerformed());
        panel_first.add(search, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel_first.add(button_search, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(clean, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        search.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    filterResultsActionPerformed();
                }
            }
        });

        tables.put(AdoctorCategory.Result, smellTable);
        JTable logTable = new JBTable();
        tables.put(AdoctorCategory.Log,logTable);
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        panel_first.add(smellTable.getTableHeader(), new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(smellTable, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        tabbedPane.add("Result aDoctor", ScrollPaneFactory.createScrollPane(panel_first));
        tabbedPane.add("Log aDoctor", ScrollPaneFactory.createScrollPane(textArea));
    }

    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    public void getTable()
    {
        smellTable = new javax.swing.JTable();
        smellTable.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (smellTable.getSelectedColumn()==0) {
                    String name_class = smellTable.getValueAt(smellTable.getSelectedRow(), smellTable.getSelectedColumn()).toString();
                    String name_class_new = name_class.replace(".", "/");
                    PsiManager psiManager = PsiManager.getInstance(project);
                    String class_to_open = psiManager.findDirectory(project.getBaseDir()).getVirtualFile().getPath()+"\\app\\src\\main\\java\\"+name_class_new + ".java";
                    System.out.println(class_to_open);
                    if (LocalFileSystem.getInstance().findFileByPath(class_to_open)!=null)
                    {
                        FileEditorManager.getInstance(project).openFile(LocalFileSystem.getInstance().findFileByPath(class_to_open), true, true);
                    }
                   else
                    {
                        Notifications.Bus.notify(new Notification("Invalid", "Error", "Cannot open selected class. Try another one from the 'src' directory.", NotificationType.ERROR));
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });

                smellTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object[][]{

                        },
                        RunAndroidSmellDetection.FILE_HEADER
                ) {
                    Class[] types = TABLE_TYPES;

                    boolean[] canEdit = new boolean[]{
                            false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }


                });
        smellTable.setDefaultEditor(Object.class, null);

        smellTable.setColumnSelectionAllowed(true);
        smellTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        if (smellTable.getColumnModel().getColumnCount() > 0) {
            smellTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }
        if (smellTable.getColumnModel().getColumnCount() > 1)
            smellTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 2)
            smellTable.getColumnModel().getColumn(2).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 3)
            smellTable.getColumnModel().getColumn(3).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 4)
            smellTable.getColumnModel().getColumn(4).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 5)
            smellTable.getColumnModel().getColumn(5).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 6)
            smellTable.getColumnModel().getColumn(6).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 7)
            smellTable.getColumnModel().getColumn(7).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 8)
            smellTable.getColumnModel().getColumn(8).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 9)
            smellTable.getColumnModel().getColumn(9).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 10)
            smellTable.getColumnModel().getColumn(10).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 11)
            smellTable.getColumnModel().getColumn(11).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 12)
            smellTable.getColumnModel().getColumn(12).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 13)
            smellTable.getColumnModel().getColumn(13).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 14)
            smellTable.getColumnModel().getColumn(14).setPreferredWidth(10);
        if (smellTable.getColumnModel().getColumnCount() > 15)
            smellTable.getColumnModel().getColumn(15).setPreferredWidth(10);


        try {
            this.csvFile = RunAndroidSmellDetection.getFileOutput().getAbsolutePath();
            this.updateTable("");
        } catch (IOException ex) {
        }


    }

    private void updateTable(String nameFilter) throws IOException {
        List<SmellData> filteredDataList = this.filterData(nameFilter);

        DefaultTableModel model = (DefaultTableModel) smellTable.getModel();
        model.setRowCount(0);
        smellTable.setAutoCreateRowSorter(true);



        for (SmellData filteredData : filteredDataList) {
            Object[] rowData = new Object[filteredData.getValues().size() + 1];
            rowData[0] = filteredData.getClassName();
            for (int i = 0; i < filteredData.getValues().size(); i++) {
                rowData[i + 1] = filteredData.getValues().get(i);
            }
            model.addRow(rowData);
        }
    }

    private List<SmellData> filterData(String nameFilter) throws IOException {
        Reader in = new FileReader(this.csvFile);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        List<SmellData> smellDataList = new ArrayList<>();

        for (CSVRecord record : records) {
            String className = record.get("Class");
            List<String> values = new ArrayList<>();
            for (int i = 1; i < record.size(); i++) {
                values.add(record.get(i));
            }

            if (className.contains(nameFilter) || nameFilter.equals("")) {
                SmellData smellData = new SmellData(className, values);
                smellDataList.add(smellData);
            }
        }

        return smellDataList;
    }

    private void filterResultsActionPerformed() {
        try {
            updateTable(search.getText());
        } catch (IOException ex) {

        }

        panel_first = new JPanel(new GridLayoutManager(3,4));
        button_search = new JButton("Filter Results", new ImageIcon(getClass().getResource("/filter.png")));
        button_search.setToolTipText("Filter Results.");
        button_search.addActionListener(evt -> filterResultsActionPerformed());
        clean = new JButton("Clean Results", new ImageIcon(getClass().getResource("/clean.png")));
        clean.setToolTipText("Clean.");
        clean.addActionListener(evt -> cleanResultsActionPerformed());
        panel_first.add(search, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel_first.add(button_search, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(clean, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        search.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    filterResultsActionPerformed();
                }
            }
        });

        panel_first.add(smellTable.getTableHeader(), new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(smellTable, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        tabbedPane.removeAll();
        tabbedPane.add("Result aDoctor", ScrollPaneFactory.createScrollPane(panel_first));
        tabbedPane.add("Log aDoctor", ScrollPaneFactory.createScrollPane(this.textArea));
    }

    private void cleanResultsActionPerformed() {
        try {
            updateTable("");
            button_search.setText("");
        } catch (IOException ex) {

        }

        panel_first = new JPanel(new GridLayoutManager(3,4));
        button_search = new JButton("Filter Results", new ImageIcon(getClass().getResource("/filter.png")));
        button_search.setToolTipText("Filter Results.");
        button_search.addActionListener(evt -> filterResultsActionPerformed());
        clean = new JButton("Clean Results", new ImageIcon(getClass().getResource("/clean.png")));
        clean.setToolTipText("Clean.");
        clean.addActionListener(evt -> cleanResultsActionPerformed());
        panel_first.add(search, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel_first.add(button_search, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(clean, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        search.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    filterResultsActionPerformed();
                }
            }
        });

        panel_first.add(smellTable.getTableHeader(), new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel_first.add(smellTable, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        tabbedPane.removeAll();
        tabbedPane.add("Result aDoctor", ScrollPaneFactory.createScrollPane(panel_first));
        tabbedPane.add("Log aDoctor", ScrollPaneFactory.createScrollPane(this.textArea));
    }
}
