package dialog;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class CloseAdoctorViewAction extends AnAction {

    private final AdoctorToolWindow toolWindow;

    CloseAdoctorViewAction(AdoctorToolWindowImpl aDoctorToolWindow) {
       super("Close", "Close aDoctor", AllIcons.Actions.Cancel);
       this.toolWindow = aDoctorToolWindow;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
     toolWindow.close();
    }
}
