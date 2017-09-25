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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import process.RunAndroidSmellDetection;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdoctorDisplay {

    private final Map<AdoctorCategory, JTable> tables = new EnumMap<AdoctorCategory, JTable>(AdoctorCategory.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private Project project;
    private javax.swing.JTable smellTable;
    private String csvFile;
    private String output_path;
    private JTextArea textArea;

    private Class[] TABLE_TYPES = {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
    };

    public AdoctorDisplay(@NotNull Project project, String path, JTextArea area) {
        this.project = project;
        this.output_path = path;
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

            /*public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }*/

        });
        smellTable.setDefaultEditor(Object.class, null);

        smellTable.setColumnSelectionAllowed(true);
        smellTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (smellTable.getColumnModel().getColumnCount() > 0) {
            smellTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        }

        try {
            this.csvFile = output_path;
            this.updateTable("");
        } catch (IOException ex) {
        }

        /*JTable table = new JTable();
        //ArrayList<SmellData> filteredDataList = this.filterData(nameFilter);

        JTableHeader header = table.getTableHeader();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentY(Component.CENTER_ALIGNMENT);
        header.setFont(new Font("Times",Font.BOLD,12));

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ClassName", "DTW", "DR", "DW", "IDFP", "IDS", "ISQL", "IGS", "LIC", "LT", "MIM", "NLMR", "PD", "RAM", "SL", "UC"}
        );

        ArrayList<SmellData> smellDataArrayList = new ArrayList<SmellData>();
        ArrayList<String> valori = new ArrayList<String>();
        valori.add("1");
        valori.add("2");
        valori.add("3");
        valori.add("4");
        valori.add("5");
        valori.add("6");
        valori.add("7");
        valori.add("8");
        valori.add("9");
        valori.add("10");
        valori.add("11");
        valori.add("12");
        valori.add("13");
        valori.add("14");
        valori.add("15");

        SmellData smellData = new SmellData("com.int",valori);
        smellDataArrayList.add(smellData);
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("dialog/Prova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));
        smellDataArrayList.add(new SmellData("com.provaprovaprovaprova",valori));

        for (SmellData smellData1 : smellDataArrayList) {
            model.addRow(new Object[]{smellData1.getClassName(), smellData1.getValues().get(0),smellData1.getValues().get(1), smellData1.getValues().get(2),
                    smellData1.getValues().get(3), smellData1.getValues().get(4), smellData1.getValues().get(5), smellData1.getValues().get(6),
                    smellData1.getValues().get(7), smellData1.getValues().get(8), smellData1.getValues().get(9), smellData1.getValues().get(10),
                    smellData1.getValues().get(11), smellData1.getValues().get(12), smellData1.getValues().get(13), smellData1.getValues().get(14),});
        }


        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Notifications.Bus.notify(new Notification("github", "Success", "Successfully created project ''" + e.toString() + "'' on github", NotificationType.INFORMATION));
            }
        });

        table.setModel(model);

        //adapt columns to content
        for (int column = 0; column < 1; column++)
        {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }

        table.setCellSelectionEnabled(true);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedColumn()==0)
                {
                    String name_class = table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).toString();
                    PsiManager psiManager = PsiManager.getInstance(project);
                    String class_to_open = psiManager.findDirectory(project.getBaseDir()).getVirtualFile().getPath()+"/src/"+name_class+".java";
                    VirtualFile file_class = LocalFileSystem.getInstance().findFileByPath(class_to_open);
                    FileEditorManager.getInstance(project).openFile(file_class,true,true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(22);
        table.setAutoCreateRowSorter(true);

        return table;*/
    }

    private void updateTable(String nameFilter) throws IOException {
        List<SmellData> filteredDataList = this.filterData(nameFilter);

        DefaultTableModel model = (DefaultTableModel) smellTable.getModel();
        model.setRowCount(0);
        smellTable.setAutoCreateRowSorter(true);

//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
//        smellTable.setRowSorter(sorter);
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
//        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
//        sorter.setSortKeys(sortKeys);
//        sorter.sort();

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
