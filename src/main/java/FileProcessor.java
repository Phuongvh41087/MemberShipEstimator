import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.ChatPOJO;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileProcessor implements MemberRecords {
    HashMap<String, String> memberMap;    //  ID, Name
    HashMap<String, Integer> tierCount;

    int count;

    public FileProcessor(String fileName, File inputFile, HashMap<String, String> mbrMap, int mbrCount, HashMap<String, Integer> t_Count) {
        memberMap = mbrMap;
        tierCount = t_Count;
        count = mbrCount;

        try {
            System.out.println("Processing: " + fileName);

            ObjectMapper objectMapper = new ObjectMapper();
            ChatPOJO[] chatArrays = objectMapper.readValue(inputFile, ChatPOJO[].class);
            ArrayList<ChatPOJO> chatARL = new ArrayList<>(Arrays.asList(chatArrays));

            TierAnalyzer tierAnalyzer = new TierAnalyzer(chatARL);
            tierAnalyzer.writeTierMessages();

            ChatProcess chatProcessor = new ChatProcess(chatARL, memberMap, count, tierCount);
            count = chatProcessor.getCount();
            memberMap = chatProcessor.getMemberMap();
            tierCount = chatProcessor.getTierCount();
            //
            System.out.println("Total Member Count: " + count);
            System.out.println("********************");
        } catch (Exception ex) {
            System.out.println("Error!");
            ex.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> getMemberMap() {
        return memberMap;
    }

    @Override
    public HashMap<String, Integer> getTierCount() {
        return tierCount;
    }

    public int getCount() {
        return count;
    }
}
