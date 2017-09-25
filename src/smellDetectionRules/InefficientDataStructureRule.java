package smellDetectionRules;



import beans.ClassBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InefficientDataStructureRule {

    public boolean isInefficientDataStructure(ClassBean pClass) {

        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", Pattern.MULTILINE);
        Matcher regexMatcher = regex.matcher(pClass.getTextContent());
        return regexMatcher.find();
    }

}
