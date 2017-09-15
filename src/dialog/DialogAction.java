package dialog;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DialogAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        new DialogUI(project).show();

    }
}
