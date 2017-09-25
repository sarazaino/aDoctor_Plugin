package smellDetectionRules;


import beans.ClassBean;
import beans.MethodBean;

public class MemberIgnoringMethodRule {

    public boolean isMemberIgnoringMethod(ClassBean pClass) {

        for (MethodBean methodBean : pClass.getMethods()) {
            if (!methodBean.isStatic()) {
                if (pClass.getInstanceVariables().size() > 0) {
                    if (methodBean.getUsedInstanceVariables().isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
