package dialog;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;

import java.io.File;

public class DialogAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        final PluginId pluginId = PluginId.getId("idaDoctor");
        final IdeaPluginDescriptor pluginDescriptor = PluginManager.getPlugin(pluginId);
        if(!new File(pluginDescriptor.getPath().getAbsolutePath()+"/resources").exists())
        {
            new File(pluginDescriptor.getPath().getAbsolutePath()+"/resources").mkdirs();
        }
        new DialogUI(project).show();

    }
}