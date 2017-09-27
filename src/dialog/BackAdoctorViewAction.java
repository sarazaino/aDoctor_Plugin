package dialog;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class BackAdoctorViewAction extends AnAction {
    private final AdoctorToolWindow toolWindow;

    BackAdoctorViewAction(AdoctorToolWindowImpl aDoctorToolWindow) {
        super("Back", "Back to aDoctor", AllIcons.Actions.Back);
        this.toolWindow = aDoctorToolWindow;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        DataContext dataContext = anActionEvent.getDataContext();
        @NotNull Project project = CommonDataKeys.PROJECT.getData(dataContext);

        toolWindow.close();
        new DialogUI(project).show();


    }
}
