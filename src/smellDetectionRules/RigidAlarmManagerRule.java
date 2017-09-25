package smellDetectionRules;


import beans.ClassBean;
import beans.MethodBean;

public class RigidAlarmManagerRule {

    public boolean isRigidAlarmManager(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("AlarmManager")) {
                for (MethodBean call : method.getMethodCalls()) {
                    if (call.getName().equals("setRepeating")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
