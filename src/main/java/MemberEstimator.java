import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import pojo.ChatPOJO;

public class MemberEstimator {

    public static void main(String[] args) {

        HashMap<String, String> memberMap = new HashMap<>();    //  ID, Name
        HashMap<String, Integer> tierCount = new HashMap<>();
        ChatProcess chatProcessor;

        String fileName;
        int count = 0;
        //
        try {
            System.out.print("Chat Log file address: ");
            count = 0;
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

            while (((fileName = inputReader.readLine()) != null) && (!fileName.trim().isEmpty())) {
                File inputFile = new File(fileName);
                if (inputFile.isFile()) {
                    System.out.println("Processing: " + fileName);

                    ObjectMapper objectMapper = new ObjectMapper();
                    ChatPOJO[] chatArrays = objectMapper.readValue(inputFile, ChatPOJO[].class);
                    ArrayList<ChatPOJO> chatARL = new ArrayList<>(Arrays.asList(chatArrays));

                    TierAnalyzer tierAnalyzer = new TierAnalyzer(chatARL);
                    tierAnalyzer.writeTierMessages();

                    chatProcessor = new ChatProcess(chatARL, memberMap, count, tierCount);
                    count = chatProcessor.getCount();
                    memberMap = chatProcessor.getMap();
                    tierCount = chatProcessor.getTierCount();
                    //
                    System.out.println("Total Member Count: " + count);
                } else {
                    System.out.println("Error!  File doesn't exist");
                }
                System.out.print("Chat Log file address: ");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        //  Output
        String outputFileName = "";
        System.out.println("*** DONE ***");
        System.out.println("==========================");
        System.out.print("Output File name: ");
        outputFileName = (new Scanner(System.in)).nextLine();
        if (!outputFileName.trim().isEmpty()) {
            OutputData outputData = new OutputData();
            outputData.outputToFile(outputFileName, memberMap, count, tierCount);
        }
    }
}