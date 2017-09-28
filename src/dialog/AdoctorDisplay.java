package dialog;

import com.intellij.openapi.project.Project;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.table.JBTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import process.RunAndroidSmellDetection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        tables.put(AdoctorCategory.Result, smellTable);
        JTable logTable = new JBTable();
        tables.put(AdoctorCategory.Log,logTable);
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabbedPane.add("Result aDoctor", ScrollPaneFactory.createScrollPane(smellTable));
        tabbedPane.add("Log aDoctor", ScrollPaneFactory.createScrollPane(textArea));
    }

    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    public void getTable()
    {
        smellTable = new javax.swing.JTable();
        smellTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                RunAndroidSmellDetection.FILE_HEADER
        ) {
            Class[] types = TABLE_TYPES;

            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }


        });
        smellTable.setDefaultEditor(Object.class, null);

        smellTable.setColumnSelectionAllowed(true);
        smellTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (smellTable.getColumnModel().getColumnCount() > 0) {
            smellTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }

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
}
