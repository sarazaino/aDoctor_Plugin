package dialog;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;

public abstract class AdoctorToolWindow implements Disposable
{
    @NonNls
    public static final String TOOL_WINDOW_ID ="aDoctor";

    public static AdoctorToolWindow getInstance(Project project)
    {
        return ServiceManager.getService(project, AdoctorToolWindow.class);
    }

    public abstract void show();
    public abstract void close();
}
