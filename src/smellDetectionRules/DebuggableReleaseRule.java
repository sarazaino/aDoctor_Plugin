package smellDetectionRules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebuggableReleaseRule {

    public boolean isDebuggableRelease(File androidManifest) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:debuggable(\\s*)=(\\s*)\"true\"", Pattern.MULTILINE);
        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

}
