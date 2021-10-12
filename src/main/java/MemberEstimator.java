import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(name = "memberEstimator", mixinStandardHelpOptions = true, version = "Member Estimator 1.1",
        description = "Estimate amount of Members based on YT Chat JSON")
public class MemberEstimator implements Callable<Integer> {

    int count = 0;
    int fileProcessed = 0;
    boolean hasArgs = true;
    Map<String, String> memberMap = new HashMap<>();
    Map<String, Integer> tierCount = new HashMap<>();

    @Option(names = {"-f", "--files"}, description = "File Names", split = ",")
    ArrayList<Path> fileNames;

    @Option(names = {"-d", "--directory"}, description = "Directory Name")
    String directoryName;

    private ArrayList<String> getFileList(String directory) {
        ArrayList<String> fileList = new ArrayList<>();

        try (Stream<Path> directoryPath = Files.walk(Paths.get(directory))) {
            System.out.println("Scanning folder...");
            directoryPath
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(f -> f.endsWith("json"))
                    .forEach(fileList::add);
        } catch (Exception ex) {
            System.out.println("Error while retrieving files from folder " + directory);
        }
        return fileList;
    }

    public void outputFile() {
        String outputFileName;

        System.out.println("*** DONE ***");
        System.out.println("==========================");
        System.out.print("Output File name: ");
        outputFileName = (new Scanner(System.in)).nextLine();
        if (!outputFileName.trim().isEmpty()) {
            OutputData outputData = new OutputData();
            outputData.outputToFile(outputFileName, memberMap, count, tierCount);
        }
    }

    public void processFile(String fileName) {
        File processingFile = new File(fileName);
        FileProcessor fileProcessor = new FileProcessor(fileName, processingFile, memberMap, count, tierCount);

        count = fileProcessor.getCount();
        memberMap = fileProcessor.getMemberMap();
        tierCount = fileProcessor.getTierCount();
        fileProcessed++;

        System.out.println("Files Processed: " + fileProcessed);
    }

    public void processDirectory(String directoryName) {
        ArrayList<String> fileList = getFileList(directoryName);

        if (!fileList.isEmpty()) {
            fileList.stream().forEach(this::processFile);
        }
    }

    public void noArgs() {
        String fileName;

        try {
            System.out.print("Chat Log file / directory address: ");
            count = 0;
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

            while (((fileName = inputReader.readLine()) != null) && (!fileName.trim().isEmpty())) {
                File inputFile = new File(fileName);
                if (inputFile.isFile()) {
                    processFile(fileName);
                } else {
                    if (inputFile.isDirectory()) {
                        processDirectory(fileName);
                    } else {
                        System.out.println("Error!  File or Folder " + fileName + " doesn't exist");
                    }
                }
                System.out.print("Chat Log file address: ");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public Integer call() {

        if (hasArgs) {
            if (!(fileNames == null) && (!fileNames.isEmpty())) {
                fileNames.stream()
                        .map(Path::toString)
                        .forEach(fileName -> {
                            File inputFile = new File(fileName);
                            if (inputFile.isFile()) {
                                processFile(fileName);
                            } else {
                                System.out.println("Error!  File " + fileName + " doesn't exist");
                            }
                        });
            }
            if ((!(directoryName == null)) && (!directoryName.trim().isEmpty())) {
                if ((new File(directoryName).isDirectory())) {
                    processDirectory(directoryName);
                } else {
                    System.out.println("Error!  Directory " + directoryName + "doesn't exist");
                }
            }
        } else {
            noArgs();
        }
        outputFile();
        return 0;
    }

    public void setNoArgs() {
        hasArgs = false;
    }

    public static void main(String[] args) {
        MemberEstimator memberEstimator = new MemberEstimator();
        if ((args == null) ||(args.length == 0)) {
            memberEstimator.setNoArgs();
        }

        int exitCode = new CommandLine(memberEstimator).execute(args);
        System.exit(exitCode);
    }
}