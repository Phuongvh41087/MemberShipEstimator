import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.outputObj.MemberList;
import pojo.outputObj.MemberTitle;
import pojo.outputObj.OutputJSON;
import pojo.outputObj.TierList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OutputData {

    public void outputToFile(String fileName, Map<String, String> memberMap, int count, Map<String, Integer> tierCount, List<String> filesProcessed) {
        Path path = Paths.get(fileName);
        ArrayList<String> outputContent = new ArrayList<>();
        TreeMap<String, String> sortedMemberMap = new TreeMap<>(memberMap);
        TreeMap<String, Integer> sortedTierCount = new TreeMap<>(tierCount);

        outputContent.add("Total Member Found: " + count + "\n");
        outputContent.add("Tiers: ");
        sortedTierCount.forEach((K, V) -> outputContent.add(K + " : " + V));
        outputContent.add("\nMember List:");
        sortedMemberMap.forEach((K, V) -> outputContent.add(outputContent.size() - (tierCount.size()+2) + ") Name: " + V + " | ID: " + K));
        outputContent.add("\nFiles Processed: ");
        outputContent.addAll(filesProcessed);
        try {
            Files.write(path, outputContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void outputToJSON(String fileName, Map<String, String> memberMap, int count, Map<String, Integer> tierCount, Map<String, Integer> titleCount, List<String> filesProcessed) {
        try {
            TreeMap<String, String> sortedMemberMap = new TreeMap<>(memberMap);
            TreeMap<String, Integer> sortedTierCount = new TreeMap<>(tierCount);
            TreeMap<String, Integer> sortedTitleCount = new TreeMap<>(titleCount);

            List<MemberList> memberList = new ArrayList<>();
            List<TierList> tierList = new ArrayList<>();
            List<MemberTitle> titleList = new ArrayList<>();

            sortedMemberMap.forEach((member_ID, member_Name) -> {
                MemberList tmpMember = new MemberList();
                tmpMember.setID(member_ID);
                tmpMember.setName(member_Name);

                memberList.add(tmpMember);
            });
            sortedTierCount.forEach((tierMessage, tierMsg_count) -> {
                TierList tmpTier = new TierList();
                tmpTier.setTierMessage(tierMessage);
                tmpTier.setTierCount(tierMsg_count);

                tierList.add(tmpTier);
            });
            sortedTitleCount.forEach((title, title_count) -> {
                MemberTitle tmpTitle = new MemberTitle(title, title_count);
                titleList.add(tmpTitle);
            });

            OutputJSON outputObject = new OutputJSON();
            outputObject.setTotalMember(count);
            outputObject.setMemberList(memberList);
            outputObject.setTierList(tierList);
            outputObject.setTitleList(titleList);
            outputObject.setFilesProcessed(filesProcessed);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().
                    writeValue(new File(fileName), outputObject);
        } catch (Exception ex) {
            System.out.println("Error occured while writing output to file " + fileName + "!");
            ex.printStackTrace();
        }
    }
}
