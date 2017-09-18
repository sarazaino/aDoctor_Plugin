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
import org.jetbrains.annotations.NotNull;

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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AdoctorDisplay {

    private final Map<AdoctorCategory, JTable> tables = new EnumMap<AdoctorCategory, JTable>(AdoctorCategory.class);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private Project project;

    public AdoctorDisplay(@NotNull Project project) {
        this.project = project;

        JTable resultTable = getTable();
        tables.put(AdoctorCategory.Result,resultTable);
        JTable logTable = new JBTable();
        tables.put(AdoctorCategory.Log,logTable);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabbedPane.add("Result aDoctor", ScrollPaneFactory.createScrollPane(resultTable));
        tabbedPane.add("Log aDoctor", ScrollPaneFactory.createScrollPane(logTable));
    }

    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    public JTable getTable()
    {
        JTable table = new JTable();
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

        return table;
    }
}
