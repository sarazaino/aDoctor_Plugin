package process;


import beans.ClassBean;
import beans.PackageBean;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import smellDetectionRules.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RunAndroidSmellDetection {

    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] FILE_HEADER;
    private static File fileName;
    private static Project root_project;

    public RunAndroidSmellDetection(Project project)
    {
        this.root_project = project;
    }

    // The folder contains the set of Android apps that need to be analyzed
    public static void main(String[] args) throws IOException {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));

        // Folder containing android apps to analyze
        final PluginId pluginId = PluginId.getId("idaDoctor");
        final IdeaPluginDescriptor pluginDescriptor = PluginManager.getPlugin(pluginId);
        File experimentDirectory = new File(root_project.getBasePath());
        fileName = new File(pluginDescriptor.getPath().getAbsolutePath()+"/resources/results.csv");
        String smellsNeeded = args[0];

        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 1];

        DataTransmissionWithoutCompressionRule dataTransmissionWithoutCompressionRule = new DataTransmissionWithoutCompressionRule();
        DebuggableReleaseRule debbugableReleaseRule = new DebuggableReleaseRule();
        DurableWakeLockRule durableWakeLockRule = new DurableWakeLockRule();
        InefficientDataFormatAndParserRule inefficientDataFormatAndParserRule = new InefficientDataFormatAndParserRule();
        InefficientDataStructureRule inefficientDataStructureRule = new InefficientDataStructureRule();
        InefficientSQLQueryRule inefficientSQLQueryRule = new InefficientSQLQueryRule();
        InternalGetterSetterRule internaleGetterSetterRule = new InternalGetterSetterRule();
        LeakingInnerClassRule leakingInnerClassRule = new LeakingInnerClassRule();
        LeakingThreadRule leakingThreadRule = new LeakingThreadRule();
        MemberIgnoringMethodRule memberIgnoringMethodRule = new MemberIgnoringMethodRule();
        NoLowMemoryResolverRule noLowMemoryResolverRule = new NoLowMemoryResolverRule();
        PublicDataRule publicDataRule = new PublicDataRule();
        RigidAlarmManagerRule rigidAlarmManagerRule = new RigidAlarmManagerRule();
        SlowLoopRule slowLoopRule = new SlowLoopRule();
        UnclosedCloseableRule unclosedCloseableRule = new UnclosedCloseableRule();

        String[] smellsType = {"DTWC", "DR", "DW", "IDFP", "IDS", "ISQLQ", "IGS", "LIC", "LT", "MIM", "NLMR", "PD", "RAM", "SL", "UC"};

        FILE_HEADER[0] = "Class";

        int headerCounter = 1;

        for (int i = 0; i < smellsNeeded.length(); i++) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = smellsType[i];
                headerCounter++;
            }
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        FileWriter fileWriter = new FileWriter(fileName);
        try (CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
            csvFilePrinter.printRecord((Object[]) FILE_HEADER);

            for (File project : experimentDirectory.listFiles()) {

                if (!project.isHidden()) {

                    // Method to convert a directory into a set of java packages.
                    ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

                    for (PackageBean packageBean : packages) {

                        for (ClassBean classBean : packageBean.getClasses()) {

                            List record = new ArrayList();

                            System.out.println("-- Analyzing class: " + classBean.getBelongingPackage() + "." + classBean.getName());

                            record.add(classBean.getBelongingPackage() + "." + classBean.getName());

                            if (smellsNeeded.charAt(0) == '1') {
                                if (dataTransmissionWithoutCompressionRule.isDataTransmissionWithoutCompression(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(1) == '1') {
                                if (debbugableReleaseRule.isDebuggableRelease(RunAndroidSmellDetection.getAndroidManifest(project))) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(2) == '1') {
                                if (durableWakeLockRule.isDurableWakeLock(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(3) == '1') {
                                if (inefficientDataFormatAndParserRule.isInefficientDataFormatAndParser(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(4) == '1') {
                                if (inefficientDataStructureRule.isInefficientDataStructure(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(5) == '1') {
                                if (inefficientSQLQueryRule.isInefficientSQLQuery(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(6) == '1') {
                                if (internaleGetterSetterRule.isInternalGetterSetter(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(7) == '1') {
                                if (leakingInnerClassRule.isLeakingInnerClass(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(8) == '1') {
                                if (leakingThreadRule.isLeakingThread(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(9) == '1') {
                                if (memberIgnoringMethodRule.isMemberIgnoringMethod(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(10) == '1') {
                                if (noLowMemoryResolverRule.isNoLowMemoryResolver(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(11) == '1') {
                                if (publicDataRule.isPublicData(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(12) == '1') {
                                if (rigidAlarmManagerRule.isRigidAlarmManager(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(13) == '1') {
                                if (slowLoopRule.isSlowLoop(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(14) == '1') {
                                if (unclosedCloseableRule.isUnclosedCloseable(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }
                            csvFilePrinter.printRecord(record);
                        }
                    }
                }
            }
        }
        System.out.println("CSV file was created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
    }

    public static File getAndroidManifest(File dir) {
        File androidManifest = null;
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                androidManifest = file;
            }
        }
        return androidManifest;
    }

    public static File getFileOutput()
    {
        return fileName;
    }
}
