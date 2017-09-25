package smellDetectionRules;

import beans.ClassBean;

import java.io.IOException;

public class DataTransmissionWithoutCompressionRule {

    public boolean isDataTransmissionWithoutCompression(ClassBean pClassBean) throws IOException {

        if (pClassBean.getTextContent().contains("File ")) {
            if (!pClassBean.getTextContent().contains("zip")) {
                return true;
            }
        }

        return false;
    }
}
