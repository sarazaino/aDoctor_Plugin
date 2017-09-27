package dialog;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

public abstract class AdoctorToolWindow implements Disposable
{
    @NonNls
    public static final String TOOL_WINDOW_ID ="aDoctor";
    public static JTextArea TOOL_WINDOW_TEXT_AREA;

    public static AdoctorToolWindow getInstance(Project project, JTextArea area)
    {
        TOOL_WINDOW_TEXT_AREA = area;
        return ServiceManager.getService(project, AdoctorToolWindow.class);
    }

    public abstract void show();
    public abstract void close();
}
