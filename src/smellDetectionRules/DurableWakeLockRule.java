package smellDetectionRules;



import beans.ClassBean;
import beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurableWakeLockRule {

    public boolean isDurableWakeLock(ClassBean pClass) {

        Pattern regex = Pattern.compile("(.*)acquire(\\s*)()", Pattern.MULTILINE);
        for (MethodBean method : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(method.getTextContent());
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;

    }

}
