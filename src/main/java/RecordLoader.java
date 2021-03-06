import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.outputObj.*;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RecordLoader implements MemberRecords {

    private Map<String, String> memberMap;
    private Map<String, Integer> tierCount;
    private Map<String, Integer> titleCount;
    private Map<String, Integer> headerPrimaryCount;
    private List<String> filesProcessed;
    private int count;

    public void TextRecordLoader(String recordFileName) {

        memberMap = new HashMap<>();
        tierCount = new HashMap<>();
        titleCount = new HashMap<>();
        // TODO: add title counting for this
        // TODO: add header primary counting too
        count = 0;

        try (BufferedReader input = Files.newBufferedReader(Paths.get(recordFileName))) {
            String inputLine;
            System.out.println("Reading Record File " + recordFileName + "...");

            while ((inputLine = input.readLine()) != null) {
                if (inputLine.toLowerCase().trim().equalsIgnoreCase("tiers:")) {
                    while ((inputLine = input.readLine()) != null) {
                        if (inputLine.trim().isEmpty()) {
                            break;
                        }
                        String[] tierMessageInfo = inputLine.split(" : ", 2);
                        tierCount.put(tierMessageInfo[0], Integer.parseInt(tierMessageInfo[1]));
                    }
                }

                if (inputLine.toLowerCase().trim().equalsIgnoreCase("member list:")) {
                    while ((inputLine = input.readLine()) != null) {
                        if (inputLine.trim().isEmpty()) {
                            break;
                        }
                        String[] memberMessageInfo = inputLine.split(Pattern.quote(" | ID: "), 2);
                        //  First split string is in form of "<Number>) Name: "
                        //  Second split string is in form of "ID: "
                        memberMessageInfo[0] = memberMessageInfo[0].substring(memberMessageInfo[0].indexOf("Name: ")+6).trim();
                        memberMessageInfo[1] = memberMessageInfo[1].replace("ID: ", "");

                        memberMap.put(memberMessageInfo[1], memberMessageInfo[0]);
                        count++;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error while reading record file " + recordFileName);
            ex.printStackTrace();
        }
        System.out.println("*** DONE ***");
    }

    //  JSON
    public RecordLoader(String recordFileName) {

        memberMap = new HashMap<>();
        tierCount = new HashMap<>();
        titleCount = new HashMap<>();
        headerPrimaryCount = new HashMap<>();

        count = 0;
        File inputFile = new File(recordFileName);

        try {
            System.out.println("Reading Record File " + recordFileName + "...");
            ObjectMapper objectMapper = new ObjectMapper();
            OutputJSON outputObject = objectMapper.readValue(inputFile, OutputJSON.class);

            count = outputObject.getTotalMember();
            ArrayList<MemberList> memberList = new ArrayList<>(outputObject.getMemberLists());
            memberList.forEach(member -> {
                memberMap.put(member.getID(), member.getName());
            });
            ArrayList<TierList> tierList = new ArrayList<>(outputObject.getTierList());
            tierList.forEach(tier -> {
                tierCount.put(tier.getTierMessage(), tier.getTierCount());
            });
            ArrayList<MemberTitle> titleList = new ArrayList<>(outputObject.getTitleList());
            titleList.forEach(title -> {
                titleCount.put(title.getmTitle_Title(), title.getmTitle_Count());
            });
            ArrayList<HeaderPrimaryTxt> headerList = new ArrayList<>(outputObject.getHeaderPrimaryList());
            headerList.forEach(headerPrimaryText -> {
                headerPrimaryCount.put(headerPrimaryText.getMember_Header(), headerPrimaryText.getCount());
            });
            filesProcessed = new ArrayList<>(outputObject.getFilesProcessed());

        } catch (Exception ex) {
            System.out.println("Error while reading record file " + recordFileName);
            ex.printStackTrace();
        }
        System.out.println("*** DONE ***");

    }


    public int getCount() {
        return count;
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

    public List<String> getFilesProcessed() {
        return filesProcessed;
    }

}
