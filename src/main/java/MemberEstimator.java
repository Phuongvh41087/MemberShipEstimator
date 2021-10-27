import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(name = "memberEstimator", mixinStandardHelpOptions = true, version = "Member Estimator 1.1",
        description = "Estimate amount of Members based on YT Chat JSON")
public class MemberEstimator implements Callable<Integer> {

    private int count = 0;
    private int fileProcessed = 0;
    private boolean hasArgs = true;
    private Map<String, String> memberMap = new HashMap<>();
    private Map<String, Integer> tierCount = new HashMap<>();
    private List<String> filesProcessed = new ArrayList<>();

    @Option(names = {"-f", "--files"}, description = "File Names", split = ",")
    ArrayList<Path> fileNames;

    @Option(names = {"-d", "--directory"}, description = "Directory Name")
    String directoryName;

    @Option(names = {"-i", "--input"}, description = "Record input file")
    String recordFileNames;

    @Option(names = {"-o", "--output"}, description = "Output file")
    String outputFileName;

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
        System.out.println("*** DONE ***");
        System.out.println("==========================");

        OutputData outputData = new OutputData();
        if ((outputFileName == null) || (outputFileName.trim().isEmpty())) {
            System.out.print("Output File name: ");
            outputFileName = (new Scanner(System.in)).nextLine();

            if (!outputFileName.trim().isEmpty()) {
                outputData.outputToFile(outputFileName, memberMap, count, tierCount, filesProcessed);
            }
        } else {
            outputData.outputToJSON(outputFileName, memberMap, count, tierCount, filesProcessed);
        }
    }

    public void processFile(String fileName) {
        if (filesProcessed.contains(fileName)) {
            System.out.println("File " + fileName + " has already been processed.  Move onto next file...");
        } else {
            File processingFile = new File(fileName);
            FileProcessor fileProcessor = new FileProcessor(fileName, processingFile, memberMap, count, tierCount);

            count = fileProcessor.getCount();
            memberMap = fileProcessor.getMemberMap();
            tierCount = fileProcessor.getTierCount();
            filesProcessed.add(fileName);
            fileProcessed++;

            System.out.println("Files Processed: " + fileProcessed);
        }
    }

    public void processDirectory(String directoryName) {
        ArrayList<String> fileList = getFileList(directoryName);

        if (!fileList.isEmpty()) {
            fileList.forEach(this::processFile);
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
            if ((!(recordFileNames == null)) && (!recordFileNames.trim().isEmpty())) {
                RecordLoader recordLoader = new RecordLoader(recordFileNames);
                memberMap = recordLoader.getMemberMap();
                tierCount = recordLoader.getTierCount();
                count = recordLoader.getCount();
                filesProcessed = recordLoader.getFilesProcessed();
            }

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

//  chat_downloader https://www.youtube.com/watch?v=M4suZ4CuEbs --message_groups "messages superchat" --output IRyS_10Oct_ChatMsg.json