package smellDetectionRules;


import beans.ClassBean;
import beans.MethodBean;

public class PublicDataRule {

    public boolean isPublicData(ClassBean pClass) {

        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("Context.MODE_WORLD_READABLE")) {
                return true;
            } else if (method.getTextContent().contains("Context.MODE_WORLD_WRITEABLE")) {
                return true;
            }
        }
        return false;
    }

}
