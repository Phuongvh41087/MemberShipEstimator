import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.inputObj.ChatPOJO;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor implements MemberRecords {
    private Map<String, String> memberMap;    //  ID, Name
    private Map<String, Integer> tierCount;
    private Map<String, Integer> titleCount;
    private Map<String, Integer> headerPrimaryCount;

    private int count;

    public FileProcessor(String fileName, File inputFile, Map<String, String> mbrMap, int mbrCount, Map<String, Integer> t_Count,
                         Map<String, Integer> title_Count, Map<String, Integer> headerPrimary_Count) {
        memberMap = new HashMap<>(mbrMap);
        tierCount = new HashMap<>(t_Count);
        titleCount = new HashMap<>(title_Count);
        headerPrimaryCount = new HashMap<>(headerPrimary_Count);
        count = mbrCount;

        try {
            System.out.println("Processing: " + fileName);

            ObjectMapper objectMapper = new ObjectMapper();
            ChatPOJO[] chatArrays = objectMapper.readValue(inputFile, ChatPOJO[].class);
            ArrayList<ChatPOJO> chatARL = new ArrayList<>(Arrays.asList(chatArrays));

            /*
            TierAnalyzer tierAnalyzer = new TierAnalyzer(chatARL);
            tierAnalyzer.writeTierMessages();
            */

            ChatProcess chatProcessor = new ChatProcess(chatARL, memberMap, count, tierCount, titleCount, headerPrimaryCount);
            count = chatProcessor.getCount();
            memberMap = chatProcessor.getMemberMap();
            tierCount = chatProcessor.getTierCount();
            titleCount = chatProcessor.getTitleCount();
            headerPrimaryCount = chatProcessor.getHeaderPrimaryCount();
            //
            System.out.println("Total Member Count: " + count);
        } catch (Exception ex) {
            System.out.println("Error!");
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getMemberMap() {
        return memberMap;
    }
    @Override
    public Map<String, Integer> getTierCount() {
        return tierCount;
    }
    @Override
    public Map<String, Integer> getTitleCount() {
        return titleCount;
    }
    @Override
    public Map<String, Integer> getHeaderPrimaryCount() {
        return headerPrimaryCount;
    }

    public int getCount() {
        return count;
    }
}
