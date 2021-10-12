import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RecordLoader implements MemberRecords {

    Map<String, String> memberMap;
    Map<String, Integer> tierCount;
    int count;

    public RecordLoader(String recordFileName) {

        memberMap = new HashMap<>();
        tierCount = new HashMap<>();
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

}