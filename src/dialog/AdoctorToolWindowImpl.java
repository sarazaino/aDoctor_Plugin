package dialog;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class AdoctorToolWindowImpl extends AdoctorToolWindow {

    private final Project project;
    private final JPanel panel;
    private final AdoctorDisplay display;
    private ToolWindow myToolWindow = null;

    public AdoctorToolWindowImpl(@NotNull Project project) {
        this.project = project;
        final DefaultActionGroup toolbarGroup = new DefaultActionGroup();
        toolbarGroup.add(new ExportAdoctorAction(new ImageIcon(getClass().getResource("/csv.png"))));
        toolbarGroup.add(new BackAdoctorViewAction(this));
        toolbarGroup.add(new CloseAdoctorViewAction(this));
        final ActionManager actionManager = ActionManager.getInstance();
        final ActionToolbar toolbar  = actionManager.createActionToolbar(TOOL_WINDOW_ID, toolbarGroup,false);
        panel = new JPanel(new BorderLayout());
        display = new AdoctorDisplay(project, TOOL_WINDOW_TEXT_AREA);
        panel.add(toolbar.getComponent(),BorderLayout.WEST);
        panel.add(display.getTabbedPane(),BorderLayout.CENTER);
        register();
    }

    public void register(){
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        myToolWindow = toolWindowManager.registerToolWindow("aDoctor",panel, ToolWindowAnchor.BOTTOM);
        myToolWindow.setTitle("Risultati");
        myToolWindow.setIcon(new ImageIcon(getClass().getResource("/aDoctor.png")));
        myToolWindow.setAvailable(false,null);
    }

    @Override
    public void show() {
        myToolWindow.setAvailable(true,null);
        myToolWindow.show(null);
    }

    @Override
    public void close() {
        myToolWindow.hide(null);
        myToolWindow.setAvailable(false,null);
    }

    @Override
    public void dispose() {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
    }
}
